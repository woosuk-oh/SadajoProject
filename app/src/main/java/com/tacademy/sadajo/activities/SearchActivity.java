package com.tacademy.sadajo.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tacademy.sadajo.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);

        tabLayout.addTab(tabLayout.newTab().setText("1").setIcon(R.drawable.heart_black));
        tabLayout.addTab(tabLayout.newTab().setText("1").setIcon(R.drawable.heart_black));
        tabLayout.addTab(tabLayout.newTab().setText("1").setIcon(R.drawable.heart_black));


        

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 1 :
                        tab.setIcon(R.drawable.selector_home);
                       // Intent intent = new Intent(MainActivity.this, SearchActivity.class);


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
