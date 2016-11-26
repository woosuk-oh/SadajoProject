package com.tacademy.sadajo.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.fonts.NanumRegularTextView;
import com.tacademy.sadajo.network.Home.HomeDB;
import com.tacademy.sadajo.network.HomeJSONParser;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;

import java.io.IOException;
import java.net.MalformedURLException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.id;


public class HomeActivity extends AppCompatActivity {

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

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;

    NinePatchDrawable ninepatch;
    HomeUserRecyclerViewAdapter homeUserRecyclerViewAdapter;
    HomeTagRecyclerViewAdapter homeTagRecyclerViewAdapter;
    HomeDB homeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setBackgroundResource(R.drawable.tool_01_main); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //back icon


        //바텀바 gone
//        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frameBottomBar);
//        frameLayout.setVisibility(View.GONE);

        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        homeBtn.setSelected(true);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));
        shoppingListBtn = (ImageButton) findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn = (ImageButton) findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn = (ImageButton) findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));

        countryNameTextView = (TextView) findViewById(R.id.countryNameTextView);//국가명

        departDateTextView = (TextView) findViewById(R.id.departDateTextView); //떠나요날짜
        comeDateTextView = (TextView) findViewById(R.id.comeDateTextView); //돌아와요날짜
        scheduleRegisterButton = (Button) findViewById(R.id.scheduleRegisterButton); //일정등록버튼
        scheduleRegisterButton.setTypeface(new NanumRegularTextView(getApplication()).getTypeface());

        cardView2CountryTextView = (TextView) findViewById(R.id.cardView2CountryTextView);//두번째카드뷰 국가명
        cardView3CountryTextView = (TextView) findViewById(R.id.cardView3CountryTextView);//세번째카드뷰 국가명
        cardView2CountryFlagImageView = (ImageView) findViewById(R.id.cardView2CountryFlagImageView);//두번째 카드뷰 flag이미지
        cardView3CountryFlagImageView = (ImageView) findViewById(R.id.cardView3countryFlagImageView);//세번째 카드뷰 flag이미지

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);

        scheduleRegisterButton.setOnClickListener(onClickListener);



        button1.setOnClickListener(onClickListener);


        //layout3
        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(45, "bottom");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.anoter_shoplist_recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 4));
        recyclerView.addItemDecoration(decoration);
        homeUserRecyclerViewAdapter = new HomeUserRecyclerViewAdapter(HomeActivity.this, ShoppingListSample.shoppinList);
        recyclerView.setAdapter(homeUserRecyclerViewAdapter);

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

            if(homeDB != null){

                cardView2CountryTextView.setText(s.getTravelCountry()); // 추천리스트 : 해당 국가
                cardView3CountryTextView.setText(s.getTravelCountry()); // 추천리스트2(다른 쇼퍼맨 쇼핑리스트) : 해당 국가


               // countryNameTextView.setText(s.travelInfos.get(0).getTitleCountry()); // 국가명 받아옴.
                countryNameTextView.setText(s.travelInfos.getTitleCountry()); // 국가명 받아옴.
                departDateTextView.setText(s.travelInfos.getStartDate()); // 떠나요
                comeDateTextView.setText(s.travelInfos.getEndDate()); //돌아와요

                /* TODO 쇼퍼맨 쇼핑리스트 부분 사용자네임 호출방법 찾아야됌.*/
    //                recyclerView.setAdapter(homeUserRecyclerViewAdapter);
    //                homeUserRecyclerViewAdapter.holder.homeUserIdTextView.setText("닉네임");

                /* TODO 버튼 동적 할당 필요. 태그 get하는 부분 Tag 배열 사이즈값 가져와서 for문으로 사이즈값만큼 돌리고 버튼 생성 */
                    button1.setVisibility(View.VISIBLE);
                    button1.setText(s.tag.get(0).toString());
                    button2.setVisibility(View.VISIBLE);
                    button2.setText(s.tag.get(1).toString());
                    button3.setVisibility(View.VISIBLE);
                    button3.setText(s.tag.get(2).toString());

                    button4.setVisibility(View.GONE);
                    //button4.setText("어렵다아아");
                    button5.setVisibility(View.GONE);




                //countryNameTextView.setText(s.shoplist.get(0).userName);
            }
        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {

        Intent intent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.scheduleRegisterButton:
                    ScheduleRegisterDialog dialog = new ScheduleRegisterDialog(HomeActivity.this);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.show();
                    break;

//                case R.id.button1:
//                    intent = new Intent(HomeActivity.this, SearchListActivity.class);
//                    startActivity(intent);
//                    break;

            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
