package com.tacademy.sadajo.shoppinglist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;

public class OtherShoppingListActivity extends BaseActivity {
    TabLayout tabLayout;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_shopping_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //toolbar background image
        setSupportActionBar(toolbar);

        toolbarTitle = (TextView)findViewById(R.id.customToolbarTitle);
        toolbarTitle.setText(" 님의 쇼핑리스트");//TODO:user Name 받아오기


        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        ViewPager viewPager = (ViewPager) findViewById(R.id.shoppingOtherViewpager);
        if (viewPager != null) {
            setupShoppingListViewPager(viewPager);
        }
        tabLayout = (TabLayout) findViewById(R.id.shoppingOtherTab);
        tabLayout.setupWithViewPager(viewPager);


        setTabImage();

    }
    private void setTabImage() {

        ImageView imageView = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        imageView.setImageResource(R.drawable.selector_tab_sl_1);
        imageView2.setImageResource(R.drawable.selector_tab_sl_2);
        tabLayout.getTabAt(0).setCustomView(imageView);
        tabLayout.getTabAt(1).setCustomView(imageView2);


    }
    private void setupShoppingListViewPager(ViewPager viewPager) {
        MyPagerAdapter shoppingListAdapter = new MyPagerAdapter(getSupportFragmentManager());
        shoppingListAdapter.appendFragment(OtherLikeListFragment.newInstance(1), "찜");
        shoppingListAdapter.appendFragment(OtherShoppingListFragment.newInstance(2), "쇼핑리스트");
        viewPager.setAdapter(shoppingListAdapter);
    }

}
