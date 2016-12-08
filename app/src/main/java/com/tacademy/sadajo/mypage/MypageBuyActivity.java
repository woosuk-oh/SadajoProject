package com.tacademy.sadajo.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;

public class MypageBuyActivity extends BaseActivity {
    TabLayout tabLayout;
    int tabNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_buy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundResource(R.drawable.tool_06_deal); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ViewPager viewPager = (ViewPager) findViewById(R.id.mypageDealViewpager);
        if (viewPager != null) {
            setupBuySellViewPager(viewPager);
        }
        tabLayout = (TabLayout) findViewById(R.id.mypageDealTab);
        tabLayout.setupWithViewPager(viewPager);
        setTabImage();


        //select될 탭 설정
        Intent intent = getIntent();
        tabNum = intent.getExtras().getInt("tabNum");
        tabLayout.getTabAt(tabNum).select();


    }

    private void setTabImage() {

        ImageView imageView = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        imageView.setImageResource(R.drawable.selector_tab_mypage_4);
        imageView2.setImageResource(R.drawable.selector_tab_mypage_5);
        tabLayout.getTabAt(0).setCustomView(imageView);
        tabLayout.getTabAt(1).setCustomView(imageView2);

    }
    private void setupBuySellViewPager(ViewPager viewPager) {
        MyPagerAdapter buySellPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        buySellPagerAdapter.appendFragment(BuyListFragment.newInstance(1), "사다조");
        buySellPagerAdapter.appendFragment(DealSellListFragment.newInstance(2), "사다줌");
        viewPager.setAdapter(buySellPagerAdapter);
    }




}
