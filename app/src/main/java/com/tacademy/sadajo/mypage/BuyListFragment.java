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
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.mypage.BuyListData;
import com.tacademy.sadajo.network.mypage.BuyListJsonParser;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class BuyListFragment extends Fragment {


  //  Bundle initBundle = getArguments(); // 아래의 setArguments해준것 빼옴. (ohter user id와 targetuserid임.)
    //initBundle.getInt("value");
  int ohteruserid = 0;
    int userAccount=0;

    private RecyclerView recyclerView;
    BuyListRecyclerViewAdapter recyclerViewAdapter;

    public BuyListFragment() {
    }


    public static BuyListFragment newInstance(int initValue) {
        BuyListFragment fragment = new BuyListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("targetUserCode", initValue);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ohteruserid = getArguments().getInt("targetUserCode", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        //layout3

        View view = inflater.inflate(R.layout.mypage_buylist_fragment, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.mypageBuyRecyclerView);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(72, "left");
        CustomRecyclerDecoration decoration2 = new CustomRecyclerDecoration(72, "right");
        recyclerView.addItemDecoration(decoration);
        recyclerView.addItemDecoration(decoration2);
        recyclerView.setLayoutManager(layoutManager);


        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        userAccount = sharedPreferenceUtil.getAccessToken();


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        new BuyListFragment.BuyListRequest().execute(userAccount,ohteruserid);
    }


    public class BuyListRequest extends AsyncTask<Integer, Void, ArrayList<BuyListData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected ArrayList<BuyListData> doInBackground(Integer... params) {
            OkHttpClient toServer;
            toServer = OkHttpInitManager.getOkHttpClient();
            int userid = params[0];
            int ownerid = params[1];
            okhttp3.Response response = null;

            ArrayList<BuyListData> buyListData = new ArrayList<>();
            try{
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userid))
                        .add("owner",String.valueOf(ownerid))
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUEST_BUYLIST))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();

                if (response.isSuccessful()){
                    String returnMessage = response.body().string();
                    buyListData = BuyListJsonParser.getBuyListDataParsing(returnMessage);
                }
            }catch (Exception e){
                Log.e("파싱에러", e.toString());
            }finally {
                if(response != null){
                    response.close();
                }
            }


            return buyListData;
        }

        @Override
        protected void onPostExecute(ArrayList<BuyListData> buyListDatas) {
            super.onPostExecute(buyListDatas);


            recyclerViewAdapter = new BuyListRecyclerViewAdapter(getContext(), buyListDatas);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();
        }



    }



}
