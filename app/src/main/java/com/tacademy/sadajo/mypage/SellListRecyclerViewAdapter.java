package com.tacademy.sadajo.mypage;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.mypage.DealListData;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


// 사다줌 어댑터


public class SellListRecyclerViewAdapter
        extends RecyclerView.Adapter<SellListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<DealListData> dealListDatas = new ArrayList<>();
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



        // TODO 서버로 부터 받아온 상태값에 따라 보이는 버튼이 달라야 됌.

        int status = dealListDatas.get(position).status; // 서버로부터 받아온 사다조 아이템 리스트들 내용 중 특정 아이템의 status값을 비교함.
        if(status==1) { //요청 수락버튼이 보여야 하는 상태값임


            holder.okButton.setOnClickListener(new View.OnClickListener() { // TODO 사다조 요청수락버튼 클릭시 서버콜로 서버에 해당 아이템의 상태값 변경해줘야 됌.

                FragmentManager fragmentManager = ((AppCompatActivity) context).getFragmentManager();

                @Override
                public void onClick(View v) { //사다조 수락버튼 누르면. 쓰레드를 통해서 상태값을 받아와 저장시킨다.


                    RequestConfirmDialogFragment requestConfirmDialogFragment = new RequestConfirmDialogFragment();
                    requestConfirmDialogFragment.show(fragmentManager, "requestConfirmDialog");

                    v.setVisibility(View.GONE);// 사다조 요청 수락 버튼 gone 처리.


                    // 여기서 쓰레드를 하나 더 내려야됌.
                    new Thread(new Runnable() { // 버튼 눌렀을때만 처리되는 부분으로, 해당 아이템의 상태값을 변경하기 위해 서버콜을 함
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            OkHttpClient toServer;
                            toServer = OkHttpInitManager.getOkHttpClient();
                            Response response = null; // 응답
                            int itemID = dealListDatas.get(position).req_code;//서버콜 하기 위해 해당 아이템의 req_code를 가져옴
                            int statusVal=0;

                            try{
                                toServer = OkHttpInitManager.getOkHttpClient();


                                RequestBody postBody = new FormBody.Builder()
                                        .add("code", String.valueOf(itemID))
                                        .build();

                                Request request = new Request.Builder()
                                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUST_MYPAGE_DEALLIST_SAYYES))
                                        .post(postBody)
                                        .build();


                                response = toServer.newCall(request).execute();

                                if (response.isSuccessful()){
                                    String returnMessage = response.body().string();

                                    JSONObject returnmsg = new JSONObject(returnMessage);
                                    statusVal = returnmsg.getInt("status"); // 해당 아이템의 상태값을 받아옴.
                                    dealListDatas.get(position).status = statusVal; // 데이터 모델의 어레이 리스트에 저장. (어댑터 내에 존재하는 어레이)

                                }
                            }catch (Exception e){
                                Log.e("파싱에러", e.toString());
                            }finally {
                                if(response != null){
                                    response.close();
                                }
                            }
                        }
                    }).start();


                    holder.sellRequestButton.setVisibility(View.VISIBLE); //TODO 요청보기 버튼 visible. 누르면 요청 내용 받아오기 위한 서버콜 필요.
                    holder.sellRequestButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RequestConfirmDialogFragment requestConfirmDialogFragment = new RequestConfirmDialogFragment();
                            requestConfirmDialogFragment.show(fragmentManager, "requestConfirmDialog");

                        }
                    });
                    holder.sellReviewButton.setVisibility(View.VISIBLE);//후기작성버튼 visible.
                    holder.sellReviewButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ReviewDialogFragment dialog = new ReviewDialogFragment();
                            dialog.show(fragmentManager, "reviewDialog");
                            view.setSelected(true);
                        }
                    });


                }
            });
        }
        else if(status == 2){// 받아온 값이 요청 수락한 상태이면.


        }

    }

    @Override
    public int getItemCount() {
        return dealListDatas.size();
    }
}

