package com.tacademy.sadajo.mypage;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;

import java.util.ArrayList;

public class BuyListRecyclerViewAdapter
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

        public final ImageButton buyReviewButton;
        public final ImageButton buyRequestButton;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            countryEngName = (TextView) view.findViewById(R.id.buyCountryEngName);
            countryKorName = (TextView) view.findViewById(R.id.buyCountryKorName);
            userNameTextView = (TextView) view.findViewById(R.id.buyUserNameTextView);
            productTextView = (TextView) view.findViewById(R.id.buyProductTextView);
            productNameTextView = (TextView) view.findViewById(R.id.buyProductNameTextView);
            dateTextTextView = (TextView) view.findViewById(R.id.buyDateTextTextView);
            dateTextView = (TextView) view.findViewById(R.id.buyDateTextView);

            productImageView = (ImageView) view.findViewById(R.id.buyProductImageView);
            profileImageView = (ImageView) view.findViewById(R.id.buyProfileImageView);

            okButton = (Button) view.findViewById(R.id.okButton);

            buyReviewButton = (ImageButton) view.findViewById(R.id.buyReviewButton);
            buyRequestButton = (ImageButton) view.findViewById(R.id.buyRequestButton);



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

        holder.productImageView.setImageResource(R.drawable.taipei);
        holder.profileImageView.setImageResource(R.drawable.profile_empty);

        holder.okButton.setOnClickListener(new View.OnClickListener() { //사다조 요청수락버튼 클릭시
            @Override
            public void onClick(View v) {//TODO: 수정필요!

                v.setVisibility(View.GONE);
                holder.buyRequestButton.setVisibility(View.VISIBLE); //요청보기 버튼 visible
                holder.buyRequestButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getFragmentManager();
                        RequestConfirmDialogFragment requestConfirmDialogFragment = new RequestConfirmDialogFragment();
                        requestConfirmDialogFragment.show(fragmentManager, "requestConfirmDialog");

                    }
                });
                holder.buyReviewButton.setVisibility(View.VISIBLE);//후기작성버튼 visible
                holder.buyReviewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getFragmentManager();
                        ReviewDialogFragment dialog = new ReviewDialogFragment();
                        dialog.show(fragmentManager, "reviewDialog");
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

