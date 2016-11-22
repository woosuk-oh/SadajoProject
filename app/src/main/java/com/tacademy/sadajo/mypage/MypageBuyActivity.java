package com.tacademy.sadajo.mypage;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_buy);

        setTitle("");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.mypage_viewpager);
        if (viewPager != null) {
            setupBuySellViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mypage_tab);
        tabLayout.setupWithViewPager(viewPager);
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
