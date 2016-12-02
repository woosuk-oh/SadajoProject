package com.tacademy.sadajo.search.searchdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailDB;

import java.util.ArrayList;

/**
 * Created by woosuk on 2016-11-15.
 */



// 쇼퍼맨에게 부탁해볼까요? 부분임.

public class DetailShopermanRecyclerAdapter extends RecyclerView.Adapter<DetailShopermanRecyclerAdapter.ViewHolder>{
    private Context context;
    private ArrayList<SearchDetailDB???> mItems;





    public DetailShopermanRecyclerAdapter(ArrayList<SearchDetailDB????> items, Context mCotext) {

        mItems = items;
        context = mCotext;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 레이아웃을 뷰(홀더)에 붙이기 위한 인플레이터임.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_detail_shopperuser_recycler, parent, false);

        Log.d("ItemSize", ""+mItems.size());


        ViewHolder holder = new ViewHolder(v);



        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {



        holder.itemName.setText(mItems.get(position).itemname);


        Glide.with(SadajoContext.getContext())
                .load(mItems.get(position).get())
                .into(holder.itemImage);


        /*holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchDetail.class);
                intent.putExtra("key", holder.itemName.getText());
                context.startActivity(intent);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return mItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView itemImage;
        public TextView itemName;

        LinearLayout itemContainer;



        public ViewHolder(final View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.detail_circleimageuser);
            itemName = (TextView) itemView.findViewById(R.id.detail_shopperuser_name);
            itemContainer = (LinearLayout) itemView.findViewById(R.id.search_detail_user_set);



        }
    }






}
