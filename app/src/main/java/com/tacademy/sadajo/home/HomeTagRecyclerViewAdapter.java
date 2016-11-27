package com.tacademy.sadajo.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.mypage.MyPageOtherActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

public  class HomeTagRecyclerViewAdapter
        extends RecyclerView.Adapter<HomeTagRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShoppingListData> shoppingListDatas;
    private Context context;

    public HomeTagRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
        this.context = context;
        this.shoppingListDatas = shoppingListDatas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public final Button button;


        public ViewHolder(View view) {
            super(view);
            mView = view;

            button  = (Button) view.findViewById(R.id.textid);

        }
    }

    @Override
    public HomeTagRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.home_card2_recyclerview_item, parent, false);

        return new HomeTagRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeTagRecyclerViewAdapter.ViewHolder holder, final int position) {


        holder.button.setText("닉네임");


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, MyPageOtherActivity.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListDatas.size();
    }
}


