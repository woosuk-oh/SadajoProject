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
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.shoppinglist.LikeListDetail;
import com.tacademy.sadajo.network.shoppinglist.LikeListJSONParser;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShoppingListDetailActivity extends BaseActivity {

    TextView shopListTitleTextView;
    TextView goodsCountTextView;
    ShopListDetailRecyclerViewAdapter shopListDetailRecyclerViewAdapter;
    int listCode;
    int userAccount;
    int userCode;
    String countryName;

    final int SHOPLIST = 0;
    final int OTHER_SHOPLIST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_detail);

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(this);
        userAccount = sharedPreferenceUtil.getAccessToken();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon


        goodsCountTextView = (TextView) findViewById(R.id.shopGoodsCountTextView);

        getTypeIntent();
        //타이틀 세팅
//        Intent intent = getIntent();
//        String countryName = intent.getExtras().getString("countryName");
//        listCode = intent.getIntExtra("listCode", 0); //리스트코드 받아옴
//        shopListTitleTextView = (TextView) findViewById(R.id.customToolbarTitle);
//        shopListTitleTextView.setText(countryName + " 쇼핑 리스트");


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shoppingListDetailRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShoppingListDetailActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        shopListDetailRecyclerViewAdapter = new ShopListDetailRecyclerViewAdapter(ShoppingListDetailActivity.this);
        recyclerView.setAdapter(shopListDetailRecyclerViewAdapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new AsyncTaskGetShopListDetail().execute();
    }

    private class AsyncTaskGetShopListDetail extends AsyncTask<Void, Void, LikeListDetail> {
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
                        .add("user", String.valueOf(userCode))
                        .add("listcode", String.valueOf(listCode)) //TODO:수정하기
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUST_SHOPDETAIL))
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


            shopListDetailRecyclerViewAdapter.addDetail(likeListDetail.goodsList);
            shopListDetailRecyclerViewAdapter.notifyDataSetChanged();

        }
    }

    public void getTypeIntent() {
        Intent intent = getIntent();
        int type = intent.getExtras().getInt("type");
        Log.e("shoplistDetail", String.valueOf(type));

        switch (type) {
            case SHOPLIST:
                userCode = userAccount;
                countryName = intent.getExtras().getString("countryName");
                listCode = intent.getIntExtra("listCode", 0); //리스트코드 받아옴
                shopListTitleTextView = (TextView) findViewById(R.id.customToolbarTitle);
                shopListTitleTextView.setText(countryName + " 쇼핑 리스트");

            case OTHER_SHOPLIST:
                userCode = intent.getIntExtra("targetUserCode", 0);
                countryName = intent.getExtras().getString("countryName");
                listCode = intent.getIntExtra("listCode", 0); //리스트코드 받아옴
                shopListTitleTextView = (TextView) findViewById(R.id.customToolbarTitle);
                goodsCountTextView.setText(String.valueOf(intent.getIntExtra("goodsCount", 0)));
                shopListTitleTextView.setText(countryName + " 쇼핑 리스트");

        }

    }
}
