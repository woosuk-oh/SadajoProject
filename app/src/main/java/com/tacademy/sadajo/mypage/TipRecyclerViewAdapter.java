package com.tacademy.sadajo.mypage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

public class TipRecyclerViewAdapter
        extends RecyclerView.Adapter<TipRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShoppingListData> shoppingListDatas;
    private Context context;

    public TipRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
        this.context = context;
        this.shoppingListDatas = shoppingListDatas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView itemText; // 코멘트 내용
        public TextView itemText2; // 코멘트 입력시간
        public ImageView itemImg; //코멘트 작성자 써클 이미지뷰


        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            itemImg = (ImageView) itemView.findViewById(R.id.detail_comment_circleimage);
            itemText = (TextView) itemView.findViewById(R.id.detail_comment_body);
            itemText2 = (TextView) itemView.findViewById(R.id.detail_comment_time);


        }


    }

    @Override
    public TipRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_detail_comment_recycler, parent, false);

        return new TipRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TipRecyclerViewAdapter.ViewHolder holder, final int position) {



        holder.itemImg.setImageResource(R.drawable.sample_profile);
        holder.itemText.setText("이번주에 샀는데 두개 사면 하나 20%로 할인 해주더라구여");
        holder.itemText2.setText("2시간전");



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(context, SearchDetail.class);
//
//                intent.putExtra("key",1); //상품아이디 전달
//                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListDatas.size();
    }
}
