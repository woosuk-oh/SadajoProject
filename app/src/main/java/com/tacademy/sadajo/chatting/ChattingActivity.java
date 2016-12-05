package com.tacademy.sadajo.chatting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.chatting.ChatJSONParser;
import com.tacademy.sadajo.network.chatting.ChattingList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChattingActivity extends BaseActivity {

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    Toolbar toolbar;

    ChattingRecyclerViewAdapter chattingRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  this.overridePendingTransition(0,0);
        setContentView(R.layout.activity_chatting);
        setBottomButtonClickListener();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.drawable.tool_04_chat); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        setToolbar(false);


        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cattingRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChattingActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        chattingRecyclerViewAdapter = new ChattingRecyclerViewAdapter(ChattingActivity.this);
        recyclerView.setAdapter(chattingRecyclerViewAdapter);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new AsyncTaskGetChattingList().execute();

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

        chattingBtn.setSelected(true);


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

    private class AsyncTaskGetChattingList extends AsyncTask<Void, Void, ChattingList> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO:커스텀다이얼로그 추가
        }

        @Override
        protected ChattingList doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            ChattingList chattingList = new ChattingList();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", "1")
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUST_CHATTINGLIST))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                  //  Log.e("Log", returedMessage);
                    chattingList = ChatJSONParser.getChatListParsing(returedMessage);

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
            return chattingList;
        }

        @Override
        public void onPostExecute(ChattingList chattingList) {
            super.onPostExecute(chattingList);

            chattingRecyclerViewAdapter.addChatList(chattingList.chatDataList);
            chattingRecyclerViewAdapter.notifyDataSetChanged();

        }
    }


}
