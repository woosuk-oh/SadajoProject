package com.tacademy.sadajo.mypage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;

import java.util.ArrayList;


public class BuyListFragment extends Fragment {


    BuyListRecyclerViewAdapter recyclerViewAdapter;

    public BuyListFragment() {
    }


    public static BuyListFragment newInstance(int initValue) {
        BuyListFragment fragment = new BuyListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle initBundle = getArguments();


        //layout3

        View view = inflater.inflate(R.layout.mypage_buylist_fragment, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(72, "left");
        CustomRecyclerDecoration decoration2 = new CustomRecyclerDecoration(72, "right");
        recyclerView.addItemDecoration(decoration);
        recyclerView.addItemDecoration(decoration2);
        recyclerView.setLayoutManager(layoutManager);


        recyclerViewAdapter = new BuyListRecyclerViewAdapter(getContext(), ShoppingListSample.shoppinList);
        recyclerView.setAdapter(recyclerViewAdapter);


        return view;

    }

    public static class BuyListRecyclerViewAdapter
            extends RecyclerView.Adapter<BuyListRecyclerViewAdapter.ViewHolder> {

        private ArrayList<ShoppingListData> shoppingListDatas;
        private Context context;

        public BuyListRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
            this.context = context;
            this.shoppingListDatas = shoppingListDatas;

        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            public final TextView countryEngName;
            public final TextView countryKorName;
            public final TextView userNameTextView;
            public final TextView productTextView;
            public final TextView productNameTextView;
            public final TextView dateTextTextView;
            public final TextView dateTextView;

            public final ImageView productImageView;
            public final ImageView profileImageView;

            public final Button okButton;

            public final ImageButton testReview;
            public final ImageButton testRequest;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                countryEngName = (TextView) view.findViewById(R.id.countryEngName);
                countryKorName = (TextView) view.findViewById(R.id.countryKorName);
                userNameTextView = (TextView) view.findViewById(R.id.userNameTextView);
                productTextView = (TextView) view.findViewById(R.id.productTextView);
                productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
                dateTextTextView = (TextView) view.findViewById(R.id.dateTextTextView);
                dateTextView = (TextView) view.findViewById(R.id.dateTextView);

                productImageView = (ImageView) view.findViewById(R.id.productImageView);
                profileImageView = (ImageView) view.findViewById(R.id.profileImageView);

                okButton = (Button) view.findViewById(R.id.okButton);

                testReview = (ImageButton)view.findViewById(R.id.testReview);
                testRequest = (ImageButton)view.findViewById(R.id.testRequest);


            }
        }


        @Override
        public BuyListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.mypage_buysell_recyclerview_item, parent, false);


            return new BuyListRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final BuyListRecyclerViewAdapter.ViewHolder holder, final int position) {

            holder.countryEngName.setText("PHILIPPINES");
            holder.countryKorName.setText("필리핀, 마닐라");
            holder.userNameTextView.setText("닉네임");
            holder.productNameTextView.setText("산타마리아노벨라 향수 최대여기까");
            holder.dateTextView.setText("2016.11.20");

            holder.productImageView.setImageResource(R.drawable.boracay_sample);
            holder.profileImageView.setImageResource(R.drawable.profile_empty);

            holder.okButton.setOnClickListener(new View.OnClickListener() { //사다조 요청수락버튼 클릭시
                @Override
                public void onClick(View v) {

                    v.setVisibility(View.GONE);
                    holder.testRequest.setVisibility(View.VISIBLE); //요청보기 버튼 visible
                    holder.testReview.setVisibility(View.VISIBLE);//후기작성버튼 visible
                    holder.testReview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            view.setSelected(true);
                        }
                    });

                }
            });

        }

        @Override
        public int getItemCount() {
            return shoppingListDatas.size();
        }
    }


}
