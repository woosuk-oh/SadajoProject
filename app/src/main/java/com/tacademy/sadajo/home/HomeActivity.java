package com.tacademy.sadajo.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
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


public class HomeActivity extends BaseActivity {


    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

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


    ImageView cardView2CountryFlagImageView;
    ImageView cardView3CountryFlagImageView;

    TextView cardView2CountryTextView;
    TextView cardView3CountryTextView;


    FlowLayout flowLayout;
    RecyclerView recyclerView;

    HomeUserRecyclerViewAdapter homeUserRecyclerViewAdapter;
    HomeDB homeDB;

    int width = ViewGroup.LayoutParams.WRAP_CONTENT;
    int height = 69;
    int tagCount;

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


        //바텀바 gone
//        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frameBottomBar);
//        frameLayout.setVisibility(View.GONE);


        countryNameTextView = (TextView) findViewById(R.id.countryNameTextView);//국가명

        departDateTextView = (TextView) findViewById(R.id.departDateTextView); //떠나요날짜
        comeDateTextView = (TextView) findViewById(R.id.comeDateTextView); //돌아와요날짜
        scheduleRegisterButton = (Button) findViewById(R.id.scheduleRegisterButton); //일정등록버튼

        cardView2CountryTextView = (TextView) findViewById(R.id.cardView2CountryTextView);//두번째카드뷰 국가명
        cardView3CountryTextView = (TextView) findViewById(R.id.cardView3CountryTextView);//세번째카드뷰 국가명
        cardView2CountryFlagImageView = (ImageView) findViewById(R.id.cardView2CountryFlagImageView);//두번째 카드뷰 flag이미지
        cardView3CountryFlagImageView = (ImageView) findViewById(R.id.cardView3countryFlagImageView);//세번째 카드뷰 flag이미지

        flowLayout = (FlowLayout) findViewById(R.id.flowLayout); //taglist layout
        recyclerView = (RecyclerView) findViewById(R.id.anoter_shoplist_recyclerView); //쇼핑리스트 리사이클러뷰

        scheduleRegisterButton.setOnClickListener(onClickListener);


        //layout3
        recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 4));
        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(45, "bottom");//리사이클러뷰 아이템간 간격
        recyclerView.addItemDecoration(decoration);


        new AsyncHomeRequest().execute();


    }


    public class AsyncHomeRequest extends AsyncTask<Void, Void, HomeDB> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         /*   progressDialog = ProgressDialog.show(HomeActivity.this,
                    "서버입력중", "잠시만 기다려 주세요 ...", true);*/
        }

        @Override
        protected HomeDB doInBackground(Void... homeDBs) {
            Response response = null; //응답 담당
            OkHttpClient toServer;
            homeDB = new HomeDB();


            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", "1")
                        .build();


                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUEST_HOME))
                        .post(postBody)
                        .build();

                //동기 방식
                response = toServer.newCall(request).execute();


                if (response.isSuccessful()) { //연결에 성공하면

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    Log.e("wooseokLog", returedMessage);
                    homeDB = HomeJSONParser.getHomeJsonParser(returedMessage); //만들어둔 파서로 returedMessage를 넣어서 파싱하여 homeDB에 값을 넣음.

                } else { // 연결에 실패하면
                    Log.e("요청/응답", response.message().toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            return homeDB;


        }

        @Override
        protected void onPostExecute(HomeDB s) {
            super.onPostExecute(s);
            //   progressDialog.dismiss();

            if (homeDB != null) {

                cardView2CountryTextView.setText(s.getTravelCountry()); // 추천리스트 : 해당 국가
                cardView3CountryTextView.setText(s.getTravelCountry()); // 추천리스트2(다른 쇼퍼맨 쇼핑리스트) : 해당 국가


                // countryNameTextView.setText(s.travelInfos.get(0).getTitleCountry()); // 국가명 받아옴.
                countryNameTextView.setText(s.travelInfos.getTitleCountry()); // 국가명 받아옴.
                departDateTextView.setText(s.travelInfos.getStartDate()); // 떠나요
                comeDateTextView.setText(s.travelInfos.getEndDate()); //돌아와요


                homeUserRecyclerViewAdapter = new HomeUserRecyclerViewAdapter(HomeActivity.this, s.shoplist);
                recyclerView.setAdapter(homeUserRecyclerViewAdapter);

                //tag button 동적생성 & setText
                tagCount = s.getTag().size();
                for (int i = 0; i < tagCount; i++) {

                    createTagButton(s.getTag().get(i), i);

                }


                //countryNameTextView.setText(s.shoplist.get(0).userName);
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

        Intent intent;
        Context context = HomeActivity.this;
        String str;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.scheduleRegisterButton:
                    ScheduleDialogFragment dialog = new ScheduleDialogFragment();
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
            Log.d("tag", str);
            startActivity(intent);


        }
    };


    //Button 생성 메소드
    public void createTagButton(String str, int i) {
        Button button = new Button(this);
        button.setText(str); //서버로부터 받아온 tag text set
        button.setBackgroundResource(R.drawable.tag_button_file); //tag ninepatch background적용
        FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(width, height);
        button.setPadding(15, 0, 15, 0); // left,right padding : 3
        params.setMargins(0, 0, 45, 45); // top, right margin : 15
        button.setGravity(Gravity.CENTER); //gravity : center
        button.setTextSize(13);// textsize : 13sp
        button.setTypeface(null, Typeface.NORMAL);
        button.setLayoutParams(params);
        button.setTag("HomeTag");
        button.setId(i);
        button.setOnClickListener(buttonClickListener);
        flowLayout.addView(button); // button added
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


}
