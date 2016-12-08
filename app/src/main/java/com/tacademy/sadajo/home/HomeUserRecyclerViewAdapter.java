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
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.mypage.MyPageActivity;
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

     int userAccount;

    public HomeUserRecyclerViewAdapter(Context context, ArrayList<HomeShoplistDB> shoppingListDatas) {
        this.context = context;
        this.shoppingListDatas = shoppingListDatas;
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(context);
        userAccount = sharedPreferenceUtil.getAccessToken();

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
                .thumbnail(0.1f)
                .into(holder.userProfileImageView);


        holder.userProfileImageView.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {

                //user 본인/상대방 구분
                if (shoppingListDatas.get(position).getUserId() == userAccount) {
                    intent = new Intent(context, MyPageActivity.class);
                    intent.putExtra("type", false); //type이 1일 경우는 bottombar GONE & backNavigation생성
                    context.startActivity(intent);

                } else {
                    intent = new Intent(context, MyPageOtherActivity.class);//해당 유저의 마이페이지로 이동
                    intent.putExtra("targetUserCode", shoppingListDatas.get(position).getUserId()); //해당페이지userID넘겨줌
                    intent.putExtra("targetUserName",shoppingListDatas.get(position).getUserName());
                    Log.e("userCode", shoppingListDatas.get(position).getUserId().toString());
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListDatas.size();
    }
}


