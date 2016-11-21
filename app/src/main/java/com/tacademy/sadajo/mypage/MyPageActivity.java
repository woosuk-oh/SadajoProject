package com.tacademy.sadajo.mypage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.fonts.GothamTextView;
import com.tacademy.sadajo.shoppinglist.ShoppingListFragment;

import java.util.ArrayList;

public class MyPageActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;
    Button buyCountButton;
    Button sellCountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        setTitle("");//툴바 타이틀명공백
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);




        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));
        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn.setSelected(true);

        sellCountButton = (Button)findViewById(R.id.sellCountButton);
        buyCountButton = (Button)findViewById(R.id.buyCountButton);

        sellCountButton.setTypeface(new GothamTextView(getApplication()).getTypeface());
        buyCountButton.setTypeface(new GothamTextView(getApplication()).getTypeface());


        ViewPager viewPager = (ViewPager) findViewById(R.id.mypageViewpager);
        if (viewPager != null) {
            setupMyPageViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mypageTab);
        tabLayout.setupWithViewPager(viewPager);






    }



    private void setupMyPageViewPager(ViewPager viewPager){
        MyPageActivity.MyPagePagerAdapter myPagePagerAdapter = new MyPageActivity.MyPagePagerAdapter(getSupportFragmentManager());
        myPagePagerAdapter.appendFragment(ReviewFragment.newInstance(1), "후기");
        myPagePagerAdapter.appendFragment(ShoppingListFragment.newInstance(2), "등록한 TIP");
        myPagePagerAdapter.appendFragment(ShoppingListFragment.newInstance(2), "등록한아이템");
        viewPager.setAdapter(myPagePagerAdapter);
    }

    private static class MyPagePagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> Fragment = new ArrayList<>();
        private final ArrayList<String> tabTitles = new ArrayList<>();

        public MyPagePagerAdapter(FragmentManager fm) {
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
