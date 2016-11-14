package com.tacademy.sadajo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.MainActivity;
import com.tacademy.sadajo.R;

public class ChattingActivity extends Activity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //       setSupportActionBar(toolbar);
//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);




        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(mClickListener);
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(mClickListener);
        searchBtn.setImageResource(R.drawable.heart_red);
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
                    searchBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(ChattingActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn :
                    intent =  new Intent(ChattingActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn :
                    intent =  new Intent(ChattingActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chattingBtn:
                    intent =  new Intent(ChattingActivity.this, ChattingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageBtn :
                    intent =  new Intent(ChattingActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
