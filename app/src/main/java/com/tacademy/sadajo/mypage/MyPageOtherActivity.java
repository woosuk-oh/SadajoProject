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
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.tacademy.sadajo.BaseActivity;
        import com.tacademy.sadajo.MyPagerAdapter;
        import com.tacademy.sadajo.R;
        import com.tacademy.sadajo.SadajoContext;
        import com.tacademy.sadajo.SharedPreferenceUtil;
        import com.tacademy.sadajo.chatting.ChattingDetailActivity;
        import com.tacademy.sadajo.network.NetworkDefineConstant;
        import com.tacademy.sadajo.network.OkHttpInitManager;
        import com.tacademy.sadajo.network.chatting.ChatJSONParser;
        import com.tacademy.sadajo.network.chatting.ChatListDB;
        import com.tacademy.sadajo.network.mypage.MyPageData;
        import com.tacademy.sadajo.network.mypage.MypageJsonParser;
        import com.tacademy.sadajo.shoppinglist.OtherShoppingListActivity;

        import okhttp3.FormBody;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.RequestBody;
        import okhttp3.Response;

public class MyPageOtherActivity extends BaseActivity {

    LinearLayout otherBuyCountLL;
    LinearLayout otherSellCountLL;


    ImageButton otherBuyCountButton;
    ImageButton otherSellCountButton;
    ImageButton otherShopListButton;
    ImageButton otherChattingButton;

    TextView otherBuyTextView;
    TextView otherSellTextView;
    TextView otherUserNameTextView;
    TextView otherLocTextView;
    ImageView otherProfileImageView;

    ChatListDB chatListDBs = new ChatListDB();

    TextView customToolbarTitle;
    int targetUserCode;
    int userAccount;
    String targetUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_mypage_other);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon

        customToolbarTitle = (TextView) findViewById(R.id.customToolbarTitle);



        Intent intent = getIntent();
        targetUserCode = intent.getIntExtra("targetUserCode", 0); //해당페이지의 유저아이디
        targetUserName = intent.getStringExtra("targetUserName");
        Log.d("타겟유저코드1","ㅇㅇ"+targetUserCode);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        otherBuyCountLL = (LinearLayout) findViewById(R.id.otherBuyCountLL);
        otherSellCountLL = (LinearLayout) findViewById(R.id.otherSellCountLL);
        otherSellCountButton = (ImageButton) findViewById(R.id.otherSellCountButton);
        otherBuyCountButton = (ImageButton) findViewById(R.id.otherBuyCountButton);
        otherChattingButton = (ImageButton) findViewById(R.id.otherChattingButton);
        otherShopListButton = (ImageButton) findViewById(R.id.otherShopListButton);


        otherBuyTextView = (TextView) findViewById(R.id.otherBuyTextView);
        otherSellTextView = (TextView) findViewById(R.id.otherSellTextView);
        otherUserNameTextView = (TextView) findViewById(R.id.otherUserNameTextView);
        otherLocTextView = (TextView) findViewById(R.id.otherLocTextView);
        otherProfileImageView = (ImageView) findViewById(R.id.otherProfileImageView);


        ViewPager viewPager = (ViewPager) findViewById(R.id.otherMypageViewpager);
        if (viewPager != null) {
            setupMyPageViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.otherMypageTab);
        tabLayout.setupWithViewPager(viewPager);


        otherSellCountButton.setOnClickListener(clickListener);
        otherBuyCountButton.setOnClickListener(clickListener);
        otherShopListButton.setOnClickListener(clickListener);
        otherChattingButton.setOnClickListener(clickListener);
        otherSellCountLL.setOnClickListener(clickListener);
        otherBuyCountLL.setOnClickListener(clickListener);



        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(this);
        userAccount = sharedPreferenceUtil.getAccessToken();


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new AsyncTaskOtherMyPageData().execute();
    }

    //사다줌 , 사다조 버튼 거래내역페이지 이동
    ImageButton.OnClickListener clickListener = new View.OnClickListener() {
        Intent intent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.otherSellCountButton: // 다른유저의 사다줌 내역
                    intent = new Intent(MyPageOtherActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 1); //select될 tab값 전달
                    intent.putExtra("targetUserCode", targetUserCode); //해당페이지의 유저Id
                    intent.putExtra("targetUserName",targetUserName);
                    startActivity(intent);
                    break;
                case R.id.otherBuyCountButton: // 다른 유저의 사다조 내역
                    intent = new Intent(MyPageOtherActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 0);
                    intent.putExtra("targetUserCode", targetUserCode); //해당페이지의 유저Id
                    intent.putExtra("targetUserName",targetUserName);
                    Log.d("MyPAgeOtherActivity","누른 유저의 아이디값: "+targetUserCode);
                    startActivity(intent);
                    break;
                case R.id.otherShopListButton:
                    intent = new Intent(MyPageOtherActivity.this, OtherShoppingListActivity.class);
                    intent.putExtra("targetUserCode", targetUserCode); //해당페이지의 유저Id
                    intent.putExtra("targetUserName",targetUserName);
                    Log.e("otherMypage", String.valueOf(targetUserCode));
                    Log.e("targetUserName", String.valueOf(targetUserName));
                    startActivity(intent);

                    break;
                case R.id.otherBuyCountLL:
                    intent = new Intent(MyPageOtherActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 1); //select될 tab값 전달
                    intent.putExtra("targetUserCode", targetUserCode); //해당페이지의 유저Id
                    intent.putExtra("targetUserName",targetUserName);
                    startActivity(intent);
                    break;
                case R.id.otherSellCountLL:
                    intent = new Intent(MyPageOtherActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 0);
                    intent.putExtra("targetUserCode", targetUserCode); //해당페이지의 유저Id
                    intent.putExtra("targetUserName",targetUserName);
                    startActivity(intent);
                    break;
                case R.id.otherChattingButton:
                    new AsyncTaskChatRoom().execute();
                    break;

            }
        }
    };


    private void setupMyPageViewPager(ViewPager viewPager) {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.appendFragment(ReviewFragment.newInstance(targetUserCode), "후기");
        pagerAdapter.appendFragment(TipFragment.newInstance(targetUserCode), "등록한 TIP");
        pagerAdapter.appendFragment(ItemFragment.newInstance(targetUserCode), "등록한아이템");
        viewPager.setAdapter(pagerAdapter);
    }

    private class AsyncTaskChatRoom extends AsyncTask<Void, Void, ChatListDB> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ChatListDB doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            ChatListDB chatListDB = new ChatListDB();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userAccount)) //대화하기 클릭한 userCode
                        .add("type", "new") // 채팅방 생성  type : new
                        .add("carr", String.valueOf(targetUserCode)) //메세지 받는사람 userCode(해당 페이지 userCode)
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUST_CHATLIST))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    Log.e("Log", returedMessage);
                    chatListDB = ChatJSONParser.getChaRoomParsing(returedMessage);

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
            return chatListDB;
        }

        @Override
        public void onPostExecute(ChatListDB chatListDB) {
            super.onPostExecute(chatListDB);


            chatListDBs = chatListDB;

            Intent intent = new Intent(MyPageOtherActivity.this, ChattingDetailActivity.class);
            intent.putExtra("roomNum", chatListDBs.roomNum); //방넘버 넘겨줌
            intent.putExtra("sender", chatListDBs.senderCode); //대화요청한 user
            intent.putExtra("receiver", chatListDBs.receiverCode); //요청받은 user 코드
            intent.putExtra("receiverName", chatListDBs.receiverName); //요청받은 user 이름
            intent.putExtra("receiverImg", chatListDBs.receiverImg); //요청받은 user 이미지
            intent.putExtra("type", 1);
            startActivity(intent);
            Log.e("roomitent", String.valueOf(chatListDBs.roomNum));
        }
    }

    private class AsyncTaskOtherMyPageData extends AsyncTask<Void, Void, MyPageData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO:커스텀다이얼로그 추가
        }

        @Override
        protected MyPageData doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            MyPageData otherDatas = new MyPageData();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userAccount))
                        .add("owner", String.valueOf(targetUserCode))
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUST_MYPAGE))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    //   Log.e("Log", returedMessage);
                    otherDatas = MypageJsonParser.getMypageDataParsing(returedMessage);

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
            return otherDatas;
        }

        @Override
        public void onPostExecute(MyPageData myPage) {
            super.onPostExecute(myPage);

            otherBuyTextView.setText(String.valueOf(myPage.buyNum));
            otherSellTextView.setText(String.valueOf(myPage.sellNum));
            otherUserNameTextView.setText(myPage.targetUserName);
            otherLocTextView.setText(myPage.targetUserLocation);
            Glide.with(SadajoContext.getContext())
                    .load(myPage.targetUserImg)

                    .into(otherProfileImageView);


        }
    }

}