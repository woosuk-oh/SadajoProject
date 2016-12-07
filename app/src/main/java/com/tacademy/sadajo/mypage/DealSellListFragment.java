package com.tacademy.sadajo.mypage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.mypage.DealListData;
import com.tacademy.sadajo.network.mypage.DealListJsonParser;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class DealSellListFragment extends Fragment {


    private RecyclerView recyclerView;

    SellListRecyclerViewAdapter recyclerViewAdapter;

    int userAccount=0;

    public DealSellListFragment() {
    }


    public static DealSellListFragment newInstance(int initValue) {
        DealSellListFragment fragment = new DealSellListFragment();
    //    Bundle bundle = new Bundle();
     //   bundle.putInt("value", initValue);
      //  fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       // Bundle initBundle = getArguments();

        //layout3

        View view = inflater.inflate(R.layout.mypage_buylist_fragment, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.mypageBuyRecyclerView);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(72, "left");
        CustomRecyclerDecoration decoration2 = new CustomRecyclerDecoration(72, "right");
        recyclerView.addItemDecoration(decoration);
        recyclerView.addItemDecoration(decoration2);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil();
        userAccount = sharedPreferenceUtil.getSharedPreference(SadajoContext.getContext(), "userAccount");

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        new DealListRequest().execute(userAccount,userAccount);
    }

    public class DealListRequest extends AsyncTask<Integer, Void, ArrayList<DealListData>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected ArrayList<DealListData> doInBackground(Integer... params) {
            OkHttpClient toServer;
            toServer = OkHttpInitManager.getOkHttpClient();
            int userid = params[0];
            int ownerid = params[1];
            okhttp3.Response response = null;

            ArrayList<DealListData> dealListData = new ArrayList<>();
            try{
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userid))
                        .add("owner",String.valueOf(ownerid))
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUEST_DEALLIST))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();

                if (response.isSuccessful()){
                    String returnMessage = response.body().string();
                    dealListData = DealListJsonParser.getDealListDataParsing(returnMessage);
                }
            }catch (Exception e){
                Log.e("파싱에러", e.toString());
            }finally {
                if(response != null){
                    response.close();
                }
            }


            return dealListData;
        }

        @Override
        protected void onPostExecute(ArrayList<DealListData> dealListDatas) {
            super.onPostExecute(dealListDatas);


            recyclerViewAdapter = new SellListRecyclerViewAdapter(getContext(), dealListDatas);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();
        }



    }


}
