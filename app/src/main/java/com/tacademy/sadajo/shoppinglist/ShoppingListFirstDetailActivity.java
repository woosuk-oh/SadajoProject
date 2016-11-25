package com.tacademy.sadajo.shoppinglist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.mypage.ItemReviewRecyclerViewAdapter;

public class ShoppingListFirstDetailActivity extends AppCompatActivity {


    ItemReviewRecyclerViewAdapter itemReviewRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_first_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setBackgroundResource(R.drawable.tool_02_shoppinglist); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30, "bottom");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shoppinListFirstDetailRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShoppingListFirstDetailActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        itemReviewRecyclerViewAdapter = new ItemReviewRecyclerViewAdapter(ShoppingListFirstDetailActivity.this, ShoppingListSample.shoppinList);
        recyclerView.setAdapter(itemReviewRecyclerViewAdapter);

    }
}
