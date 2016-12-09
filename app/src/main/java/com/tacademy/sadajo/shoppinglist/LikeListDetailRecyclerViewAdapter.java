package com.tacademy.sadajo.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.shoppinglist.Goods;
import com.tacademy.sadajo.search.searchdetail.SearchDetail;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 12. 6..
 */

public class LikeListDetailRecyclerViewAdapter
        extends RecyclerView.Adapter<LikeListDetailRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Goods> goodsArrayList = new ArrayList<>();
    private Context context;

    public LikeListDetailRecyclerViewAdapter(Context context) {
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public ImageView itemProductImageView;
        public ImageView itemFlagImageView;
        public TextView itemCountryNameTextView;
        public TextView itemProductNameTextView;


        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            itemProductImageView = (ImageView) itemView.findViewById(R.id.itemProductImageView);
            itemFlagImageView = (ImageView) itemView.findViewById(R.id.itemFlagImageView);
            itemCountryNameTextView = (TextView) itemView.findViewById(R.id.itemCountryNameTextView);
            itemProductNameTextView = (TextView) itemView.findViewById(R.id.itemProductNameTextView);


        }
    }

    @Override
    public LikeListDetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.mypage_item_recyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        Glide.with(SadajoContext.getContext())
                .load(goodsArrayList.get(position).goodsImg)
                .thumbnail(0.1f)
                .into(holder.itemProductImageView);
        Glide.with(SadajoContext.getContext())
                .load(goodsArrayList.get(position).goodscountryFlagImg)
                .thumbnail(0.1f)
                .into(holder.itemFlagImageView);
        holder.itemProductNameTextView.setText(goodsArrayList.get(position).goodsName);
        holder.itemCountryNameTextView.setText(goodsArrayList.get(position).goodsCountryName);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SearchDetail.class);
                intent.putExtra("key", holder.itemProductNameTextView.getText()); //상품명으로 넘겨줌 TODO:서치에서 상품명Intent처리
                context.startActivity(intent);
            }
        });

    }

    public void addDetail(ArrayList<Goods> goodsArrayList) {
        this.goodsArrayList = goodsArrayList;

    }

    @Override
    public int getItemCount() {
        return goodsArrayList.size();
    }
}
