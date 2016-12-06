package com.tacademy.sadajo.search.searchdetail;

import android.content.Context;
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
import com.tacademy.sadajo.network.Search.SeachDetail.TipsDB;

import java.util.ArrayList;

/**
 * Created by woosuk on 2016-11-15.
 */



// Search List Activity 에 들어갈 리싸이클러뷰. 어댑터+홀더

public class DetailCommentRecyclerAdapter extends RecyclerView.Adapter<DetailCommentRecyclerAdapter.ViewHolder>{
    private Context context;
    private ArrayList<TipsDB> mItems;





    public DetailCommentRecyclerAdapter(ArrayList<TipsDB> items, Context mCotext) {

        mItems = items;
        context = mCotext;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 레이아웃을 뷰(홀더)에 붙이기 위한 인플레이터임.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_detail_comment_recycler, parent, false);

        Log.d("ItemSize", ""+mItems.size());


        ViewHolder holder = new ViewHolder(v);



        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


      //  holder.itemText.setText(mItems.get(position).getWriter());
        holder.itemText.setText(mItems.get(position).getTips_content());
        Glide.with(SadajoContext.getContext())
                .load(mItems.get(position).getTips_user_img())
                .placeholder(R.drawable.search_profile_105)
                .into(holder.itemImg);
    }

    @Override
    public int getItemCount() {
        return mItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemText; // 코멘트 내용
        public TextView itemText2; // 코멘트 입력시간
        public ImageView itemImg; //코멘트 작성자 써클 이미지뷰


        public ViewHolder(final View itemView) {
            super(itemView);

            itemImg = (ImageView) itemView.findViewById(R.id.detail_comment_circleimage);
            itemText = (TextView) itemView.findViewById(R.id.detail_comment_body);
            itemText2 = (TextView) itemView.findViewById(R.id.detail_comment_time);


        }
    }






}
