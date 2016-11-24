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

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {


    ItemReviewRecyclerViewAdapter itemReviewRecyclerViewAdapter;

    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance(int initValue) {
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.mypage_fragment_item, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.itemRecyclerView);
        recyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom"); //아이템간 간격
        recyclerView.addItemDecoration(decoration);

        itemReviewRecyclerViewAdapter = new ItemReviewRecyclerViewAdapter(getContext(), ShoppingListSample.shoppinList);
        recyclerView.setAdapter(itemReviewRecyclerViewAdapter);

        Bundle initBundle = getArguments();

        if(itemReviewRecyclerViewAdapter.getItemCount()==0){
            view = inflater.inflate(R.layout.mypage_item_recyclerview_noitem, container, false);

        }
        return view;
    }


}
