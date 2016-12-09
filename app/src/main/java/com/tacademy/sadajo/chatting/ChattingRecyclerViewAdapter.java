package com.tacademy.sadajo.chatting;

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

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.chatting.ChatDataList;

import java.util.ArrayList;

public class ChattingRecyclerViewAdapter
        extends RecyclerView.Adapter<ChattingRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ChatDataList> chatDataLists;
    private Context mContext;

    int userAccount;

    public ChattingRecyclerViewAdapter(Context context) {
        mContext = context;
        this.chatDataLists = new ArrayList<>();
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(mContext);
        userAccount = sharedPreferenceUtil.getAccessToken();


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView chattingNameTextView;
        public final TextView chattingContentTextView;
        public final TextView chattingDateTextView;
        public final ImageView chattingProfileImageView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            chattingNameTextView = (TextView) view.findViewById(R.id.chattingNameTextView);
            chattingContentTextView = (TextView) view.findViewById(R.id.chattingContentTextView);
            chattingDateTextView = (TextView) view.findViewById(R.id.chattingDateTextView);
            chattingProfileImageView = (ImageView) view.findViewById(R.id.chattingProfileImageView);

        }
    }

    @Override
    public ChattingRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.chatting_recyclerview_item, parent, false);

        return new ChattingRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChattingRecyclerViewAdapter.ViewHolder holder, final int position) {


        if (userAccount == chatDataLists.get(position).requestUserCode) { //본인이 대화 요청자일 경우
            holder.chattingNameTextView.setText(chatDataLists.get(position).carrierName);

            Glide.with(SadajoContext.getContext())
                    .load(chatDataLists.get(position).carrierImg)
                    .into(holder.chattingProfileImageView);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, ChattingDetailActivity.class);
                    intent.putExtra("roomNum", chatDataLists.get(position).roomNum);//roomNum넘겨주기
                    intent.putExtra("targetUserImg", chatDataLists.get(position).carrierImg);
                    intent.putExtra("targetUserCode", chatDataLists.get(position).carrierCode);
                    intent.putExtra("targetUserName", chatDataLists.get(position).carrierName);
                    intent.putExtra("type", 0);

                    mContext.startActivity(intent);

                }
            });
        } else { //대화를 요청받은 경우

            holder.chattingNameTextView.setText(chatDataLists.get(position).requestUserName);

            Glide.with(SadajoContext.getContext())
                    .load(chatDataLists.get(position).requestUserImg)
                    .into(holder.chattingProfileImageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, ChattingDetailActivity.class);
                    intent.putExtra("roomNum", chatDataLists.get(position).roomNum);//roomNum넘겨주기
                    intent.putExtra("targetUserImg", chatDataLists.get(position).requestUserImg);
                    intent.putExtra("targetUserCode", chatDataLists.get(position).requestUserCode);
                    intent.putExtra("targetUserName", chatDataLists.get(position).requestUserName);
                    intent.putExtra("type", 0);
                    mContext.startActivity(intent);
                }
            });


        }
        holder.chattingContentTextView.setText("안녕하세요");
        holder.chattingDateTextView.setText(chatDataLists.get(position).lastDate);
    }


    public void addChatList(ArrayList<ChatDataList> chatDataLists) {
        this.chatDataLists = chatDataLists;

    }

    @Override
    public int getItemCount() {
        return chatDataLists.size();
    }
}