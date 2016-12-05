package com.tacademy.sadajo.search.searchlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
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

public class SearchListRecyclerAdapter extends RecyclerView.Adapter<SearchListRecyclerAdapter.ViewHolder>{
    private Context context;

    private ArrayList<SearchGoodsDB> searchgoodsDBs;



    private static int TYPE_HEADER = 0;
    private static int TYPE_BODY = 1;
    private static int TYPE_FOOTER = 3;



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
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 레이아웃을 뷰(홀더)에 붙이기 위한 인플레이터임.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_grid_item, parent, false);
      //  Log.d("ItemSize", ""+searchgoodsDBs.size());
      /*  if(mItems.size() == lastPosition2){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_detail_view_pager_item, parent, false);
            Log.d("ItemSize", ""+mItems.size());
        }*/

        ViewHolder holder = new ViewHolder(v);





        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        //TODO 서치리스트 리싸이클러 어댑터 홀더부분 수정,
     //    holder.itemImage.setImageResource(mItems.get(position).image);


        holder.itemName.setText(searchgoodsDBs.get(position).getGoods_name());
        holder.countryName.setText(searchgoodsDBs.get(position).getCountry());

        Glide.with(SadajoContext.getContext())
                .load(searchgoodsDBs.get(position).getItem_img())
                .into(holder.itemImage);

        setAnimation(holder.itemContainer, position);

        // 리싸이클러뷰에서 버튼 동적할당. 버튼이 있는 리니어 레이아웃에서 createTagButton 메소드 실행



        for (int i = 0; i < 5; i++) {

            holder.hashbutton.addView(createTagButton("버튼테스트", i));

          /*  if(holder.hashbutton> 0)
*/
        }



        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchDetail.class);
                intent.putExtra("key", searchgoodsDBs.get(position).getItem_id());
                intent.putExtra("key2", searchgoodsDBs.get(position).getGoods_name());
                intent.putExtra("key3", searchgoodsDBs.get(position).getCountry());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        if(searchgoodsDBs.size() == 0) {
        return 0;
        }
        return searchgoodsDBs.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView itemImage;
        public TextView itemName;
        public TextView countryName;
        LinearLayout itemContainer;
        LinearLayout hashbutton;





        public ViewHolder(final View itemView) {
            super(itemView);

            itemImage = (ImageView) itemView.findViewById(R.id.search_item_image);
            itemName = (TextView) itemView.findViewById(R.id.search_item_name);
            countryName = (TextView) itemView.findViewById(R.id.search_country_name);
            itemContainer = (LinearLayout) itemView.findViewById(R.id.card_back);
            hashbutton = (LinearLayout) itemView.findViewById(R.id.list_item_hash_button);



        }
    }

    private void setAnimation(View viewToAnimate, int position){

        // 새로 보여지는 뷰라면 애니메이션을 해줌 (새로운 아이템마다 왼쪽에서 날라오는 이벤트 발생.)

        if(position > lastPosition)
        {
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



        // TODO 버튼 셋 온클릭 리스너 달아줘야함.
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
