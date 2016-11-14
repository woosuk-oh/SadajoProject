package com.tacademy.sadajo.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.HomeActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.activities.ChattingActivity;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.search.SearchActivity;

public class ShoppingListActivity extends AppCompatActivity {

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

        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(mClickListener);
        shoppingListBtn.setImageResource(R.drawable.heart_red);
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
                    shoppingListBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(ShoppingListActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn :
                    shoppingListBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(ShoppingListActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn :
                    intent =  new Intent(ShoppingListActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chattingBtn:
                    shoppingListBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(ShoppingListActivity.this, ChattingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageBtn :
                    shoppingListBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(ShoppingListActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
