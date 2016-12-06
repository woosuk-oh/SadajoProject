package com.tacademy.sadajo.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;

public class OtherShoppingListActivity extends BaseActivity {
    TabLayout tabLayout;
    TextView toolbarTitle;
    Toolbar toolbar;

    int pageUserCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_shopping_list);


        Intent intent = getIntent();
        pageUserCode = intent.getIntExtra("userCode", 0); //해당 페이지 user의 userCode
        Log.e("otherActivity",String.valueOf(pageUserCode));

        toolbar = (Toolbar) findViewById(R.id.toolbar); //toolbar background image
        setSupportActionBar(toolbar);

        toolbarTitle = (TextView) findViewById(R.id.customToolbarTitle);
        toolbarTitle.setText(" 님의 쇼핑리스트");//TODO:user Name 받아오기


        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon
        setToolbar(true); //backbutton 이벤트


        ViewPager viewPager = (ViewPager) findViewById(R.id.shoppingOtherViewpager);
        if (viewPager != null) {
            setupShoppingListViewPager(viewPager);
        }
        tabLayout = (TabLayout) findViewById(R.id.shoppingOtherTab);
        tabLayout.setupWithViewPager(viewPager);
        setTabImage(); //tab이미지 지정



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
        MyPagerAdapter otherShoppingListAdapter = new MyPagerAdapter(getSupportFragmentManager());
        otherShoppingListAdapter.appendFragment(OtherLikeListFragment.newInstance(pageUserCode), "찜");
        otherShoppingListAdapter.appendFragment(OtherShoppingListFragment.newInstance(pageUserCode), "쇼핑리스트");
        viewPager.setAdapter(otherShoppingListAdapter);
    }

    public void setToolbar(boolean b) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(b); //back icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //클릭시 뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

}
