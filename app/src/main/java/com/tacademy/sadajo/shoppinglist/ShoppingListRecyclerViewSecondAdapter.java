package com.tacademy.sadajo.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.home.ShoppingListFirstDetailActivity;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */

public class ShoppingListRecyclerViewSecondAdapter
        extends RecyclerView.Adapter<ShoppingListRecyclerViewSecondAdapter.ViewHolder> {

    private ArrayList<ShoppingListData> shoppingListDatas;
    private Context context;


    public ShoppingListRecyclerViewSecondAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
        this.context = context;
        this.shoppingListDatas = shoppingListDatas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView countryNameTextView;
        public final TextView cityNameTextView;
        public final ImageView productImageView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            countryNameTextView = (TextView) view.findViewById(R.id.countryNameTextView);
            cityNameTextView = (TextView) view.findViewById(R.id.cityNameTextView);

            productImageView = (ImageView) view.findViewById(R.id.productImageView);


        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.shoppinglist_recyclerview2_item, parent, false);


        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.countryNameTextView.setText("ITALY");
        holder.cityNameTextView.setText("이탈리아");
        holder.productImageView.setImageResource(R.drawable.sample_img);

//            Glide.with(GirlsApplication.getGirlsContext())
//                    .load(girlInfo)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .animate(android.R.anim.slide_in_left)
//                    .into(holder.girlsImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                    Intent intent = new Intent(GirlsApplication.getGirlsContext(), GirlsMemberDetailActivity.class);
//                    intent.putExtra("memberImage", girlsImages.get(position));
//                    intent.putExtra("memberName", holder.memberName.getText().toString());
//
//                    ActivityOptionsCompat options =
//                            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                    owner, holder.girlsImage, ViewCompat.getTransitionName(holder.girlsImage));
//
//                    ActivityCompat.startActivity(owner, intent, options.toBundle());

                Intent intent = new Intent(context, ShoppingListFirstDetailActivity.class);
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return shoppingListDatas.size();
    }
}