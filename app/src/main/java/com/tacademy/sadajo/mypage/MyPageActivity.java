package com.tacademy.sadajo.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.home.HomeActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.chatting.ChattingActivity;
import com.tacademy.sadajo.search.searchlist.SearchListActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListActivity;

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
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(mClickListener);
        mypageBtn.setSelected(true);





    }

    ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.homeBtn :
                    mypageBtn.setSelected(false);
                    intent =  new Intent(MyPageActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn :

                    intent =  new Intent(MyPageActivity.this, SearchListActivity.class);
                    mypageBtn.setSelected(false);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn :
                    mypageBtn.setSelected(false);
                    intent =  new Intent(MyPageActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chattingBtn:
                    mypageBtn.setSelected(false);
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
