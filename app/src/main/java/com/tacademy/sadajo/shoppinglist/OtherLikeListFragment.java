package com.tacademy.sadajo.shoppinglist;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
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

    OtherLikeListRecyclerViewAdapter otherLikeRecyclerViewAdapter;
    RecyclerView otherLikeListRecyclerView;
    private int targetUserCode; //해당 페이지의 userCode

    public OtherLikeListFragment() {

    }

    public static OtherLikeListFragment newInstance(int initValue) {
        OtherLikeListFragment fragment = new OtherLikeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("targetUserCode", initValue);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        targetUserCode = getArguments().getInt("targetUserCode", 0);
        Log.e("otherLike", String.valueOf(targetUserCode));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        otherLikeListRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_other_like_list, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        otherLikeListRecyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom"); //아이템간 간격
        otherLikeListRecyclerView.addItemDecoration(decoration);

        otherLikeRecyclerViewAdapter = new OtherLikeListRecyclerViewAdapter(getActivity());
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
                        .add("user", String.valueOf(targetUserCode))
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
    public class OtherLikeListRecyclerViewAdapter
            extends RecyclerView.Adapter<OtherLikeListRecyclerViewAdapter.ViewHolder> {

        private ArrayList<ShopListDB> fieldListDBs;
        private Context context;


        private final static int NO_ITEM_VIEW = 0;
        private final static int CONTENT_VIEW = 1;

        public OtherLikeListRecyclerViewAdapter(Context context) {
            this.context = context;
            fieldListDBs = new ArrayList<>();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView countryNameTextView;
            public final TextView cityNameTextView;
            public final TextView productCountTextView;
            public final TextView listEmptyTextView;
            public final ImageView productImageView;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                countryNameTextView = (TextView) view.findViewById(R.id.likeCountryNameTextView);
                cityNameTextView = (TextView) view.findViewById(R.id.likeCityNameTextView);
                productCountTextView = (TextView) view.findViewById(R.id.likeProductCountTextView);
                listEmptyTextView = (TextView) view.findViewById(R.id.likeListEmptyTextView);
                productImageView = (ImageView) view.findViewById(R.id.likeProductImageView);
            }
        }


        @Override
        public OtherLikeListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            int layoutRes = 0;

            switch (viewType) {
                case NO_ITEM_VIEW: //리스트가 빈 경우
                    layoutRes = R.layout.shoppinglist_noitem_layout;
                    break;

                case CONTENT_VIEW:
                    layoutRes = R.layout.shoppinglist_likelist_recyclerview_item; //나머지 item lyaout
                    break;
            }

            View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
            return new OtherLikeListRecyclerViewAdapter.ViewHolder(view);

        }

        @Override
        public int getItemViewType(int position) {
            if (fieldListDBs.size() == 0) {
                Log.e("count", String.valueOf(getItemCount()));
                return NO_ITEM_VIEW;
            } else {
                return CONTENT_VIEW;
            }
        }

        @Override
        public void onBindViewHolder(final OtherLikeListRecyclerViewAdapter.ViewHolder holder, final int position) {

            if (fieldListDBs !=null && fieldListDBs.size() >0) {
                final  ShopListDB shopList = fieldListDBs.get(position);
                holder.countryNameTextView.setText(shopList.countryNameEng);
                holder.cityNameTextView.setText(shopList.countryNameKor);


                //담은 상품이 없을 경우 default이미지 보여줌
                if (shopList.goodsCount == 0) {
                    holder.productCountTextView.setVisibility(View.GONE);
                    holder.listEmptyTextView.setVisibility(View.VISIBLE);
                    holder.productImageView.setVisibility(View.GONE);

                } else {
                    holder.productCountTextView.setText(String.valueOf(shopList.goodsCount));
                    holder.productCountTextView.setVisibility(View.VISIBLE);
                    holder.listEmptyTextView.setVisibility(View.GONE);
                    Glide.with(SadajoContext.getContext())
                            .load(shopList.img)
                            .thumbnail(0.1f)
                            .into(holder.productImageView);
                }

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, LikeListDetailActivity.class); //찜리스트 디테일페이지로이동
                        intent.putExtra("listCode", shopList.listCode); //찜리스트이 listCode 넘겨줌
                        intent.putExtra("countryName", shopList.countryNameKor.toString());
                        intent.putExtra("type",3); //
                        intent.putExtra("targetUserCode",targetUserCode);
                        Log.e("shopListCode", shopList.listCode.toString());
                        context.startActivity(intent);

                    }
                });
            }

        }


        public void addLikeList(ArrayList<ShopListDB> likeListDBs) {
            if(fieldListDBs != null && fieldListDBs.size() > 0){
                fieldListDBs.removeAll(fieldListDBs);
            }
            fieldListDBs.addAll(likeListDBs);
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return fieldListDBs.size();
        }
    }

}
