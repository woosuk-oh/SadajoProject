package com.tacademy.sadajo.shoppinglist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;

import static com.tacademy.sadajo.shoppinglist.ShoppingListSample.shoppinList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends Fragment {
    public static int increment;


    ShoppingListRecyclerViewAdapter recyclerViewAdapter;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    public static ShoppingListFragment newInstance(int initValue) {
        ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        shoppingListFragment.setArguments(bundle);
        return shoppingListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle initBundle = getArguments();

        View view = inflater.inflate(R.layout.shoppinglist_frament_first, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView shoppingListRecyclerView = (RecyclerView) view.findViewById(R.id.shoppingListRecyclerView1);
        shoppingListRecyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom"); //아이템간 간격
        shoppingListRecyclerView.addItemDecoration(decoration);

        recyclerViewAdapter = new ShoppingListRecyclerViewAdapter(getContext(), shoppinList);
        shoppingListRecyclerView.setAdapter(recyclerViewAdapter);

        if (recyclerViewAdapter.getItemCount() == 0) {
            view = inflater.inflate(R.layout.shoppinglist_noitem_layout, container, false);
        }//쇼핑리스트 아이템이 하나도 없을 경우

        return view;


    }


}
