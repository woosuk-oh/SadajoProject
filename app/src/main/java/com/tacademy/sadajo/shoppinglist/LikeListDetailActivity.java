package com.tacademy.sadajo.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.mypage.ItemReviewRecyclerViewAdapter;

public class LikeListDetailActivity extends BaseActivity {

    TextView toolbarTitle;
    ItemReviewRecyclerViewAdapter itemReviewRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_first_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon

        //타이틀 세팅
        Intent intent= getIntent();
        String countryName = intent.getExtras().getString("countryName");
        toolbarTitle = (TextView)findViewById(R.id.customToolbarTitle);
        toolbarTitle.setText(countryName+" 찜 리스트");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shoppinListFirstDetailRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LikeListDetailActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        itemReviewRecyclerViewAdapter = new ItemReviewRecyclerViewAdapter(LikeListDetailActivity.this, ShoppingListSample.shoppinList);
        recyclerView.setAdapter(itemReviewRecyclerViewAdapter);

    }
}
