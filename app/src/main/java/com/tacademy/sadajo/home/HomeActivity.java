package com.tacademy.sadajo.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.Home.HomeDB;
import com.tacademy.sadajo.network.Home.HomeJSONParser;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.search.searchlist.SearchListActivity;

import org.apmem.tools.layouts.FlowLayout;

import java.io.IOException;
import java.net.MalformedURLException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.ProgressDialog.show;
import static android.graphics.Typeface.NORMAL;


public class HomeActivity extends BaseActivity {


    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;
    private int userAccount;

    Toolbar toolbar;
    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    // ImageView profileImageView;
    TextView countryNameTextView;
    TextView comeDateTextView;
    TextView departDateTextView;
    Button scheduleRegisterButton;

    LinearLayout register;


    ImageView cardView2CountryFlagImageView;
    ImageView cardView3CountryFlagImageView;

    TextView cardView2CountryTextView;
    TextView cardView3CountryTextView;


    FlowLayout flowLayout;
    RecyclerView recyclerView;

    HomeUserRecyclerViewAdapter homeUserRecyclerViewAdapter;
    HomeDB homeDB;
    ProgressDialog progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setBottomButtonClickListener();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setBackgroundResource(R.drawable.tool_01_main); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        setToolbar(false);


        countryNameTextView = (TextView) findViewById(R.id.countryNameTextView);//국가명

        departDateTextView = (TextView) findViewById(R.id.departDateTextView); //떠나요날짜

        comeDateTextView = (TextView) findViewById(R.id.comeDateTextView); //돌아와요날짜
        scheduleRegisterButton = (Button) findViewById(R.id.scheduleRegisterButton); //일정등록버튼
        register = (LinearLayout) findViewById(R.id.register);
        cardView2CountryTextView = (TextView) findViewById(R.id.cardView2CountryTextView);//두번째카드뷰 국가명
        cardView3CountryTextView = (TextView) findViewById(R.id.cardView3CountryTextView);//세번째카드뷰 국가명
        cardView2CountryFlagImageView = (ImageView) findViewById(R.id.cardView2CountryFlagImageView);//두번째 카드뷰 flag이미지
        cardView3CountryFlagImageView = (ImageView) findViewById(R.id.cardView3countryFlagImageView);//세번째 카드뷰 flag이미지

        flowLayout = (FlowLayout) findViewById(R.id.flowLayout); //taglist layout
        recyclerView = (RecyclerView) findViewById(R.id.anoter_shoplist_recyclerView); //쇼핑리스트 리사이클러뷰

        scheduleRegisterButton.setOnClickListener(onClickListener);
        register.setOnClickListener(onClickListener);


        //layout3
        recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 4));
        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(45, "bottom");//리사이클러뷰 아이템간 간격
        recyclerView.addItemDecoration(decoration);





        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(SadajoContext.getContext());
        sharedPreferenceUtil.setAccessToken(2);
        userAccount = sharedPreferenceUtil.getAccessToken();

        String fcmToken = FirebaseInstanceId.getInstance().getToken(); //푸시 토큰받아옴

       // sharedPreferenceUtil.setUuidKey(fcmToken);
        Log.e("Home Activity :", String.valueOf(userAccount));
        Log.e("Home fcmToken :", fcmToken);
        // 페이스북 아이디 됐는지 확인
        Log.d("페북로그인", "가져온 페북아이디:" + sharedPreferenceUtil.getFaceBookId());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new AsyncHomeRequest().execute();

    }

    public class AsyncHomeRequest extends AsyncTask<Void, Void, HomeDB> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = show(HomeActivity.this,
                    "서버입력중", "잠시만 기다려 주세요 ...", true);
        }

        @Override
        protected HomeDB doInBackground(Void... homeDBs) {
            Response response = null; //응답 담당
            OkHttpClient toServer;
            homeDB = new HomeDB();


            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userAccount))
                        .build();


                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUEST_HOME))
                        .post(postBody)
                        .build();


                //동기 방식
                response = toServer.newCall(request).execute();


                if (response.isSuccessful()) { //연결에 성공하면

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    //  Log.e("wooseokLog", returedMessage);
                    homeDB = HomeJSONParser.getHomeJsonParser(returedMessage); //만들어둔 파서로 returedMessage를 넣어서 파싱하여 homeDB에 값을 넣음.

                } else { // 연결에 실패하면
                    Log.e("요청/응답", response.message().toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                networkfail.sendEmptyMessage(0);
                progressDialog.dismiss();
            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(SadajoContext.getContext(),
//                        "서버와의 통신 연결이 원활치 않습니다.", Toast.LENGTH_SHORT).show();
//                  progressDialog.dismiss();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            return homeDB;


        }

        @Override
        protected void onPostExecute(HomeDB s) {
            // progressDialog.dismiss();
            super.onPostExecute(s);
            progressDialog.dismiss();

//            if (homeDB != null) {


            if (s != null) { //서버로부터 msg를 받았으면.
                progressDialog.dismiss();
                Log.e("progerss", "progress");
                cardView2CountryTextView.setText(s.getTravelCountry()); // 추천리스트 : 해당 국가
                cardView3CountryTextView.setText(s.getTravelCountry()); // 추천리스트2(다른 쇼퍼맨 쇼핑리스트) : 해당 국가


                countryNameTextView.setText(s.travelInfos.titleCountry); // 국가명 받아옴.
                departDateTextView.setText(s.travelInfos.getStartDate()); // 떠나요
                comeDateTextView.setText(s.travelInfos.getEndDate()); //돌아와요
                homeUserRecyclerViewAdapter = new HomeUserRecyclerViewAdapter(HomeActivity.this, s.shoplist);
                recyclerView.setAdapter(homeUserRecyclerViewAdapter);


                String flagUrl = s.getCountryImg();
                Glide.with(SadajoContext.getContext())
                        .load(flagUrl)
                        .into(cardView2CountryFlagImageView);
                Glide.with(SadajoContext.getContext())
                        .load(flagUrl)
                        .into(cardView3CountryFlagImageView);


                //tag button 동적생성 & setText
                int tagCount = s.getTag().size();
                for (int i = 0; i < tagCount; i++) {

                    createTagButton(s.getTag().get(i), i);

                }


            }
        }
    }

    private void setBottomButtonClickListener() {
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));
        shoppingListBtn = (ImageButton) findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn = (ImageButton) findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn = (ImageButton) findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));
        homeBtn.setSelected(true);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View view) {
            ScheduleDialogFragment dialog = new ScheduleDialogFragment();
            switch (view.getId()) {
                case R.id.scheduleRegisterButton:
                    dialog.show(getFragmentManager(), "scheduleDialog");
                    break;
                case R.id.register:
                    dialog.show(getFragmentManager(), "scheduleDialog");
                    break;


            }
        }
    };


    Button.OnClickListener buttonClickListener = new View.OnClickListener() { //tag Button ClickListener
        @Override
        public void onClick(View view) {
            Intent intent;
            Context context = HomeActivity.this;
            String str;
            str = homeDB.getTag().get(view.getId()).toString();
            intent = new Intent(context, SearchListActivity.class);
            intent.putExtra("tag", str); //tag String 넘겨줌
            Log.e("tag", str);
            startActivity(intent);


        }
    };


    //Button 생성 메소드
    public void createTagButton(String str, int i) {
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = 69;

        Button button = new Button(this);
        button.setText(str); //서버로부터 받아온 tag text set
        button.setBackgroundResource(R.drawable.tag_button_file); //tag ninepatch background적용
        FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(width, height);
        button.setPadding(15, 0, 15, 0); // left,right padding : 3
        params.setMargins(0, 0, 45, 45); // bottom, right margin : 15
        button.setGravity(Gravity.CENTER); //gravity : center
        button.setTextSize(13);// textsize : 13sp
        button.setTypeface(button.getTypeface(), NORMAL);
        button.setLayoutParams(params);
        button.setTag("HomeTag");
        button.setId(i);
        button.setOnClickListener(buttonClickListener);
        flowLayout.addView(button); // button 추가
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        long intervalTime = currentTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = currentTime;
            Toast.makeText(getApplicationContext(),
                    "'뒤로' 버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }


    public void setToolbar(boolean b) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(b); //back icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //클릭시 뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    Handler networkfail = new Handler() {// 핸들러
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "네트워크 통신 장애",
                    Toast.LENGTH_LONG).show();

        }
    };
}
