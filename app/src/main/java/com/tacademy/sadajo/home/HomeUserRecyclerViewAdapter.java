package com.tacademy.sadajo.home;

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
import com.tacademy.sadajo.mypage.MyPageOtherActivity;
import com.tacademy.sadajo.network.Home.HomeShoplistDB;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

public class HomeUserRecyclerViewAdapter
        extends RecyclerView.Adapter<HomeUserRecyclerViewAdapter.ViewHolder> {

    private ArrayList<HomeShoplistDB> shoppingListDatas;
    private Context context;

    public HomeUserRecyclerViewAdapter(Context context, ArrayList<HomeShoplistDB> shoppingListDatas) {
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
            userProfileImageView = (ImageView) view.findViewById(R.id.homeUserProfileImageView);


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


        holder.homeUserIdTextView.setText(shoppingListDatas.get(position).getUserName());
        //holder.userProfileImageView.setImageResource(R.drawable.sample_profile);

        Glide.with(SadajoContext.getContext())
                .load(shoppingListDatas.get(position).getUserImg())
                .into(holder.userProfileImageView);



        holder.userProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: 본인일 경우 조건 필요
                Intent intent = new Intent(context, MyPageOtherActivity.class);//해당 유저의 마이페이지로 이동
                intent.putExtra("userCode",shoppingListDatas.get(position).getUserId()); //해당페이지userID넘겨줌
                Log.e("userCode",shoppingListDatas.get(position).getUserId().toString());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListDatas.size();
    }
}


