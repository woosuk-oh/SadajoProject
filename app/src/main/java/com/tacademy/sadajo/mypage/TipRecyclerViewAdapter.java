package com.tacademy.sadajo.mypage;

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
import com.tacademy.sadajo.network.mypage.MypageTip;
import com.tacademy.sadajo.search.searchdetail.SearchDetail;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

public class TipRecyclerViewAdapter
        extends RecyclerView.Adapter<TipRecyclerViewAdapter.ViewHolder> {

    private ArrayList<MypageTip> mypageTipArrayList;
    private Context context;

    public TipRecyclerViewAdapter(Context context) {
        this.context = context;
        this.mypageTipArrayList = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView tipContentTextView; // 코멘트 내용
        public TextView tipDateTextView; // 코멘트 입력시간
        public ImageView tipUserImageView; //코멘트 작성자 써클 이미지뷰


        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            tipUserImageView = (ImageView) itemView.findViewById(R.id.detail_comment_circleimage);
            tipContentTextView = (TextView) itemView.findViewById(R.id.detail_comment_body);
            tipDateTextView = (TextView) itemView.findViewById(R.id.detail_comment_time);


        }


    }

    @Override
    public TipRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_detail_comment_recycler, parent, false);

        return new TipRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TipRecyclerViewAdapter.ViewHolder holder, final int position) {



        holder.tipContentTextView.setText(mypageTipArrayList.get(position).content);
        holder.tipDateTextView.setText(mypageTipArrayList.get(position).date);
        Glide.with(SadajoContext.getContext())
                .load(mypageTipArrayList.get(position).userImg)
                .into(holder.tipUserImageView);



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SearchDetail.class);


                intent.putExtra("key",mypageTipArrayList.get(position).goodsCode); //상품아이디 전달
                //intent.putExtra("key2",); //상품아이디 전달
                context.startActivity(intent);


            }
        });

    }


    public void addTip(ArrayList<MypageTip> mypageTips){

        if (mypageTipArrayList != null && mypageTipArrayList.size() > 0) {
            mypageTipArrayList.removeAll(mypageTipArrayList);
        }
        mypageTipArrayList.addAll(mypageTips);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mypageTipArrayList.size();
    }
}
