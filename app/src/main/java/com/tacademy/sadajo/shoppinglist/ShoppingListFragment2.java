package com.tacademy.sadajo.shoppinglist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.home.ScheduleRegisterDialog;

import java.util.ArrayList;

import static com.tacademy.sadajo.shoppinglist.ShoppingListSample.shoppinList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment2 extends Fragment {
    public static int increment;


    ShoppingListRecyclerViewAdapter recyclerViewAdapter;
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

        View view = inflater.inflate(R.layout.fragment_shoppinglist, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView  shoppingListRecyclerView = (RecyclerView)view.findViewById(R.id.shoppingListRecyclerView2);
        shoppingListRecyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30,"bottom"); //아이템간 간격
        shoppingListRecyclerView.addItemDecoration(decoration);

        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(getContext(), shoppinList);
        shoppingListRecyclerView.setAdapter(recyclerViewAdapter);

        if(recyclerViewAdapter.getItemCount()==0){
            view = inflater.inflate(R.layout.shoppinglist_noitem_layout, container, false);
        }//쇼핑리스트 아이템이 하나도 없을 경우

        return  view;




    }




    public static class ShoppingListRecyclerViewAdapter
            extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

        private ArrayList<ShoppingListData> shoppingListDatas;
        private Context context;



        //item viewType
        private final static int HEADER_VIEW = 0;
        private final static int CONTENT_VIEW = 1;


        public ShoppingListRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
            this.context = context;
            this.shoppingListDatas = shoppingListDatas;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView countryNameTextView;
            public final TextView cityNameTextView;
            public final TextView dateTextView;
            public final ImageView productImageView;
            public final Button newScheduleButton;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                countryNameTextView = (TextView) view.findViewById(R.id.countryNameTextView);
                cityNameTextView = (TextView) view.findViewById(R.id.cityNameTextView);
                dateTextView = (TextView) view.findViewById(R.id.dateTextView);
                productImageView = (ImageView) view.findViewById(R.id.productImageView);
                newScheduleButton=(Button) view.findViewById(R.id.newScheduleButton);

            }


        }




        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


//
//            View view = LayoutInflater.from(parent.getContext()).inflate(
//                    R.layout.shoppinglist_recyclerview_item1, parent, false);
//
//
//            return new ViewHolder(view);
//

            int layoutRes = 0;
            switch (viewType) {
                case HEADER_VIEW:
                    layoutRes = R.layout.shoppinglist_recyclerview_item1_first; //첫번째 item layout
                    break;
                case CONTENT_VIEW:
                    layoutRes = R.layout.shoppinglist_recyclerview_item1; //나머지 item lyaout
                    break;
            }

            View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
            return new ViewHolder(view);

        }

        @Override
        public int getItemViewType(int position) {
            switch(position) {
                case 0:
                    return HEADER_VIEW; //첫번째 아이템 viewType
                default:
                    return CONTENT_VIEW;
            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            int viewType = getItemViewType(position); //viewType 체크

            //첫번째 아이템이 아닌 경우
            if(viewType == CONTENT_VIEW){

                holder.countryNameTextView.setText(shoppingListDatas.get(position).countryName);
                holder.cityNameTextView.setText(shoppingListDatas.get(position).cityName);
                holder.dateTextView.setText(shoppingListDatas.get(position).travelDate);
                holder.productImageView.setImageResource(shoppingListDatas.get(position).productImgae);

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
            }else{
                holder.newScheduleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ScheduleRegisterDialog dialog =new ScheduleRegisterDialog(context,R.style.CustomDialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(true);
                        dialog.show();

                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return shoppingListDatas.size();
        }
    }
}
