package com.tacademy.sadajo.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.chatting.ChattingActivity;
import com.tacademy.sadajo.home.HomeActivity;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.search.SearchListActivity;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        setTitle("");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.shopping_viewpager);
        if (viewPager != null) {
            setupShoppingListViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.shopping_tab);
        tabLayout.setupWithViewPager(viewPager);


        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(mClickListener);
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(mClickListener);

        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(mClickListener);
     //   shoppingListBtn.setImageResource(R.drawable.heart_red);
        shoppingListBtn.setSelected(true);
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(mClickListener);
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(mClickListener);




    }

    ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.homeBtn :
                    shoppingListBtn.setSelected(false);
                    intent =  new Intent(ShoppingListActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn :
                    intent =  new Intent(ShoppingListActivity.this, SearchListActivity.class);
                    shoppingListBtn.setSelected(false);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn :
                    intent =  new Intent(ShoppingListActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chattingBtn:
                    shoppingListBtn.setSelected(false);
                    intent =  new Intent(ShoppingListActivity.this, ChattingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageBtn :
                    shoppingListBtn.setSelected(false);
                    intent =  new Intent(ShoppingListActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };


    private void setupShoppingListViewPager(ViewPager viewPager){
        GirlGroupPagerAdapter girlsAdapter = new GirlGroupPagerAdapter(getSupportFragmentManager());
        girlsAdapter.appendFragment(ShoppingListFragment.newInstance(1), "쇼핑리스트");
        girlsAdapter.appendFragment(ShoppingListFragment.newInstance(2), "찜");
        viewPager.setAdapter(girlsAdapter);
    }

private static class GirlGroupPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<ShoppingListFragment> girsFragment = new ArrayList<>();
    private final ArrayList<String> tabTitles = new ArrayList<>();

    public GirlGroupPagerAdapter(FragmentManager fm) {
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
