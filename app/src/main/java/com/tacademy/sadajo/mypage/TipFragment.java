package com.tacademy.sadajo.mypage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;


public class TipFragment extends Fragment {


    TipRecyclerViewAdapter tipRecyclerViewAdapter;
    int userAccount;
    int targetUserCode;

    public TipFragment() {
    }

    public static TipFragment newInstance(int initValue) {
        TipFragment fragment = new TipFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.mypage_fragment_tip, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tipRecyclerView);
        recyclerView.setLayoutManager(layoutManager);


        tipRecyclerViewAdapter = new TipRecyclerViewAdapter(getContext(), ShoppingListSample.shoppinList);
        recyclerView.setAdapter(tipRecyclerViewAdapter);


        Bundle initBundle = getArguments();

        if (tipRecyclerViewAdapter.getItemCount() == 0) {
            view = inflater.inflate(R.layout.mypage_tip_recyclerview_noitem, container, false);

        }
        return view;

    }


}
