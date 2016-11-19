package com.tacademy.sadajo.mypage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import com.tacademy.sadajo.BottomBarClickListner;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.shoppinglist.ShoppingListFragment;

import java.util.ArrayList;

public class MyPageActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

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
        homeBtn.setOnClickListener(new BottomBarClickListner(this));
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListner(this));
        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListner(this));
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListner(this));
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListner(this));
        mypageBtn.setSelected(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.mypageViewpager);
        if (viewPager != null) {
            setupShoppingListViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mypageTab);
        tabLayout.setupWithViewPager(viewPager);






    }

//    ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
//        Intent intent;
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.homeBtn :
//                    mypageBtn.setSelected(false);
//                    intent =  new Intent(MyPageActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.searchBtn :
//
//                    intent =  new Intent(MyPageActivity.this, SearchListActivity.class);
//                    mypageBtn.setSelected(false);
//                    startActivity(intent);
//                    break;
//                case R.id.shoppingListBtn :
//                    mypageBtn.setSelected(false);
//                    intent =  new Intent(MyPageActivity.this, ShoppingListActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.chattingBtn:
//                    mypageBtn.setSelected(false);
//                    intent =  new Intent(MyPageActivity.this, ChattingActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.mypageBtn :
//                    intent =  new Intent(MyPageActivity.this, MyPageActivity.class);
//                    startActivity(intent);
//                    break;
//            }
//        }
//    };

    private void setupShoppingListViewPager(ViewPager viewPager){
        MyPageActivity.GroupPagerAdapter girlsAdapter = new MyPageActivity.GroupPagerAdapter(getSupportFragmentManager());
        girlsAdapter.appendFragment(ShoppingListFragment.newInstance(1), "후기");
        girlsAdapter.appendFragment(ShoppingListFragment.newInstance(2), "등록한 TIP");
        girlsAdapter.appendFragment(ShoppingListFragment.newInstance(2), "등록한아이템");
        viewPager.setAdapter(girlsAdapter);
    }

    private static class GroupPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<ShoppingListFragment> girsFragment = new ArrayList<>();
        private final ArrayList<String> tabTitles = new ArrayList<>();

        public GroupPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void appendFragment(ShoppingListFragment fragment, String title) {
            girsFragment.add(fragment);
            tabTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return girsFragment.get(position);
        }

        @Override
        public int getCount() {
            return girsFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
    }
}
