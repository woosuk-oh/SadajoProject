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
public class ShoppingListFragment2 extends Fragment {
    public static int increment;


    ShoppingListRecyclerViewSecondAdapter recyclerViewAdapter;

    public ShoppingListFragment2() {
        // Required empty public constructor
    }

    public static ShoppingListFragment2 newInstance(int initValue) {
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
        RecyclerView shoppingListRecyclerView = (RecyclerView) view.findViewById(R.id.shoppingListRecyclerView2);
        shoppingListRecyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom"); //아이템간 간격
        shoppingListRecyclerView.addItemDecoration(decoration);

        recyclerViewAdapter = new ShoppingListRecyclerViewSecondAdapter(getContext(), shoppinList);
        shoppingListRecyclerView.setAdapter(recyclerViewAdapter);

        if (recyclerViewAdapter.getItemCount() == 0) {
            view = inflater.inflate(R.layout.shoppinglist_noitem_layout, container, false);
        }//쇼핑리스트 아이템이 하나도 없을 경우

        return view;


    }


}

