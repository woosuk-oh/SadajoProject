package com.tacademy.sadajo.mypage;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.search.searchdetail.SearchDetail;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;

import java.util.ArrayList;

public class ItemReviewRecyclerViewAdapter
        extends RecyclerView.Adapter<ItemReviewRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShoppingListData> shoppingListDatas;
    private Context context;

    public ItemReviewRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
        this.context = context;
        this.shoppingListDatas = shoppingListDatas;
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
    public ItemReviewRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.mypage_item_recyclerview_item, parent, false);

        return new ItemReviewRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemReviewRecyclerViewAdapter.ViewHolder holder, final int position) {


        holder.itemProductImageView.setImageResource(R.drawable.detail_item_img_sample);
        holder.itemProductNameTextView.setText("산타마리아노벨라 향수");
        holder.itemFlagImageView.setImageResource(R.drawable.flag);
        holder.itemCountryNameTextView.setText("이탈리아");


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SearchDetail.class);
                intent.putExtra("key", holder.itemProductNameTextView.getText());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListDatas.size();
    }
}