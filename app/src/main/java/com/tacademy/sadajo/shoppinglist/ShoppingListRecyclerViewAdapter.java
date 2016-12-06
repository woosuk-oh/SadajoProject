package com.tacademy.sadajo.shoppinglist;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.home.ScheduleDialogFragment;
import com.tacademy.sadajo.network.shoppinglist.ShopListDB;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */


public class ShoppingListRecyclerViewAdapter
        extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShopListDB> shopListDB;
    private Context mContext;


    //item viewType
    private final static int NO_ITEM_VIEW = 0;
    private final static int HEADER_VIEW = 1;
    private final static int CONTENT_VIEW = 2;


    public ShoppingListRecyclerViewAdapter(Context context) {
        mContext = context;
        shopListDB = new ArrayList<>();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView countryNameTextView;
        public final TextView cityNameTextView;
        public final TextView dateTextView;
        public final TextView shoplistCountTextView;
        public final TextView productEmptyTextView;
        public final ImageView productImageView;
        public final ImageButton newScheduleButton;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            countryNameTextView = (TextView) view.findViewById(R.id.shopListCountryNameTextView);
            cityNameTextView = (TextView) view.findViewById(R.id.shopListCityNameTextView);
            dateTextView = (TextView) view.findViewById(R.id.shopListDateTextView);
            shoplistCountTextView = (TextView) view.findViewById(R.id.shopListCountTextView);
            productEmptyTextView = (TextView) view.findViewById(R.id.shopListProductEmptyTextView);
            productImageView = (ImageView) view.findViewById(R.id.shopListProductImageView);
            newScheduleButton = (ImageButton) view.findViewById(R.id.shopListNewScheduleButton);

        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        int layoutRes = 0;

        switch (viewType) {
            case NO_ITEM_VIEW: //리스트가 빈 경우
                layoutRes = R.layout.shoppinglist_noitem_layout;
                break;
            case HEADER_VIEW:
                layoutRes = R.layout.shoppinglist_recyclerview_item_first; //첫번째 item layout
                break;
            case CONTENT_VIEW:
                layoutRes = R.layout.shoppinglist_recyclerview_item; //나머지 item lyaout
                break;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public int getItemViewType(int position) {
        if (shopListDB.size() == 0) { //item없을 때
            return NO_ITEM_VIEW;
        } else if (position == 0) {

            return HEADER_VIEW; //첫번째 아이템 viewType
        } else {
            return CONTENT_VIEW;

        }

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        int viewType = getItemViewType(position); //viewType 체크


        //첫번째 아이템이 아닌 경우
        if (viewType == CONTENT_VIEW) {
            String countryKor = shopListDB.get(position).countryNameKor;
            holder.countryNameTextView.setText(shopListDB.get(position).countryNameEng);
            holder.cityNameTextView.setText(countryKor + ", " + shopListDB.get(position).cityName);
            holder.dateTextView.setText(shopListDB.get(position).startDate + "~" + shopListDB.get(position).endDate);
            //           holder.productImageView.setImageResource(R.drawable.product_sample);
            // holder.shoplistCountTextView.setText("99+");

            if (shopListDB.get(position).goodsCount == 0) { //goods count가 0일 때 widget Visibility설정
                holder.shoplistCountTextView.setVisibility(View.GONE);
                holder.productEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                holder.shoplistCountTextView.setVisibility(View.VISIBLE);
                holder.shoplistCountTextView.setText(String.valueOf(shopListDB.get(position).goodsCount));
                holder.productEmptyTextView.setVisibility(View.GONE);


            }

            Glide.with(SadajoContext.getContext())//productImage
                    .load(shopListDB.get(position).img)
                    .into(holder.productImageView);


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, LikeListDetailActivity.class); //LikeList의 Detail 액티비티로 이동
                    intent.putExtra("listCode", shopListDB.get(position).listCode); //listCode넘겨줌
                    intent.putExtra("countryName", shopListDB.get(position).countryNameKor.toString()); //국가명 넘겨줌
                    Log.e("shopListCode", shopListDB.get(position).listCode.toString());
                    mContext.startActivity(intent);
                }
            });
        } else if (viewType == HEADER_VIEW) {
            holder.newScheduleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentManager fragmentManager = ((AppCompatActivity) mContext).getFragmentManager();
                    ScheduleDialogFragment dialog = new ScheduleDialogFragment();
                    dialog.show(fragmentManager, "scheduleDialog");

                }
            });
        }
    }


    public void addShopList(ArrayList<ShopListDB> shopLists) {

        if (shopListDB != null && shopListDB.size() > 0) {
            shopListDB.removeAll(shopLists);
        }
        shopListDB.addAll(shopLists);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (shopListDB.size() == 0) {
            return 0;
        } else {
            return shopListDB.size();
        }
    }
}