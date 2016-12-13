package com.tacademy.sadajo.mypage;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.mypage.DealListData;

import java.util.ArrayList;


// 사다줌 어댑터


public class SellListRecyclerViewAdapter
        extends RecyclerView.Adapter<SellListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<DealListData> dealListDatas;
    private Context context;

    public SellListRecyclerViewAdapter(Context context, ArrayList<DealListData> dealListDatas) {
        this.context = context;
        this.dealListDatas = dealListDatas;

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

        public final ImageButton sellReviewButton;
        public final ImageButton sellRequestButton;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            countryEngName = (TextView) view.findViewById(R.id.sellCountryEngName);
            countryKorName = (TextView) view.findViewById(R.id.sellCountryKorName);
            userNameTextView = (TextView) view.findViewById(R.id.sellUserNameTextView);
            productTextView = (TextView) view.findViewById(R.id.sellProductTextView);
            productNameTextView = (TextView) view.findViewById(R.id.sellProductNameTextView);
            dateTextTextView = (TextView) view.findViewById(R.id.sellDateTextTextView);
            dateTextView = (TextView) view.findViewById(R.id.sellDateTextView);

            productImageView = (ImageView) view.findViewById(R.id.sellCountryImageView);
            profileImageView = (ImageView) view.findViewById(R.id.sellProfileImageView);

            okButton = (Button) view.findViewById(R.id.sellOkButton);

            sellReviewButton = (ImageButton) view.findViewById(R.id.sellReviewButton);
            sellRequestButton = (ImageButton) view.findViewById(R.id.sellRequestButton);


        }
    }


    @Override
    public SellListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.mypage_deal_sell_recyclerview_item, parent, false);


        return new SellListRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SellListRecyclerViewAdapter.ViewHolder holder, final int position) {

  /*      CountryCodeHashMap countryCodeHashMap = new CountryCodeHashMap();

        String countryCode = countryCodeHashMap
                .getCountryCode(dealListDatas.get(position).country_name);
        dealListDatas.get(position).country_name = countryCode;
        Log.d("countryCode","국가명"+countryCode);
*/

        holder.countryEngName.setText(dealListDatas.get(position).country_name);

        holder.countryKorName.setText(dealListDatas.get(position).country_name);
        holder.userNameTextView.setText(dealListDatas.get(position).nick);
        holder.productNameTextView.setText(dealListDatas.get(position).goods_name);
        holder.dateTextView.setText(dealListDatas.get(position).thedate);

        Glide.with(SadajoContext.getContext())
                .load(dealListDatas.get(position).req_img)


                .into(holder.profileImageView);

        Glide.with(SadajoContext.getContext())
                .load(dealListDatas.get(position).country_img)


                .into(holder.productImageView);

        holder.okButton.setOnClickListener(new View.OnClickListener() { //사다조 요청수락버튼 클릭시

  //          FragmentManager fragmentManager = ((AppCompatActivity) context).getFragmentManager();

            @Override
            public void onClick(View v) {//TODO: 수정필요!


                Toast.makeText(SadajoContext.getContext(),"서비스 준비중",Toast.LENGTH_SHORT).show();

//                RequestConfirmDialogFragment requestConfirmDialogFragment = new RequestConfirmDialogFragment();
//                requestConfirmDialogFragment.show(fragmentManager, "requestConfirmDialog");
//
//                v.setVisibility(View.GONE);
//
//                holder.sellRequestButton.setVisibility(View.VISIBLE); //요청보기 버튼 visible
//                holder.sellRequestButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        RequestConfirmDialogFragment requestConfirmDialogFragment = new RequestConfirmDialogFragment();
//                        requestConfirmDialogFragment.show(fragmentManager, "requestConfirmDialog");
//
//                    }
//                });
//                holder.sellReviewButton.setVisibility(View.VISIBLE);//후기작성버튼 visible
//                holder.sellReviewButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ReviewDialogFragment dialog = new ReviewDialogFragment();
//                        dialog.show(fragmentManager, "reviewDialog");
//                        view.setSelected(true);
//                    }
//                });
//
//
            }
        });


    }

    @Override
    public int getItemCount() {
        return dealListDatas.size();
    }
}

