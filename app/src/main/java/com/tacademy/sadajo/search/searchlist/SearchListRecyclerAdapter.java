package com.tacademy.sadajo.search.searchlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.Search.SearchGoodsDB;
import com.tacademy.sadajo.search.searchdetail.SearchDetail;

import java.util.ArrayList;

/**
 * Created by woosuk on 2016-11-15.
 */


// Search List Activity 에 들어갈 리싸이클러뷰. 어댑터+홀더


public class SearchListRecyclerAdapter extends RecyclerView.Adapter<SearchListRecyclerAdapter.ViewHolder> {
    private Context context;

    private ArrayList<SearchGoodsDB> searchgoodsDBs;


    private final static int NO_ITEM_VIEW = 0;
    private final static int CONTENT_VIEW = 1;


    private int lastPosition = -1;
    private int lastPosition2 = +1;


    public SearchListRecyclerAdapter(Context mCotext, ArrayList<SearchGoodsDB> searchGoodsDBs) {

/*        mItems = items;*/
        context = mCotext;
        this.searchgoodsDBs = searchGoodsDBs;
    /*    TYPE_FOOTER = items.size() + 1;*/
    }

    @Override
    public int getItemViewType(int position) {

        if (getItemCount() == 0) {
            return NO_ITEM_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        int layoutRes = 0;

        switch (viewType) {
            case NO_ITEM_VIEW:
                layoutRes = R.layout.search_list_noitem;
                Log.e("SearchList", "Search 리스트 항목 없음");
                break;
            case CONTENT_VIEW:
                layoutRes = R.layout.search_list_grid_item; //나머지 item lyaout
                break;
        }


        // 레이아웃을 뷰(홀더)에 붙이기 위한 인플레이터임.
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        ViewHolder holder = new ViewHolder(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.itemName.setText(searchgoodsDBs.get(position).getGoods_name());
        holder.countryName.setText(searchgoodsDBs.get(position).getCountry());

        Glide.with(SadajoContext.getContext())
                .load(searchgoodsDBs.get(position).getItem_img())
                .centerCrop() // 이미지를 중앙에 맞게 자른다.
                .thumbnail(0.1f)
                .into(holder.itemImage);

        Glide.with(SadajoContext.getContext())
                .load(searchgoodsDBs.get(position).getCountry_img())
                .into(holder.cuntryImg);

        setAnimation(holder.itemContainer, position);

        // 리싸이클러뷰에서 버튼 동적할당. 버튼이 있는 리니어 레이아웃에서 createTagButton 메소드 실행


        for (int i = 0; i < 5; i++) {

            holder.hashbutton.addView(createTagButton("버튼테스트", i));

        }


        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchDetail.class);
                intent.putExtra("key", searchgoodsDBs.get(position).getGoods_code());
                intent.putExtra("key2", searchgoodsDBs.get(position).getGoods_name());
                intent.putExtra("key3", searchgoodsDBs.get(position).getCountry());
                intent.putExtra("key4", searchgoodsDBs.get(position).getCountry_img());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {

        return searchgoodsDBs.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        public TextView itemName;
        public TextView countryName;
        public ImageView cuntryImg;
        LinearLayout itemContainer;
        LinearLayout hashbutton;


        public ViewHolder(final View itemView) {
            super(itemView);

            itemImage = (ImageView) itemView.findViewById(R.id.search_item_image);
            cuntryImg = (ImageView) itemView.findViewById(R.id.cuntry_image);

            itemName = (TextView) itemView.findViewById(R.id.search_item_name);
            countryName = (TextView) itemView.findViewById(R.id.search_country_name);
            itemContainer = (LinearLayout) itemView.findViewById(R.id.card_back);
            hashbutton = (LinearLayout) itemView.findViewById(R.id.list_item_hash_button);

            itemName.setSelected(true);


        }
    }

    private void setAnimation(View viewToAnimate, int position) {

        // 새로 보여지는 뷰라면 애니메이션을 해줌 (새로운 아이템마다 왼쪽에서 날라오는 이벤트 발생.)

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    //Button 생성 메소드
    public Button createTagButton(String str, int i) {
        Button button = new Button(context);
        button.setText(str); //서버로부터 받아온 tag text set
        button.setBackgroundResource(R.drawable.tag_button_file); //tag ninepatch background적용

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = 69;


        // TODO 버튼 셋 (해시버튼) 온클릭 리스너 달아줘야함.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        button.setPadding(15, 0, 15, 0); // left,right padding : 3
        params.setMargins(0, 0, 45, 45); // top, right margin : 15
        button.setGravity(Gravity.CENTER); //gravity : center
        button.setTextSize(13);// textsize : 13sp
        button.setTypeface(null, Typeface.NORMAL);//textstyle : Nanum M
        button.setLayoutParams(params);
        button.setTag("HomeTag");
        button.setId(i);
        //button.setOnClickListener(buttonClickListener);
        return button;
//        list_item_hash_button.addView(button); // button added


    }



/*
    public void clearData() { //중복 제거용인데 사용 안함. onResume에서 걍 다시 execute해주므로,
        int size = this.searchgoodsDBs.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.searchgoodsDBs.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }*/
}
