package com.tacademy.sadajo.chatting;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;

import java.util.ArrayList;

public class ChattingRecyclerViewAdapter
        extends RecyclerView.Adapter<ChattingRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShoppingListData> shoppingListDatas;
    private Context context;

    public ChattingRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
        this.context = context;
        this.shoppingListDatas = shoppingListDatas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView chattingNameTextView;
        public final TextView chattingContentTextView;
        public final TextView chattingDateTextView;
        public final ImageView chattingProfileImageView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            chattingNameTextView = (TextView) view.findViewById(R.id.chattingNameTextView);
            chattingContentTextView = (TextView) view.findViewById(R.id.chattingContentTextView);
            chattingDateTextView = (TextView) view.findViewById(R.id.chattingDateTextView);
            chattingProfileImageView = (ImageView) view.findViewById(R.id.chattingProfileImageView);

        }
    }

    @Override
    public ChattingRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.chatting_recyclerview_item, parent, false);

        return new ChattingRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChattingRecyclerViewAdapter.ViewHolder holder, final int position) {


//            holder.shoppinglistCountryNameTextView.setText(shoppingListDatas.get(position).getCountryName().toString());
//            holder.shoppinglistDateTextView.setText(shoppingListDatas.get(position).getTravelDate().toString());
//            holder.shoppinglistFolderImageView.setImageResource(R.drawable.mark);

        holder.chattingNameTextView.setText("채팅제목");
        holder.chattingContentTextView.setText("안녕하세요");
        holder.chattingDateTextView.setText("2016.11.20");
        holder.chattingProfileImageView.setImageResource(R.drawable.product_sample);


//            Glide.with(GirlsApplication.getGirlsContext())
//                    .load(girlInfo)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .animate(android.R.anim.slide_in_left)
//                    .into(holder.girlsImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Intent intent = new Intent(GirlsApplication.getGirlsContext(), GirlsMemberDetailActivity.class);
//                    intent.putExtra("memberImage", girlsImages.get(position));
//                    intent.putExtra("memberName", holder.memberName.getText().toString());
//
//                    ActivityOptionsCompat options =
//                            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                    owner, holder.girlsImage, ViewCompat.getTransitionName(holder.girlsImage));
//
//                    ActivityCompat.startActivity(owner, intent, options.toBundle());
                Intent intent = new Intent(context, ChattingDetailActivity.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingListDatas.size();
    }
}