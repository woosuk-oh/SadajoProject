package com.tacademy.sadajo.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class OtherShoppingListFragment extends Fragment {

    RecyclerView otherShopListRecyclerView;
    OtherShopListRecyclerViewAdapter otherShopListRecyclerViewAdapter;
    private int targetUserCode;
    ImageView otherShopNoItem;

    public OtherShoppingListFragment() {
        // Required empty public constructor
    }

    public static OtherShoppingListFragment newInstance(int initValue) {
        OtherShoppingListFragment fragment = new OtherShoppingListFragment();
        Bundle args = new Bundle();
        args.putInt("targetUserCode", initValue);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetUserCode = getArguments().getInt("targetUserCode", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_other_shopping_list, container, false);

        otherShopListRecyclerView = (RecyclerView) view.findViewById(R.id.otherShopListRecyclerView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        otherShopListRecyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom"); //아이템간 간격
        otherShopListRecyclerView.addItemDecoration(decoration);

        otherShopListRecyclerViewAdapter = new OtherShopListRecyclerViewAdapter(getActivity());
        otherShopListRecyclerView.setAdapter(otherShopListRecyclerViewAdapter);
        otherShopNoItem = (ImageView)view.findViewById(R.id.otherShopNoItemImageView);
        return otherShopListRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new AsyncTaskOtherShopList().execute();
    }
    private class AsyncTaskOtherShopList extends AsyncTask<Void, Void, ArrayList<ShopListDB>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO:커스텀다이얼로그 추가
        }

        @Override
        protected ArrayList<ShopListDB> doInBackground(Void... params) {

            Response response = null; //응답 담당
            OkHttpClient toServer; //연결 담당
            ArrayList<ShopListDB> otherShopList = new ArrayList<>();
            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(targetUserCode))
                        .build();

                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUEST_SHOPLIST))
                        .post(postBody)
                        .build();


                response = toServer.newCall(request).execute();
                if (response.isSuccessful()) {

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    // Log.e("Log", returedMessage);
                    otherShopList = LikeListJSONParser.getLikeListParsing(returedMessage);

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
            return otherShopList;
        }

        @Override
        public void onPostExecute(ArrayList<ShopListDB> listDBs) {
            super.onPostExecute(listDBs);
            if (listDBs != null && listDBs.size() > 0) {
                otherShopListRecyclerViewAdapter.addOtherShopList(listDBs);
                otherShopNoItem.setVisibility(View.GONE);
            }else{
                Log.e("size---", String.valueOf(listDBs.size()));
                otherShopNoItem.setVisibility(View.VISIBLE);

            }
        }
    }
    public  class OtherShopListRecyclerViewAdapter
            extends RecyclerView.Adapter<OtherShopListRecyclerViewAdapter.ViewHolder>{

        private ArrayList<ShopListDB> otherListDBs;
        private Context context;
        public OtherShopListRecyclerViewAdapter(Context context) {
            this.context = context;
            otherListDBs = new ArrayList<>();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView countryNameTextView;
            public final TextView cityNameTextView;
            public final TextView productCountTextView;
            public final TextView listEmptyTextView;
            public final ImageView productImageView;
            public final TextView dateTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                countryNameTextView = (TextView) view.findViewById(R.id.otherShopCountryNameTextView);
                cityNameTextView = (TextView) view.findViewById(R.id.otherShopCityNameTextView);
                productCountTextView = (TextView) view.findViewById(R.id.otherShopCountTextView);
                dateTextView = (TextView) view.findViewById(R.id.otherShopDateTextView);
                listEmptyTextView = (TextView) view.findViewById(R.id.otherShopEmptyTextView);
                productImageView = (ImageView) view.findViewById(R.id.otherShopProductImageView);
            }
        }


        @Override
        public OtherShopListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shoppinglist_other_shoplist_recyclerview_item, parent, false);
            return new ViewHolder(view);

        }


        @Override
        public void onBindViewHolder(final OtherShopListRecyclerViewAdapter.ViewHolder holder, final int position) {


            final  ShopListDB shopList = otherListDBs.get(position);
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

                        .into(holder.productImageView);
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ShoppingListDetailActivity.class); //찜리스트 디테일페이지로이동
                    intent.putExtra("listCode", shopList.listCode); //쇼핑리스트 listCode 넘겨줌
                    intent.putExtra("countryName", shopList.countryNameKor.toString());
                    intent.putExtra("targetUserCode",targetUserCode);
                    intent.putExtra("goodsCount",shopList.goodsCount);
                    intent.putExtra("type",1);

                    Log.e("shopListCode", shopList.listCode.toString());
                    Log.e("shopLisCount", shopList.goodsCount.toString());
                    context.startActivity(intent);

                }
            });
        }




        public void addOtherShopList(ArrayList<ShopListDB> likeListDBs) {
            if(otherListDBs != null && otherListDBs.size() > 0){
                otherListDBs.removeAll(otherListDBs);
            }
            otherListDBs.addAll(likeListDBs);
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return otherListDBs.size();
        }
    }

}
