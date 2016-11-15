package com.tacademy.sadajo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.HomeActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.search.SearchActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListActivity;

public class ChattingActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        setTitle("");//툴바 타이틀명공백
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);




        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(mClickListener);
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(mClickListener);

        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(mClickListener);
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(mClickListener);
        chattingBtn.setSelected(true);
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(mClickListener);





    }

    ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.homeBtn :
                    chattingBtn.setSelected(false);
                    intent =  new Intent(ChattingActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn :
                    chattingBtn.setSelected(false);
                    intent =  new Intent(ChattingActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn :
                    chattingBtn.setSelected(false);
                    intent =  new Intent(ChattingActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chattingBtn:
                    intent =  new Intent(ChattingActivity.this, ChattingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageBtn :
                    chattingBtn.setSelected(false);
                    intent =  new Intent(ChattingActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
