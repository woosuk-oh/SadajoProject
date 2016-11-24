package com.tacademy.sadajo.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;

public class MyPageActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;
    ImageButton buyCountButton;
    ImageButton sellCountButton;


    int viewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        viewType = intent.getExtras().getInt("viewType");

        //vietType에 따라 다른사람프로필, 내프로필 다른 레이아웃 inflate
        if (viewType == 0) {
            setContentView(R.layout.activity_mypage);

            homeBtn = (ImageButton) findViewById(R.id.homeBtn);
            homeBtn.setOnClickListener(new BottomBarClickListener(this));
            searchBtn = (ImageButton) findViewById(R.id.searchBtn);
            searchBtn.setOnClickListener(new BottomBarClickListener(this));
            shoppingListBtn = (ImageButton) findViewById(R.id.shoppingListBtn);
            shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
            chattingBtn = (ImageButton) findViewById(R.id.chattingBtn);
            chattingBtn.setOnClickListener(new BottomBarClickListener(this));
            mypageBtn = (ImageButton) findViewById(R.id.mypageBtn);
            mypageBtn.setOnClickListener(new BottomBarClickListener(this));
            mypageBtn.setSelected(true);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setBackgroundResource(R.drawable.tool_03_mypage); //toolbar image
            getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); //back icon

        } else {
            setContentView(R.layout.activity_myage_user_profilepage);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon

        }


        sellCountButton = (ImageButton) findViewById(R.id.sellCountButton);
        buyCountButton = (ImageButton) findViewById(R.id.buyCountButton);


        ViewPager viewPager = (ViewPager) findViewById(R.id.mypageViewpager);
        if (viewPager != null) {
            setupMyPageViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mypageTab);
        tabLayout.setupWithViewPager(viewPager);


        //탭레이아웃 탭 셀렉터
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//
//            tabLayout.getTabAt(i).setIcon(R.drawable.selector_home);
//            tabLayout.getTabAt(i).setText("");
//
//
//        }

        sellCountButton.setOnClickListener(clickListener);
        buyCountButton.setOnClickListener(clickListener);

    }


    //사다줌 , 사다조 버튼 거래내역페이지 이동
    ImageButton.OnClickListener clickListener = new View.OnClickListener() {
        Intent intent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sellCountButton:
                    intent = new Intent(MyPageActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 1); //select될 tab값 전달
                    startActivity(intent);
                    break;
                case R.id.buyCountButton:
                    intent = new Intent(MyPageActivity.this, MypageBuyActivity.class);
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
