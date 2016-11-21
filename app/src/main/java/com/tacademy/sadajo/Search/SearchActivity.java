package com.tacademy.sadajo.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.HomeActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.activities.ChattingActivity;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListActivity;

public class SearchActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle(""); //툴바 타이틀명공백

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);




        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(mClickListener);
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(mClickListener);
        searchBtn.setSelected(true);
        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(mClickListener);
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(mClickListener);
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(mClickListener);





    }

    ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.homeBtn :
                   searchBtn.setSelected(false);
                    intent =  new Intent(SearchActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn :
                    intent =  new Intent(SearchActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn :
                    searchBtn.setSelected(false);
                    intent =  new Intent(SearchActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chattingBtn:
                    searchBtn.setSelected(false);
                    intent =  new Intent(SearchActivity.this, ChattingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageBtn :
                    searchBtn.setSelected(false);
                    intent =  new Intent(SearchActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
