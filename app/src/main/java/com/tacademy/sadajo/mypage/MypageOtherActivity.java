package com.tacademy.sadajo.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;

import static com.tacademy.sadajo.R.id.buyCountButton;
import static com.tacademy.sadajo.R.id.sellCountButton;

public class MyPageOtherActivity extends BaseActivity {


    ImageButton otherBuyCountButton;
    ImageButton otherSellCountButton;

    TextView mypageBuyTextView;

    int viewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_mypage_other);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon


        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        otherSellCountButton = (ImageButton) findViewById(R.id.otherSellCountButton);
        otherBuyCountButton = (ImageButton) findViewById(R.id.otherBuyCountButton);


        ViewPager viewPager = (ViewPager) findViewById(R.id.otherMypageViewpager);
        if (viewPager != null) {
            setupMyPageViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.otherMypageTab);
        tabLayout.setupWithViewPager(viewPager);


        otherSellCountButton.setOnClickListener(clickListener);
        otherBuyCountButton.setOnClickListener(clickListener);

    }


    //사다줌 , 사다조 버튼 거래내역페이지 이동
    ImageButton.OnClickListener clickListener = new View.OnClickListener() {
        Intent intent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.otherSellCountButton:
                    intent = new Intent(MyPageOtherActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 1); //select될 tab값 전달
                    startActivity(intent);
                    break;
                case R.id.otherBuyCountButton:
                    intent = new Intent(MyPageOtherActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 0);
                    startActivity(intent);
                    break;

            }
        }
    };


    private void setupMyPageViewPager(ViewPager viewPager) {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.appendFragment(ReviewFragment.newInstance(1), "후기");
        pagerAdapter.appendFragment(TipFragment.newInstance(2), "등록한 TIP");
        pagerAdapter.appendFragment(ItemFragment.newInstance(3), "등록한아이템");
        viewPager.setAdapter(pagerAdapter);
    }


}
