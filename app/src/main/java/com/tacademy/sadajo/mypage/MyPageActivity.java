package com.tacademy.sadajo.mypage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.mypage.MyPageData;
import com.tacademy.sadajo.network.mypage.MypageJsonParser;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyPageActivity extends BaseActivity {

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    ImageButton buyCountButton;
    ImageButton sellCountButton;

    LinearLayout mypageBuy;
    LinearLayout mypageSell;


    TextView myPageBuyTextView;
    TextView myPageSellTextView;
    TextView myPageUserNameTextView;
    TextView myPageLocTextView;
    ImageView myPageProfileImageView;


    TabLayout tabLayout;
    Toolbar toolbar;

    int type;
    int BOTTOM = 0;
    public static int SHOPERMAN =1;
    int userAccount;

    String mycountry;
    String mycity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


     /*   if(mycountry == null){*/
            SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(SadajoContext.getContext());
            mycountry = sharedPreferenceUtil.getLocaleKey();
        Log.d("마이페이지mycountry","마이페이지mycountry: "+mycountry);
/*
        }*/



        setContentView(R.layout.activity_mypage);

        setBottomButtonClickListener();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.drawable.tool_03_mypage); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden


        userAccount = sharedPreferenceUtil.getAccessToken();
        Log.e("userAccount", "현재 유저 아이디"+userAccount);
        getTypeIntent(); //이동경로에 따른 페이지구성을 위한 메소드


        sellCountButton = (ImageButton) findViewById(R.id.sellCountButton);
        buyCountButton = (ImageButton) findViewById(R.id.buyCountButton);

        mypageBuy = (LinearLayout) findViewById(R.id.mypageBuy);
        mypageSell = (LinearLayout) findViewById(R.id.mypageSell);

        myPageBuyTextView = (TextView) findViewById(R.id.myPageBuyTextView);
        myPageSellTextView = (TextView) findViewById(R.id.myPageSellTextView);
        myPageUserNameTextView = (TextView) findViewById(R.id.myPageUserNameTextView);
        myPageLocTextView = (TextView) findViewById(R.id.myPageLocTextView);
        myPageProfileImageView=(ImageView)findViewById(R.id.myPageProfileImageView);



        ViewPager viewPager = (ViewPager) findViewById(R.id.mypageViewpager);
        if (viewPager != null) {
            setupMyPageViewPager(viewPager);
        }
        tabLayout = (TabLayout) findViewById(R.id.mypageTab);
        tabLayout.setupWithViewPager(viewPager);
        setTabImage();

        sellCountButton.setOnClickListener(clickListener);
        buyCountButton.setOnClickListener(clickListener);
        mypageSell.setOnClickListener(clickListener);
        mypageBuy.setOnClickListener(clickListener);


     /*   Intent intent;
        Context context = MyPageActivity.this;
        intent = new Intent(context, DealSellListFragment.class);
        intent.putExtra("user", userAccount);
        startActivity(intent);*/

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new AsyncTaskMyPageData().execute();
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
        mypageBtn.setSelected(true);


    }

    private void setTabImage() {

        ImageView imageView = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        ImageView imageView3 = new ImageView(this);
        imageView.setImageResource(R.drawable.selector_tab_mypage_1);
        imageView2.setImageResource(R.drawable.selector_tab_mypage_2);
        imageView3.setImageResource(R.drawable.selector_tab_mypage_3);
        tabLayout.getTabAt(0).setCustomView(imageView);
        tabLayout.getTabAt(1).setCustomView(imageView2);
        tabLayout.getTabAt(2).setCustomView(imageView3);

    }


    //사다줌 , 사다조 버튼 거래내역페이지 이동
    ImageButton.OnClickListener clickListener = new View.OnClickListener() {
        Intent intent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sellCountButton:
                    intent = new Intent(MyPageActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 1); //select될 tab값 전달
                    intent.putExtra("targetUserCode", userAccount);
                    startActivity(intent);
                    break;
                case R.id.buyCountButton:
                    intent = new Intent(MyPageActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 0);
                    intent.putExtra("targetUserCode", userAccount);
                    startActivity(intent);
                    break;
                case R.id.mypageSell:
                    intent = new Intent(MyPageActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 1); //select될 tab값 전달
                    intent.putExtra("targetUserCode", userAccount);
                    startActivity(intent);
                    break;
                case R.id.mypageBuy:
                    intent = new Intent(MyPageActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 0);
                    intent.putExtra("targetUserCode", userAccount);
                    startActivity(intent);
                    break;

            }
        }
    };


    private void setupMyPageViewPager(ViewPager viewPager) {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.appendFragment(ReviewFragment.newInstance(userAccount), "후기");
        pagerAdapter.appendFragment(TipFragment.newInstance(userAccount), "등록한 TIP");
        pagerAdapter.appendFragment(ItemFragment.newInstance(userAccount), "등록한아이템");
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        long intervalTime = currentTime - backPressedTime;

        if (type == BOTTOM) {
            if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = currentTime;
                Toast.makeText(getApplicationContext(),
                        "'뒤로' 버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onBackPressed();
        }
    }



    public void getTypeIntent() {
        Intent intent = getIntent();
        type = intent.getExtras().getInt("type");
        if (type == SHOPERMAN) { //bottom navigation으로 이동한 것이 아닌 경우
            FrameLayout bottomBar = (FrameLayout) findViewById(R.id.frameBottomBar);
            bottomBar.setVisibility(View.GONE);//bottom navigation 제거
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon 생성
            toolbar.setNavigationOnClickListener(new View.OnClickListener() { //뒤로가기
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });


        } else {//bottom navigation으로 이동한 경우
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); //back icon

        }

    }

    private class AsyncTaskMyPageData extends AsyncTask<Void, Void, MyPageData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO:커스텀다이얼로그 추가
        }

        @Override
        protected MyPageData doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            MyPageData dbs = new MyPageData();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userAccount))
                        .add("owner", String.valueOf(userAccount))
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUST_MYPAGE))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    //   Log.e("Log", returedMessage);
                    dbs = MypageJsonParser.getMypageDataParsing(returedMessage);

                } else {
                    Log.e("요청에러", response.message().toString());
                }

            } catch (Exception e) {
                Log.e("파싱에러", e.toString());
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            return dbs;
        }

        @Override
        public void onPostExecute(MyPageData myPage) {
            super.onPostExecute(myPage);


            // TODO 인텐트 받아서 위치값 넣어주기.
            myPageBuyTextView.setText(String.valueOf(myPage.buyNum));
            myPageSellTextView.setText(String.valueOf(myPage.sellNum));
            myPageUserNameTextView.setText(myPage.targetUserName);
          //  myPageLocTextView.setText(myPage.targetUserLocation);
            Intent intent = getIntent();
            mycountry = intent.getExtras().getString("mycountry");
            mycity = intent.getExtras().getString("mycity");

            myPageLocTextView.setVisibility(View.GONE);
          //  myPageLocTextView.setText(mycountry+mycity);

            Glide.with(SadajoContext.getContext())
                    .load(myPage.targetUserImg)
                    .into(myPageProfileImageView);


        }
    }

}

