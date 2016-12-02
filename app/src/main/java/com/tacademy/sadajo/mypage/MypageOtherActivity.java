package com.tacademy.sadajo.mypage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.chatting.ChattingDetailActivity;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.chatting.ChatJSONParser;
import com.tacademy.sadajo.network.chatting.ChatListDB;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyPageOtherActivity extends BaseActivity {


    ImageButton otherBuyCountButton;
    ImageButton otherSellCountButton;
    ImageButton otehrShopListButton;
    ImageButton otherChattingButton;

    ChatListDB chatListDBs;

    TextView mypageBuyTextView;

    int viewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_mypage_other);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon


        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        chatListDBs = new ChatListDB();

        otherSellCountButton = (ImageButton) findViewById(R.id.otherSellCountButton);
        otherBuyCountButton = (ImageButton) findViewById(R.id.otherBuyCountButton);
        otherChattingButton = (ImageButton) findViewById(R.id.otherChattingButton);
        otehrShopListButton = (ImageButton) findViewById(R.id.otehrShopListButton);


        ViewPager viewPager = (ViewPager) findViewById(R.id.otherMypageViewpager);
        if (viewPager != null) {
            setupMyPageViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.otherMypageTab);
        tabLayout.setupWithViewPager(viewPager);


        otherSellCountButton.setOnClickListener(clickListener);
        otherBuyCountButton.setOnClickListener(clickListener);
        otehrShopListButton.setOnClickListener(clickListener);
        otherChattingButton.setOnClickListener(clickListener);

    }


    //사다줌 , 사다조 버튼 거래내역페이지 이동
    ImageButton.OnClickListener clickListener = new View.OnClickListener() {
        Intent intent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.otherSellCountButton:
                    intent = new Intent(MyPageOtherActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 1); //select될 tab값 전달
                    startActivity(intent);
                    break;
                case R.id.otherBuyCountButton:
                    intent = new Intent(MyPageOtherActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 0);
                    startActivity(intent);
                    break;
                case R.id.otehrShopListButton:

                    break;
                case R.id.otherChattingButton:
                    new AsyncTaskChatRoom().execute();
                    intent = new Intent(MyPageOtherActivity.this, ChattingDetailActivity.class);
                    intent.putExtra("roomNum",chatListDBs.roomNum); //방넘버 넘겨줌
                    Log.e("roomNum",String.valueOf(chatListDBs.roomNum));
                    startActivity(intent);

                    break;

            }
        }
    };


    private void setupMyPageViewPager(ViewPager viewPager) {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.appendFragment(ReviewFragment.newInstance(1), "후기");
        pagerAdapter.appendFragment(TipFragment.newInstance(2), "등록한 TIP");
        pagerAdapter.appendFragment(ItemFragment.newInstance(3), "등록한아이템");
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
                        .add("user", "1")
                        .add("type","new")
                        .add("carr","3")
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
            Log.e("ccc",String.valueOf(chatListDBs.roomNum));
        }
    }


}
