package com.tacademy.sadajo.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.tacademy.sadajo.R;

public class ShoppingListDetailActivity extends AppCompatActivity {

    TextView  shopListTitleTextView;
    ShopListDetailRecyclerViewAdapter shopListDetailRecyclerViewAdapter;
    int listCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon

        //타이틀 세팅
        Intent intent = getIntent();
        String countryName = intent.getExtras().getString("countryName");
        listCode = intent.getIntExtra("listCode", 0); //리스트코드 받아옴
        shopListTitleTextView = (TextView) findViewById(R.id.customToolbarTitle);
        shopListTitleTextView.setText(countryName + " 쇼핑 리스트");
    }
}
