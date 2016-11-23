package com.tacademy.sadajo.mypage;


import android.content.Context;
import android.content.Intent;
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
import com.tacademy.sadajo.search.searchdetail.SearchDetail;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {



    ItemReviewRecyclerViewAdapter itemReviewRecyclerViewAdapter;
    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance(int initValue) {
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.mypage_fragment_item, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.itemRecyclerView);
        recyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30,"bottom"); //아이템간 간격
        recyclerView.addItemDecoration(decoration);

        itemReviewRecyclerViewAdapter = new ItemReviewRecyclerViewAdapter(getContext(), ShoppingListSample.shoppinList);
        recyclerView.setAdapter(itemReviewRecyclerViewAdapter);

        Bundle initBundle = getArguments();
        return  view;
    }

    public static class ItemReviewRecyclerViewAdapter
            extends RecyclerView.Adapter<ItemReviewRecyclerViewAdapter.ViewHolder> {

        private ArrayList<ShoppingListData> shoppingListDatas;
        private Context context;

        public ItemReviewRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
            this.context = context;
            this.shoppingListDatas = shoppingListDatas;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public ImageView itemProductImageView;
            public ImageView itemFlagImageView;
            public TextView itemCountryNameTextView;
            public TextView itemProductNameTextView;



            public ViewHolder(final View itemView) {
                super(itemView);
                mView = itemView;
                itemProductImageView = (ImageView) itemView.findViewById(R.id.itemProductImageView);
                itemFlagImageView = (ImageView) itemView.findViewById(R.id.itemFlagImageView);
                itemCountryNameTextView = (TextView) itemView.findViewById(R.id.itemCountryNameTextView);
                itemProductNameTextView = (TextView) itemView.findViewById(R.id.itemProductNameTextView);



            }
        }

        @Override
        public ItemReviewRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.mypage_item_recyclerview_item, parent, false);

            return new ItemReviewRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ItemReviewRecyclerViewAdapter.ViewHolder holder, final int position) {


//
//            holder.countryNameTextView.setText(shoppingListDatas.get(position).countryName);
//            holder.cityNameTextView.setText(shoppingListDatas.get(position).cityName);
//            holder.dateTextView.setText(shoppingListDatas.get(position).travelDate);
//            holder.productImageView.setImageResource(shoppingListDatas.get(position).productImgae);

            holder.itemProductImageView.setImageResource(R.drawable.detail_item_img_sample);
            holder.itemProductNameTextView.setText("산타마리아노벨라 향수");
            holder.itemFlagImageView.setImageResource(R.drawable.flag);
            holder.itemCountryNameTextView.setText("이탈리아");


       /* if(position == mItems.size()+1)
        {

            holder.itemImage.setImageResource(R.drawable.search_button);
        }*/



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
                    Intent intent = new Intent(context, SearchDetail.class);
                    intent.putExtra("key", holder.itemProductNameTextView.getText());
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return shoppingListDatas.size();
        }
    }

}
