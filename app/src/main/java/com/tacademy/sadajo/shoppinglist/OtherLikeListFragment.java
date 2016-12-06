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

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.shoppinglist.LikeListJSONParser;
import com.tacademy.sadajo.network.shoppinglist.ShopListDB;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OtherLikeListFragment extends Fragment {

    LikeListRecyclerViewAdapter otherLikeRecyclerViewAdapter;
    RecyclerView otherLikeListRecyclerView;
    private int userCode; //해당 페이지의 userCode

    public OtherLikeListFragment() {

    }

    public static OtherLikeListFragment newInstance(int initValue) {
        OtherLikeListFragment fragment = new OtherLikeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("userCode", initValue);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        userCode = getArguments().getInt("userCode", 0);
        Log.e("otherLike", String.valueOf(userCode));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        otherLikeListRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_other_like_list, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        otherLikeListRecyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom"); //아이템간 간격
        otherLikeListRecyclerView.addItemDecoration(decoration);

        otherLikeRecyclerViewAdapter = new LikeListRecyclerViewAdapter(getActivity());
        otherLikeListRecyclerView.setAdapter(otherLikeRecyclerViewAdapter);


        return otherLikeListRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new AsyncTaskOtherLikeList().execute();
    }

    private class AsyncTaskOtherLikeList extends AsyncTask<Void, Void, ArrayList<ShopListDB>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO:커스텀다이얼로그 추가
        }

        @Override
        protected ArrayList<ShopListDB> doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            ArrayList<ShopListDB> dbs = new ArrayList<>();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userCode))
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUEST_LIKELIST))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    //   Log.e("Log", returedMessage);
                    dbs = LikeListJSONParser.getLikeListParsing(returedMessage);

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
        public void onPostExecute(ArrayList<ShopListDB> listDBs) {
            super.onPostExecute(listDBs);
            if (listDBs != null && listDBs.size() > 0) {
                otherLikeRecyclerViewAdapter.addLikeList(listDBs);
            } else {
                Log.e("size---", String.valueOf(listDBs.size()));
            }
        }

    }


}
