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
public class ReviewFragment extends Fragment {


    ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(int initValue) {
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        reviewFragment.setArguments(bundle);
        return reviewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mypage_fragment_review, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom"); //아이템간 간격
        recyclerView.addItemDecoration(decoration);

        reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(getContext(), ShoppingListSample.shoppinList);
        recyclerView.setAdapter(reviewRecyclerViewAdapter);


        if(reviewRecyclerViewAdapter.getItemCount()==0){
            view =   inflater.inflate(R.layout.mypage_review_recyclerview_noitem, container, false);
        }
        Bundle initBundle = getArguments();
        return view;
    }


}
