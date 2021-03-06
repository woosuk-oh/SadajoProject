package com.tacademy.sadajo.shoppinglist;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.shoppinglist.ShopListDB;
import com.tacademy.sadajo.network.shoppinglist.ShopListJSONParser;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends Fragment {


    ShoppingListRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView shoppingListRecyclerView;
    int userAccount;
    ImageView shopNoItemImageView;

    public ShoppingListFragment() {
    }

    public static ShoppingListFragment newInstance(int initValue) {
        ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
        return shoppingListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shoppinglist_frament, container, false);
        shoppingListRecyclerView = (RecyclerView)view.findViewById(R.id.shoppingListRecyclerView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        shoppingListRecyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom"); //아이템간 간격
        shoppingListRecyclerView.addItemDecoration(decoration);

        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(getContext());
        shoppingListRecyclerView.setAdapter(recyclerViewAdapter);
        shopNoItemImageView = (ImageView)view.findViewById(R.id.shopNoItemImageView);


        return shoppingListRecyclerView;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        userAccount = sharedPreferenceUtil.getAccessToken();

    }

    @Override
    public void onResume() {
        super.onResume();
        new AsyncTaskShopList().execute();

    }

    private class AsyncTaskShopList extends AsyncTask<Void, Void, ArrayList<ShopListDB>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<ShopListDB> doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            ArrayList<ShopListDB> listDBs = new ArrayList<>();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userAccount))
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUEST_SHOPLIST))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    //  Log.e("Log", returedMessage);
                    listDBs = ShopListJSONParser.getShopListParsing(returedMessage);

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
            return listDBs;
        }

        @Override
        public void onPostExecute(ArrayList<ShopListDB> shopListDBs) {
            super.onPostExecute(shopListDBs);
            if(shopListDBs != null && shopListDBs.size() > 0 ){
                shopNoItemImageView.setVisibility(View.GONE);
                recyclerViewAdapter.addShopList(shopListDBs);
            }else{
                shopNoItemImageView.setVisibility(View.VISIBLE);

            }

        }
    }


}
