package com.tacademy.sadajo.shoppinglist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.shoppinglist.LikeListDetail;
import com.tacademy.sadajo.network.shoppinglist.LikeListJSONParser;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LikeListDetailActivity extends BaseActivity {

    TextView toolbarTitle;
    LikeListDetailRecyclerViewAdapter likeDetailRecyclerViewAdapter;
    int listCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_first_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon

        //타이틀 세팅
        Intent intent = getIntent();
        String countryName = intent.getExtras().getString("countryName");

        listCode = intent.getIntExtra("listCode", 0); //리스트코드 받아옴
        toolbarTitle = (TextView) findViewById(R.id.customToolbarTitle);

        toolbarTitle.setText(countryName + " 찜 리스트");


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shoppinListFirstDetailRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LikeListDetailActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        likeDetailRecyclerViewAdapter = new LikeListDetailRecyclerViewAdapter(LikeListDetailActivity.this);
        recyclerView.setAdapter(likeDetailRecyclerViewAdapter);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new AsyncTaskGetLikeListDetail().execute();
    }


    private class AsyncTaskGetLikeListDetail extends AsyncTask<Void, Void, LikeListDetail> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO:커스텀다이얼로그 추가
        }

        @Override
        protected LikeListDetail doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            LikeListDetail likeListDetail = new LikeListDetail();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", "2")
                        .add("listcode", String.valueOf(listCode)) //TODO:수정하기
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUST_LIKEDETAIL))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    //  Log.e("Log", returedMessage);
                    likeListDetail = LikeListJSONParser.getLikeListDetailParsing(returedMessage);

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
            return likeListDetail;
        }

        @Override
        public void onPostExecute(LikeListDetail likeListDetail) {
            super.onPostExecute(likeListDetail);

            likeDetailRecyclerViewAdapter.addDetail(likeListDetail.goodsList);
            likeDetailRecyclerViewAdapter.notifyDataSetChanged();

        }
    }
}