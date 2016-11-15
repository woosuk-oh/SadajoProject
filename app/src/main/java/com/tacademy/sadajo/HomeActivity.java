package com.tacademy.sadajo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.activities.ChattingActivity;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.search.SearchActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListActivity;


public class HomeActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("");//툴바 타이틀명공백
         Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);

//           TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);



        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(mClickListener);
        homeBtn.setSelected(true);
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(mClickListener);
        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(mClickListener);
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(mClickListener);
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(mClickListener);




//       Button button  = (Button)findViewById(R.id.testBtn) ;

//        tabLayout.addTab(tabLayout.newTab().setText("홈").setIcon(R.drawable.heart_black));
//        tabLayout.addTab(tabLayout.newTab().setText("검색").setIcon(R.drawable.heart_black));
//        tabLayout.addTab(tabLayout.newTab().setText("쇼핑리스트").setIcon(R.drawable.heart_black));
//        tabLayout.addTab(tabLayout.newTab().setText("채팅").setIcon(R.drawable.heart_black));
//        tabLayout.addTab(tabLayout.newTab().setText("마이페이지").setIcon(R.drawable.heart_black));
//

//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//              switch (tab.getPosition()){
//                  case 0 :
//                      tab.setIcon(R.drawable.selector_home);
//                     // Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
//                      break;
//                  case 1 :
//                      tab.setIcon(R.drawable.selector_home);
//                      Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
//                      startActivity(intent);
//                      //overridePendingTransition(0,0);
//                      break;
//                  case 2 :
//                      tab.setIcon(R.drawable.selector_home);
//                      break;
//                  case 3 :
//                      tab.setIcon(R.drawable.selector_home);
//                      break;
//                  case 4 :
//                      tab.setIcon(R.drawable.selector_home);
//                      break;
//
//
//              }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });


    }

    ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.homeBtn :

                     intent =  new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn :
                    homeBtn.setSelected(false);

                    intent =  new Intent(HomeActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn :
                    homeBtn.setSelected(false);
                     intent =  new Intent(HomeActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id. chattingBtn:
                    homeBtn.setSelected(false); //   homeBtn.setImageResource(R.drawable.heart_black);
                    intent =  new Intent(HomeActivity.this, ChattingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageBtn :
                    homeBtn.setSelected(false);
                   intent =  new Intent(HomeActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
