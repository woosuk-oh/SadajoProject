package com.tacademy.sadajo.mypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.sadajo.R;


public class BuyListFragment extends Fragment {


    public BuyListFragment() {
    }


    public static BuyListFragment newInstance(String param1, String param2) {
        BuyListFragment fragment = new BuyListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_list, container, false);
    }


}
