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

public class ShopListDetailRecyclerViewAdapter
        extends RecyclerView.Adapter<ShopListDetailRecyclerViewAdapter.ViewHolder> {


    private ArrayList<Goods> shopGoodsArrayList;
    private Context context;

    public ShopListDetailRecyclerViewAdapter(Context context) {
        this.context = context;
        shopGoodsArrayList = new ArrayList<>();
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
    public ShopListDetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.mypage_item_recyclerview_item, parent, false);

        return new ShopListDetailRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShopListDetailRecyclerViewAdapter.ViewHolder holder, final int position) {


        Glide.with(SadajoContext.getContext())
                .load(shopGoodsArrayList.get(position).goodsImg)

                .into(holder.itemProductImageView);
        Glide.with(SadajoContext.getContext())
                .load(shopGoodsArrayList.get(position).goodscountryFlagImg)

                .into(holder.itemFlagImageView);
        holder.itemProductNameTextView.setText(shopGoodsArrayList.get(position).goodsName);
        holder.itemCountryNameTextView.setText(shopGoodsArrayList.get(position).goodsCountryName);




        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SearchDetail.class);
                intent.putExtra("key",  shopGoodsArrayList.get(position).goodsCode);
                intent.putExtra("key2",  shopGoodsArrayList.get(position).goodsName);
                intent.putExtra("key3",  shopGoodsArrayList.get(position).goodsCountryName);
                context.startActivity(intent);
            }
        });

    }
    public void addDetail(ArrayList<Goods> shopGoods){
        this.shopGoodsArrayList.addAll(shopGoods);

    }
    @Override
    public int getItemCount() {
        return shopGoodsArrayList.size();
    }


}
