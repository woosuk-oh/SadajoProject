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

import com.tacademy.sadajo.R;

import java.util.ArrayList;

/**
 * Created by woosuk on 2016-11-15.
 */



// Search List Activity 에 들어갈 리싸이클러뷰. 어댑터+홀더

public class SearchDetailRecyclerAdapter extends RecyclerView.Adapter<SearchDetailRecyclerAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ItemArrayList> mItems;





    public SearchDetailRecyclerAdapter(ArrayList<ItemArrayList> items, Context mCotext) {

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

        holder.itemImage.setImageResource(mItems.get(position).image);
        holder.itemName.setText(mItems.get(position).itemname);



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