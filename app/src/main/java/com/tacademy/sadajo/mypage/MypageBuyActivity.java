package com.tacademy.sadajo.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;



public class MypageBuyActivity extends BaseActivity {
    TabLayout tabLayout;
    int tabNum;
    int otheruserid;

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

        //select될 탭 설정
        Intent intent = getIntent();
        tabNum = intent.getExtras().getInt("tabNum");
        otheruserid = intent.getExtras().getInt("targetUserCode");
        Log.d("otheruserid","MypageBuyActivity 타겟아이디:"+otheruserid);


        // 커스텀 뷰페이저 선언.
        TradeListViewPager viewPager = (TradeListViewPager) findViewById(R.id.mypageDealViewpager);
        if (viewPager != null) {
            setupBuySellViewPager(viewPager, otheruserid);
        }
        tabLayout = (TabLayout) findViewById(R.id.mypageDealTab);
        tabLayout.setupWithViewPager(viewPager);

        setTabImage();

        tabLayout.getTabAt(tabNum).select();
/*

        */
/* 인텐트로 받아온 otheruserid 값을 번들을 통해 프래그먼트로 이동*//*

        Bundle bundle = new Bundle();
        bundle.putInt("targetUserCode", otheruserid);
        BuyListFragment buyListFragment = new BuyListFragment();
        DealSellListFragment dealSellListFragment = new DealSellListFragment();
        buyListFragment.setArguments(bundle);
        dealSellListFragment.setArguments(bundle);

*/



    }

    private void setTabImage() {

        ImageView imageView = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        imageView.setImageResource(R.drawable.selector_tab_mypage_4);
        imageView2.setImageResource(R.drawable.selector_tab_mypage_5);
        tabLayout.getTabAt(0).setCustomView(imageView);
        tabLayout.getTabAt(1).setCustomView(imageView2);

    }
    private void setupBuySellViewPager(TradeListViewPager viewPager, int otheruserid) {
        MyPagerAdapter buySellPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        buySellPagerAdapter.appendFragment(BuyListFragment.newInstance(otheruserid), "사다조");
        buySellPagerAdapter.appendFragment(DealSellListFragment.newInstance(otheruserid), "사다줌");
        viewPager.setAdapter(buySellPagerAdapter);
        viewPager.setPagingEnabled(false); // 커스텀뷰페이저의 setpagingEnabled로 page swipe disable.
    }






}
