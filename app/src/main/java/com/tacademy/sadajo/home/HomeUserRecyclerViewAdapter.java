package com.tacademy.sadajo.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

public  class HomeUserRecyclerViewAdapter
        extends RecyclerView.Adapter<HomeUserRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShoppingListData> shoppingListDatas;
    private Context context;

    public HomeUserRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
        this.context = context;
        this.shoppingListDatas = shoppingListDatas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public final TextView homeUserIdTextView;
        public final ImageView userProfileImageView;


        public ViewHolder(View view) {
            super(view);
            mView = view;

            homeUserIdTextView = (TextView) view.findViewById(R.id.homeUserIdTextView);
            userProfileImageView = (ImageView) view.findViewById(R.id.userProfileImageView);

        }
    }

    @Override
    public HomeUserRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.home_layout3_recyclerview_item, parent, false);

        return new HomeUserRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeUserRecyclerViewAdapter.ViewHolder holder, final int position) {


        holder.homeUserIdTextView.setText("닉네임");
        holder.userProfileImageView.setImageResource(R.drawable.profile_empty);


        holder.userProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, MyPageActivity.class);
                intent.putExtra("viewType",1);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListDatas.size();
    }
}


