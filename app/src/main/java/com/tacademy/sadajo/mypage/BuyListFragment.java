package com.tacademy.sadajo.mypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;


public class BuyListFragment extends Fragment {


    BuyListRecyclerViewAdapter recyclerViewAdapter;

    public BuyListFragment() {
    }


    public static BuyListFragment newInstance(int initValue) {
        BuyListFragment fragment = new BuyListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle initBundle = getArguments();


        //layout3

        View view = inflater.inflate(R.layout.mypage_buylist_fragment, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(72, "left");
        CustomRecyclerDecoration decoration2 = new CustomRecyclerDecoration(72, "right");
        recyclerView.addItemDecoration(decoration);
        recyclerView.addItemDecoration(decoration2);
        recyclerView.setLayoutManager(layoutManager);


        recyclerViewAdapter = new BuyListRecyclerViewAdapter(getContext(), ShoppingListSample.shoppinList);
        recyclerView.setAdapter(recyclerViewAdapter);


        return view;

    }


}
