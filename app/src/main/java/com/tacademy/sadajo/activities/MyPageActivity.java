package com.tacademy.sadajo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.MainActivity;
import com.tacademy.sadajo.R;

public class MyPageActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //       setSupportActionBar(toolbar);
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
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(mClickListener);
        mypageBtn.setImageResource(R.drawable.heart_red);





    }

    ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.homeBtn :
                    mypageBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(MyPageActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn :
                    mypageBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(MyPageActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn :
                    mypageBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(MyPageActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chattingBtn:
                    mypageBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(MyPageActivity.this, ChattingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageBtn :
                    intent =  new Intent(MyPageActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
