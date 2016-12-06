package com.tacademy.sadajo.shoppinglist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;

public class ShoppingListActivity extends BaseActivity {


    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    TabLayout tabLayout;

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        setBottomButtonClickListener();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //toolbar background image
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.drawable.tool_02_shoppinglist);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden

        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //back icon



        ViewPager viewPager = (ViewPager) findViewById(R.id.shoppingViewpager);
        if (viewPager != null) {
            setupShoppingListViewPager(viewPager);
        }
        tabLayout = (TabLayout) findViewById(R.id.shoppingTab);
        tabLayout.setupWithViewPager(viewPager);


        setTabImage();

    }
    private void setBottomButtonClickListener(){
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
        shoppingListBtn.setSelected(true);


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
        shoppingListAdapter.appendFragment(LikeListFragment.newInstance(0), "찜");
        shoppingListAdapter.appendFragment(ShoppingListFragment.newInstance(1), "쇼핑리스트");
        viewPager.setAdapter(shoppingListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return viewType();//false하면 메뉴아이콘 hidden
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        long intervalTime = currentTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = currentTime;
            Toast.makeText(getApplicationContext(),
                    "'뒤로' 버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean viewType() {
        return false;
    }

}
