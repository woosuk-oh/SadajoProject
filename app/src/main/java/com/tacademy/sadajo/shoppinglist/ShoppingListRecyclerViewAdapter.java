package com.tacademy.sadajo.shoppinglist;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.home.ScheduleDialogFragment;
import com.tacademy.sadajo.network.shoppinglist.CountryEngHashMap;
import com.tacademy.sadajo.network.shoppinglist.ShopListDB;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */


public class ShoppingListRecyclerViewAdapter
        extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ShopListDB> shopListDB = new ArrayList<>();
    private Context mcontext;


    //item viewType
    private final static int NO_ITEM_VIEW = 0;
    private final static int HEADER_VIEW = 1;
    private final static int CONTENT_VIEW = 2;


    public ShoppingListRecyclerViewAdapter(Context context) {
        mcontext = context;


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView countryNameTextView;
        public final TextView cityNameTextView;
        public final TextView dateTextView;
        public final ImageView productImageView;
        public final ImageButton newScheduleButton;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            countryNameTextView = (TextView) view.findViewById(R.id.countryNameTextView);
            cityNameTextView = (TextView) view.findViewById(R.id.cityNameTextView);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
            productImageView = (ImageView) view.findViewById(R.id.productImageView);
            newScheduleButton = (ImageButton) view.findViewById(R.id.newScheduleButton);

        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        int layoutRes = 0;

        switch (viewType) {
            case NO_ITEM_VIEW:
                layoutRes = R.layout.shoppinglist_noitem_layout;
                break;
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
        if (getItemCount() == 0) {
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
            CountryEngHashMap countryEngHashMap = new CountryEngHashMap();
            String countryKor = shopListDB.get(position).countryCode;
            String countryEng = countryEngHashMap.getCountryEngName(shopListDB.get(position).countryCode);
            holder.countryNameTextView.setText(countryEng);
            holder.cityNameTextView.setText(countryKor + ", " + shopListDB.get(position).cityCode);
            holder.dateTextView.setText(shopListDB.get(position).startDate + "~" + shopListDB.get(position).endDate);
            holder.productImageView.setImageResource(R.drawable.product_sample);

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
        } else {
            holder.newScheduleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentManager fragmentManager = ((AppCompatActivity) mcontext).getFragmentManager();
                    ScheduleDialogFragment dialog = new ScheduleDialogFragment();
                    dialog.show(fragmentManager, "scheduleDialog");

                }
            });
        }
    }


    public void addShopList(ArrayList<ShopListDB> shopListDBs) {
        this.shopListDB.addAll(shopListDBs);
    }

    @Override
    public int getItemCount() {
        return shopListDB.size();
    }
}