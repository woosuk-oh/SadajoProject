package com.tacademy.sadajo.mypage;


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

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.mypage.MypageTip;
import com.tacademy.sadajo.network.mypage.TipListParser;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TipFragment extends Fragment {


    TipRecyclerViewAdapter tipRecyclerViewAdapter;
    ImageView tipNoItem;
    int userAccount;
    int targetUserCode;

    public TipFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        targetUserCode = getArguments().getInt("targetUserCode", 0);
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getActivity());
        userAccount = sharedPreferenceUtil.getAccessToken();

    }

    public static TipFragment newInstance(int initValue) {
        TipFragment fragment = new TipFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("targetUserCode", initValue);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.mypage_fragment_tip, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tipRecyclerView);
        recyclerView.setLayoutManager(layoutManager);

        tipNoItem = (ImageView) view.findViewById(R.id.tipNoItemImageView);

        tipRecyclerViewAdapter = new TipRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(tipRecyclerViewAdapter);


//        if (tipRecyclerViewAdapter.getItemCount() == 0) {
//            view = inflater.inflate(R.layout.mypage_tip_recyclerview_noitem, container, false);
//
//        }
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        new AsyncTaskTipList().execute();
    }

    private class AsyncTaskTipList extends AsyncTask<Void, Void, ArrayList<MypageTip>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO:커스텀다이얼로그 추가
        }

        @Override
        protected ArrayList<MypageTip> doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            ArrayList<MypageTip> dbs = new ArrayList<>();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userAccount))
                        .add("owner", String.valueOf(targetUserCode))
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUST_MYPAGE_TIP))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    //   Log.e("Log", returedMessage);
                    dbs = TipListParser.getTipListParsing(returedMessage);

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
        public void onPostExecute(ArrayList<MypageTip> listDBs) {
            super.onPostExecute(listDBs);
            if (listDBs != null && listDBs.size() > 0) {
                tipRecyclerViewAdapter.addTip(listDBs);
                tipNoItem.setVisibility(View.GONE);


            } else {
                tipNoItem.setVisibility(View.VISIBLE);

                Log.e("size---", String.valueOf(listDBs.size()));
            }
        }
    }

}
