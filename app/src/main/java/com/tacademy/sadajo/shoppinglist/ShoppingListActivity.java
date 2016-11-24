package com.tacademy.sadajo.shoppinglist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;

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
        //     this.overridePendingTransition(0,0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //toolbar background image
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.drawable.tool_02_shoppinglist);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden

        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //back icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ViewPager viewPager = (ViewPager) findViewById(R.id.shopping_viewpager);
        if (viewPager != null) {
            setupShoppingListViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.shopping_tab);
        tabLayout.setupWithViewPager(viewPager);


        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));

        shoppingListBtn = (ImageButton) findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        shoppingListBtn.setSelected(true);
        chattingBtn = (ImageButton) findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn = (ImageButton) findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));


        // tabLayout.getTabAt(0).setText("");


        //탭레이아웃 탭 셀렉터
        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            tabLayout.getTabAt(i).setIcon(R.drawable.selector_tab_sl_1);
            tabLayout.getTabAt(i).setText("");



        }


    }


    private void setupShoppingListViewPager(ViewPager viewPager) {
        MyPagerAdapter shoppingListAdapter = new MyPagerAdapter(getSupportFragmentManager());
        shoppingListAdapter.appendFragment(ShoppingListFragment2.newInstance(1), "찜");
        shoppingListAdapter.appendFragment(ShoppingListFragment.newInstance(2), "쇼핑리스트");
        viewPager.setAdapter(shoppingListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;//false하면 메뉴아이콘 hidden
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


}
