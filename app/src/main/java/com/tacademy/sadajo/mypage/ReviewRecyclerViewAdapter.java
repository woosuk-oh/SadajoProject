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

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;

import java.util.ArrayList;

public class ReviewRecyclerViewAdapter
        extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShoppingListData> shoppingListDatas;
    private Context context;

    public ReviewRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
        this.context = context;
        this.shoppingListDatas = shoppingListDatas;
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


//
//            holder.countryNameTextView.setText(shoppingListDatas.get(position).countryName);
//            holder.cityNameTextView.setText(shoppingListDatas.get(position).cityName);
//            holder.dateTextView.setText(shoppingListDatas.get(position).travelDate);
//            holder.productImageView.setImageResource(shoppingListDatas.get(position).productImgae);

        holder.userId.setText("아롱이다롱");
        holder.reviewTitleTextView.setText("아이디");
        holder.reviewDateTextView.setText("2016.11.20");
        holder.reviewContentsTextView.setText("후기후기마이페이지ㅎㅎㅎㅎㅎㅎ");
        holder.userProfileImageView.setImageResource(R.drawable.profile_empty);

//            Glide.with(GirlsApplication.getGirlsContext())
//                    .load(girlInfo)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .animate(android.R.anim.slide_in_left)
//                    .into(holder.girlsImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                    Intent intent = new Intent(GirlsApplication.getGirlsContext(), GirlsMemberDetailActivity.class);
//                    intent.putExtra("memberImage", girlsImages.get(position));
//                    intent.putExtra("memberName", holder.memberName.getText().toString());
//
//                    ActivityOptionsCompat options =
//                            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                    owner, holder.girlsImage, ViewCompat.getTransitionName(holder.girlsImage));
//
//                    ActivityCompat.startActivity(owner, intent, options.toBundle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListDatas.size();
    }
}