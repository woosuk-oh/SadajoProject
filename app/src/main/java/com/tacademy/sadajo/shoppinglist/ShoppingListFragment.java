package com.tacademy.sadajo.shoppinglist;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.MainActivity;
import com.tacademy.sadajo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends Fragment {
    public static int increment;
    static MainActivity owner ;
    ArrayList<ShoppingListData> shoppingListDatas = new ArrayList<>();

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    public static ShoppingListFragment newInstance(int initValue){
        ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        shoppingListFragment.setArguments(bundle);
        return shoppingListFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        RecyclerView  shoppingListRecyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_shoppinglist, container, false);
        Bundle initBundle = getArguments();
        increment += initBundle.getInt("value");
        owner = (MainActivity)getActivity();
        shoppingListRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        shoppingListRecyclerView.setAdapter(new ShoppingListRecyclerViewAdapter(getContext(),
                ShoppingListSample.shoppinList));


        return  shoppingListRecyclerView;

    }

    public static class ShoppingListRecyclerViewAdapter
            extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

        private ArrayList<ShoppingListData> shoppingListDatas;
        private Context context;

        public ShoppingListRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
            this.context = context;
            this.shoppingListDatas = shoppingListDatas;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView shoppinglistCountryNameTextView;
            public final TextView shoppinglistDateTextView;
            public final ImageView shoppinglistFolderImageView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                shoppinglistCountryNameTextView = (TextView) view.findViewById(R.id.shoppinglistCountryNameTextView);
                shoppinglistDateTextView = (TextView) view.findViewById(R.id.shoppinglistDateTextView);
                shoppinglistFolderImageView = (ImageView) view.findViewById(R.id.shoppinglistFolderImageView);

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.shoppinglist_recyclerview_item1, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


//            holder.shoppinglistCountryNameTextView.setText(shoppingListDatas.get(position).getCountryName().toString());
//            holder.shoppinglistDateTextView.setText(shoppingListDatas.get(position).getTravelDate().toString());
//            holder.shoppinglistFolderImageView.setImageResource(R.drawable.mark);

            holder.shoppinglistCountryNameTextView.setText(shoppingListDatas.get(position).countryName);
            holder.shoppinglistDateTextView.setText(shoppingListDatas.get(position).travelDate);
            holder.shoppinglistFolderImageView.setImageResource(R.drawable.mark);

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
