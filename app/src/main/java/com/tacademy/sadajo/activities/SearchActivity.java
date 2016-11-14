package com.tacademy.sadajo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tacademy.sadajo.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
 //       setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView)findViewById(R.id.textview);

        tabLayout.addTab(tabLayout.newTab().setText("홈").setIcon(R.drawable.heart_black));
        tabLayout.addTab(tabLayout.newTab().setText("검색").setIcon(R.drawable.heart_black));
        tabLayout.addTab(tabLayout.newTab().setText("쇼핑리스트").setIcon(R.drawable.heart_black));
        tabLayout.addTab(tabLayout.newTab().setText("채팅").setIcon(R.drawable.heart_black));
        tabLayout.addTab(tabLayout.newTab().setText("마이페이지").setIcon(R.drawable.heart_black));



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0 :
                        tab.setIcon(R.drawable.selector_home);
                        // Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        break;
                    case 1 :
                        tab.setIcon(R.drawable.selector_home);
                        Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;
                    case 2 :
                        tab.setIcon(R.drawable.selector_home);
                        break;
                    case 3 :
                        tab.setIcon(R.drawable.selector_home);
                        break;
                    case 4 :
                        tab.setIcon(R.drawable.selector_home);
                        break;


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
