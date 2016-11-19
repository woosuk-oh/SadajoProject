package com.tacademy.sadajo.chatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import com.tacademy.sadajo.BottomBarClickListner;
import com.tacademy.sadajo.R;

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
        homeBtn.setOnClickListener(new BottomBarClickListner(this));
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListner(this));

        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListner(this));
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListner(this));
        chattingBtn.setSelected(true);
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListner(this));





    }
 //   ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
//        Intent intent;
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.homeBtn :
//                    chattingBtn.setSelected(false);
//                    intent =  new Intent(ChattingActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.searchBtn :
//
//
//                    intent =  new Intent(ChattingActivity.this, SearchListActivity.class);
//
//                    chattingBtn.setSelected(false);
//
//                    startActivity(intent);
//                    break;
//                case R.id.shoppingListBtn :
//                    chattingBtn.setSelected(false);
//                    intent =  new Intent(ChattingActivity.this, ShoppingListActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.chattingBtn:
//                    intent =  new Intent(ChattingActivity.this, ChattingActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.mypageBtn :
//                    chattingBtn.setSelected(false);
//                    intent =  new Intent(ChattingActivity.this, MyPageActivity.class);
//                    startActivity(intent);
//                    break;
//            }
//        }
//    };
//
}
