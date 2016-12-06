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
import com.tacademy.sadajo.network.chatting.ChatDataList;

import java.util.ArrayList;

public class ChattingRecyclerViewAdapter
        extends RecyclerView.Adapter<ChattingRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ChatDataList> chatDataLists= new ArrayList<>();
    private Context context;

    public ChattingRecyclerViewAdapter(Context context) {
        this.context = context;
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


        //Todo: user구분 필요 수정하기
        holder.chattingNameTextView.setText(chatDataLists.get(position).carrierName);
        holder.chattingContentTextView.setText("안녕하세요");
        holder.chattingDateTextView.setText(chatDataLists.get(position).lastDate);


        Glide.with(SadajoContext.getContext())
                .load(chatDataLists.get(position).carrierImg)
                .into(holder.chattingProfileImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChattingDetailActivity.class);
                intent.putExtra("roomNum",chatDataLists.get(position).roomNum);//roomNum넘겨주기
                //TODO:이미지도 넘겨주기
               context.startActivity(intent);

            }
        });

    }


    public void addChatList(ArrayList<ChatDataList> chatDataLists) {
        this.chatDataLists = chatDataLists;

    }

    @Override
    public int getItemCount() {
        return chatDataLists.size();
    }
}