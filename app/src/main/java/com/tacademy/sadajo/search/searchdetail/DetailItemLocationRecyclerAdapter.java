package com.tacademy.sadajo.search.searchdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tacademy.sadajo.R;

import java.util.ArrayList;

/**
 * Created by woosuk on 2016-11-15.
 */



// Search List Activity 에 들어갈 리싸이클러뷰. 어댑터+홀더

public class DetailItemLocationRecyclerAdapter extends RecyclerView.Adapter<DetailItemLocationRecyclerAdapter.ViewHolder>{
    private Context context;
    private ArrayList<String> mItems;





    public DetailItemLocationRecyclerAdapter(ArrayList<String> items, Context mCotext) {

        mItems = items;
        context = mCotext;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 레이아웃을 뷰(홀더)에 붙이기 위한 인플레이터임.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_detail_itemlocation_recycler, parent, false);

        Log.d("ItemSize", ""+mItems.size());


        ViewHolder holder = new ViewHolder(v);



        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.itemName.setText(mItems.get(position));






    }

    @Override
    public int getItemCount() {
        return mItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName;



        public ViewHolder(final View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.detail_location_itemnames);




        }
    }






}
