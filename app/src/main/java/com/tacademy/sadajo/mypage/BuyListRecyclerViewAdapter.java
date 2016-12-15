package com.tacademy.sadajo.mypage;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.mypage.BuyListData;

import java.util.ArrayList;

public class BuyListRecyclerViewAdapter
        extends RecyclerView.Adapter<BuyListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<BuyListData> buyListDatas;
    private Context context;

    public BuyListRecyclerViewAdapter(Context context, ArrayList<BuyListData> buyListDatas) {
        this.context = context;
        this.buyListDatas = buyListDatas;

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

        public final TextView buyRequestTextView;

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

            buyRequestTextView = (TextView) view.findViewById(R.id.buyRequestTextView);

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

        holder.countryEngName.setText(buyListDatas.get(position).country_name);

        holder.countryKorName.setText(buyListDatas.get(position).country_name);
        holder.userNameTextView.setText(buyListDatas.get(position).nick);
        holder.productNameTextView.setText(buyListDatas.get(position).goods_name);
        holder.dateTextView.setText(buyListDatas.get(position).thedate);

        Glide.with(SadajoContext.getContext())
                .load(buyListDatas.get(position).carr_img)

                .into(holder.profileImageView);

        Glide.with(SadajoContext.getContext())
                .load(buyListDatas.get(position).country_img)


                .into(holder.productImageView);


        // TODO 서버콜 이후에 사다조 상태값이 거래중이면 아래 비지빌리티 gone,
        // 후기작성 버튼으로 변경, 상태값을 로컬 데이터모델에서 저장.
        // runonui Thread 사용,
        // 스크롤로 움직였다가 돌아와도 적용되게 하려면 멤버변수에 상태값을 저장해둬야함. 서버콜을 보낼때 로컬의 데이터모델에 저장해둘것.
        holder.buyRequestTextView.setVisibility(View.VISIBLE);

        //if(buyListDatas)


      /*  holder.okButton.setOnClickListener(new View.OnClickListener() { //사다조 요청수락버튼 클릭시

            FragmentManager fragmentManager = ((AppCompatActivity) context).getFragmentManager();

            @Override
            public void onClick(View v) {//TODO: 수정필요!


                RequestConfirmDialogFragment requestConfirmDialogFragment = new RequestConfirmDialogFragment();
                requestConfirmDialogFragment.show(fragmentManager, "requestConfirmDialog");
                v.setVisibility(View.GONE);

                holder.buyRequestButton.setVisibility(View.VISIBLE); //요청보기 버튼 visible
                holder.buyRequestButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestConfirmDialogFragment requestConfirmDialogFragment = new RequestConfirmDialogFragment();
                        requestConfirmDialogFragment.show(fragmentManager, "requestConfirmDialog");

                    }
                });
                holder.buyReviewButton.setVisibility(View.VISIBLE);//후기작성버튼 visible
                holder.buyReviewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReviewDialogFragment dialog = new ReviewDialogFragment();
                        dialog.show(fragmentManager, "reviewDialog");
                        view.setSelected(true);
                    }
                });


            }
        });

*/
    }

    @Override
    public int getItemCount() {
        return buyListDatas.size();
    }
}

