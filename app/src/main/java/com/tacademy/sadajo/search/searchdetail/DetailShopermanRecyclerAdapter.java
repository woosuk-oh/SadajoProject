package com.tacademy.sadajo.search.searchdetail;

import android.content.Context;
import android.content.Intent;
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
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.mypage.MyPageOtherActivity;
import com.tacademy.sadajo.network.Search.SeachDetail.ShopermanDB;

import java.util.ArrayList;

/**
 * Created by woosuk on 2016-11-15.
 */


// 쇼퍼맨에게 부탁해볼까요? 부분임.

public class DetailShopermanRecyclerAdapter extends RecyclerView.Adapter<DetailShopermanRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ShopermanDB> mItems = new ArrayList<>();
    int userAccount;
    int targetUserCode;


    public DetailShopermanRecyclerAdapter(Context mCotext, ArrayList<ShopermanDB> items) {

        mItems = items;
        context = mCotext;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 레이아웃을 뷰(홀더)에 붙이기 위한 인플레이터임.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_detail_shopperuser_recycler, parent, false);

        Log.d("ItemSize", "" + mItems.size());

        ViewHolder holder = new ViewHolder(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(context);
        userAccount = sharedPreferenceUtil.getAccessToken();
        targetUserCode = mItems.get(position).getUser_code();

        holder.itemName.setText(mItems.get(position).getUser_name());
        Log.d("쇼퍼맨", String.valueOf(targetUserCode));
        Log.d("userAccount", String.valueOf(userAccount));
        Glide.with(SadajoContext.getContext())
                .load(mItems.get(position).getUser_img())


                .into(holder.itemImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View view) {

                Log.e("userAccount", String.valueOf(userAccount));

                if (userAccount == targetUserCode) { //내 프로필일 경우
                    intent = new Intent(context, MyPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", 1);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    intent = new Intent(context, MyPageOtherActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("targetUserCode", targetUserCode);
                    intent.putExtra("targetUserName", mItems.get(position).getUser_name());

                    context.startActivity(intent);

                }

//                Intent intent = new Intent(context, MyPageOtherActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                intent.putExtra("targetUserCode", targetUserCode);
////                intent.putExtra("targetUserName", mItems.get(position).getUser_name());
//                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ImageView itemImage; //쇼퍼맨 이미지
        public TextView itemName; // 쇼퍼맨 이름

        LinearLayout itemContainer;


        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            itemImage = (ImageView) itemView.findViewById(R.id.detail_circleimageuser);
            itemName = (TextView) itemView.findViewById(R.id.detail_shopperuser_name);
            itemContainer = (LinearLayout) itemView.findViewById(R.id.search_detail_user_set);


        }
    }


}
