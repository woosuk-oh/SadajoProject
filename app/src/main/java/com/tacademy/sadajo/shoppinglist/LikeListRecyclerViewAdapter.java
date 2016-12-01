package com.tacademy.sadajo.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.shoppinglist.ShopListDB;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

public class LikeListRecyclerViewAdapter
        extends RecyclerView.Adapter<LikeListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShopListDB> listDBs = new ArrayList<>();
    private Context context;


    private final static int NO_ITEM_VIEW= 0;
    private final static int CONTENT_VIEW = 1;

    public LikeListRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutRes = 0;

        switch (viewType) {
            case NO_ITEM_VIEW: //리스트가 빈 경우
                layoutRes = R.layout.shoppinglist_noitem_layout;
                Log.e("like","NoItem");
                break;

            case CONTENT_VIEW:
                layoutRes = R.layout.shoppinglist_likelist_recyclerview_item; //나머지 item lyaout
                Log.e("recyclerViewLayout","Item");
                break;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount()== 1) {
            Log.e("count",String.valueOf(getItemCount()));
            return NO_ITEM_VIEW;
        } else {

            return CONTENT_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        int viewType = getItemViewType(position); //viewType 체크


        if (viewType == CONTENT_VIEW) {
            holder.countryNameTextView.setText(listDBs.get(position).countryNameEng);
            holder.cityNameTextView.setText(listDBs.get(position).countryNameKor);

            //       holder.productImageView.setImageResource(R.drawable.sample_img);

            //담은 상품이 없을 경우 default이미지 보여줌
            if (listDBs.get(position).goodsCount == 0) {
                holder.productCountTextView.setVisibility(View.GONE);
                holder.listEmptyTextView.setVisibility(View.VISIBLE);
                //  holder.productImageView.setVisibility(View.GONE);

            } else {
                holder.productCountTextView.setText(String.valueOf(listDBs.get(position).goodsCount));

//            Glide.with(SadajoContext.getContext())
//                    .load(listDBs.get(position).img)
//                    .into(holder.productImageView);
            }


            Glide.with(SadajoContext.getContext())
                    .load(listDBs.get(position).img)
                    .into(holder.productImageView);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, LikeListDetailActivity.class); //찜리스트 디테일페이지로이동
                    intent.putExtra("listCode", listDBs.get(position).listCode); //찜리스트이 listCode 넘겨줌
                    intent.putExtra("countryName",listDBs.get(position).countryNameKor.toString());
                    Log.e("shopListCode", listDBs.get(position).listCode.toString());
                    context.startActivity(intent);

                }
            });
        }

    }


    public void addLikeList(ArrayList<ShopListDB> likeListDBs) {

        this.listDBs.addAll(likeListDBs);
    }


    @Override
    public int getItemCount() {

        if(listDBs.size()>0){
            return listDBs.size();
        }else{
            return 1;
        }

    }
}