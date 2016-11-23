package com.tacademy.sadajo.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tacademy.sadajo.R;

import java.util.ArrayList;

public class MypageBuyActivity extends AppCompatActivity {

    int tabNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_buy);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundResource(R.drawable.tool_06_deal); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //back icon



        ViewPager viewPager = (ViewPager) findViewById(R.id.mypage_viewpager);
        if (viewPager != null) {
            setupBuySellViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mypage_tab);
        tabLayout.setupWithViewPager(viewPager);

        //select될 탭 설정
        Intent intent= getIntent();
        tabNum = intent.getExtras().getInt("tabNum");
        tabLayout.getTabAt(tabNum).select();
    }


    private void setupBuySellViewPager(ViewPager viewPager){
        BuySellPagerAdapter buySellPagerAdapter = new BuySellPagerAdapter(getSupportFragmentManager());
        buySellPagerAdapter.appendFragment(BuyListFragment.newInstance(1), "사다조");
        buySellPagerAdapter.appendFragment(BuyListFragment.newInstance(2), "사다줌");
        viewPager.setAdapter(buySellPagerAdapter);
    }

    private static class BuySellPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<android.support.v4.app.Fragment> Fragment = new ArrayList<>();
        private final ArrayList<String> tabTitles = new ArrayList<>();

        public BuySellPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void appendFragment(Fragment fragment, String title) {
            Fragment.add(fragment);
            tabTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment.get(position);
        }

        @Override
        public int getCount() {
            return Fragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
    }
}
