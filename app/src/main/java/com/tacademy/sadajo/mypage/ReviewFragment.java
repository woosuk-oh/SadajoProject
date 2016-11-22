package com.tacademy.sadajo.mypage;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {


    ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(int initValue){
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        reviewFragment.setArguments(bundle);
        return reviewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage_review, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30,"bottom"); //아이템간 간격
        recyclerView.addItemDecoration(decoration);

        reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(getContext(), ShoppingListSample.shoppinList);
        recyclerView.setAdapter(reviewRecyclerViewAdapter);

        Bundle initBundle = getArguments();
        return  view;
    }

    public static class ReviewRecyclerViewAdapter
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
                userId = (TextView) view.findViewById(R.id.userId);
                reviewTitleTextView = (TextView) view.findViewById(R.id.reviewTitleTextView);
                reviewDateTextView = (TextView) view.findViewById(R.id.reviewDateTextView);
                reviewContentsTextView = (TextView) view.findViewById(R.id.reviewContentsTextView);
                userProfileImageView = (ImageView) view.findViewById(R.id.userProfileImageView);

            }
        }

        @Override
        public ReviewRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.mypage_recyclerview_item1, parent, false);

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
}
