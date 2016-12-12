package com.tacademy.sadajo.mypage;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.mypage.MypageReview;

import java.util.ArrayList;

public class ReviewRecyclerViewAdapter
        extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {

    private ArrayList<MypageReview> mypageReviews;
    private Context context;

    public ReviewRecyclerViewAdapter(Context context) {
        this.context = context;
        this.mypageReviews = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView userId;
        public final TextView reviewTitleTextView;
        public final TextView reviewDateTextView;
        public final TextView reviewContentsTextView;
        public final ImageView userProfileImageView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            userId = (TextView) view.findViewById(R.id.reviewUserId);
            reviewTitleTextView = (TextView) view.findViewById(R.id.reviewTitleTextView);
            reviewDateTextView = (TextView) view.findViewById(R.id.reviewDateTextView);
            reviewContentsTextView = (TextView) view.findViewById(R.id.reviewContentsTextView);
            userProfileImageView = (ImageView) view.findViewById(R.id.reviewUserProfileImageView);

        }
    }

    @Override
    public ReviewRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.mypage_review_recyclerview_item1, parent, false);

        return new ReviewRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewRecyclerViewAdapter.ViewHolder holder, final int position) {


        holder.userId.setText(mypageReviews.get(position).userName);
        holder.reviewTitleTextView.setText(mypageReviews.get(position).userName);
        holder.reviewDateTextView.setText(mypageReviews.get(position).date);
        holder.reviewContentsTextView.setText(mypageReviews.get(position).content);

        Glide.with(SadajoContext.getContext())
                .load(mypageReviews.get(position).userImg)
                .into(holder.userProfileImageView);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    public void addReview(ArrayList<MypageReview> listDB) {

        if (mypageReviews != null && mypageReviews.size() > 0) {
            mypageReviews.removeAll(mypageReviews);
        }
        mypageReviews.addAll(listDB);
        notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return mypageReviews.size();
    }
}