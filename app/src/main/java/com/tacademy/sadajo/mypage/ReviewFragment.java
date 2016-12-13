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

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.mypage.MypageReview;
import com.tacademy.sadajo.network.mypage.ReviewListParser;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {


    ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;
    int userAccount;
    int targetUserCode;
    ImageView reviewNoItemImageView;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        targetUserCode = getArguments().getInt("targetUserCode", 0);
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getActivity());
        userAccount = sharedPreferenceUtil.getAccessToken();

    }

    public static ReviewFragment newInstance(int initValue) {
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("targetUserCode", initValue);
        reviewFragment.setArguments(bundle);

        return reviewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mypage_fragment_review, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.reviewRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        reviewNoItemImageView = (ImageView) view.findViewById(R.id.reviewNoItemImageView);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom"); //아이템간 간격
        recyclerView.addItemDecoration(decoration);

        reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(reviewRecyclerViewAdapter);



//        if(reviewRecyclerViewAdapter.getItemCount()==0){
//            view =   inflater.inflate(R.layout.mypage_review_recyclerview_noitem, container, false);
//        }

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        new AsyncTaskReviewList().execute();
    }

    private class AsyncTaskReviewList extends AsyncTask<Void, Void, ArrayList<MypageReview>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO:커스텀다이얼로그 추가
        }

        @Override
        protected ArrayList<MypageReview> doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            ArrayList<MypageReview> dbs = new ArrayList<>();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userAccount))
                        .add("owner", String.valueOf(targetUserCode))
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUST_MYPAGE_REVIEW))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    //   Log.e("Log", returedMessage);
                    dbs = ReviewListParser.getReviewListParsing(returedMessage);

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
        public void onPostExecute(ArrayList<MypageReview> listDBs) {
            super.onPostExecute(listDBs);
            if (listDBs != null && listDBs.size() > 0) {
                reviewRecyclerViewAdapter.addReview(listDBs);
                reviewNoItemImageView.setVisibility(View.GONE);


            } else {
                reviewNoItemImageView.setVisibility(View.VISIBLE);

                Log.e("size---", String.valueOf(listDBs.size()));
            }
        }
    }


}
