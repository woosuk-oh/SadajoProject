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
import com.tacademy.sadajo.network.shoppinglist.ShopListDB;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

public class LikeListRecyclerViewAdapter
        extends RecyclerView.Adapter<LikeListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShopListDB> listDBs;
    private Context context;


    public LikeListRecyclerViewAdapter(Context context, ArrayList<ShopListDB> listDBs) {
        this.context = context;
        this.listDBs = listDBs;
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
            countryNameTextView = (TextView) view.findViewById(R.id.countryNameTextView);
            cityNameTextView = (TextView) view.findViewById(R.id.cityNameTextView);
            productCountTextView = (TextView) view.findViewById(R.id.productCountTextView);
            listEmptyTextView = (TextView) view.findViewById(R.id.listEmptyTextView);
            productImageView = (ImageView) view.findViewById(R.id.productImageView);


        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.shoppinglist_likelist_recyclerview_item, parent, false);


        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.countryNameTextView.setText(listDBs.get(position).countryNameEng);
        holder.cityNameTextView.setText(listDBs.get(position).countryNameKor);

 //       holder.productImageView.setImageResource(R.drawable.sample_img);

        if(listDBs.get(position).goodsCount == 0){
            holder.productCountTextView.setVisibility(View.GONE);
            holder.listEmptyTextView.setVisibility(View.VISIBLE);
          //  holder.productImageView.setVisibility(View.GONE);

        }else{
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

                Intent intent = new Intent(context, LikeListDetailActivity.class);
                intent.putExtra("listCode",listDBs.get(position).listCode);
                context.startActivity(intent);

            }
        });

    }


    public void addLikeList(ArrayList<ShopListDB> likeListDBs) {
        this.listDBs.addAll(likeListDBs);
    }



    @Override
    public int getItemCount() {
        return listDBs.size();
    }
}