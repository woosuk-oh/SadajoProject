package com.tacademy.sadajo.shoppinglist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;

import java.util.ArrayList;

import static com.tacademy.sadajo.shoppinglist.ShoppingListSample.shoppinList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment2 extends Fragment {
    public static int increment;


    ShoppingListRecyclerViewSecondAdapter recyclerViewAdapter;
    public ShoppingListFragment2() {
        // Required empty public constructor
    }

    public static ShoppingListFragment2 newInstance(int initValue){
        ShoppingListFragment2 shoppingListFragment = new ShoppingListFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        shoppingListFragment.setArguments(bundle);
        return shoppingListFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle initBundle = getArguments();

        View view = inflater.inflate(R.layout.shoppinglist_fragment_second, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView  shoppingListRecyclerView = (RecyclerView)view.findViewById(R.id.shoppingListRecyclerView2);
        shoppingListRecyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30,"bottom"); //아이템간 간격
        shoppingListRecyclerView.addItemDecoration(decoration);

        recyclerViewAdapter = new ShoppingListRecyclerViewSecondAdapter(getContext(), shoppinList);
        shoppingListRecyclerView.setAdapter(recyclerViewAdapter);

        if(recyclerViewAdapter.getItemCount()==0){
            view = inflater.inflate(R.layout.shoppinglist_noitem_layout, container, false);
        }//쇼핑리스트 아이템이 하나도 없을 경우

        return  view;




    }




    public static class ShoppingListRecyclerViewSecondAdapter
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

                    }
                });

            }


        @Override
        public int getItemCount() {
            return shoppingListDatas.size();
        }
    }
}

