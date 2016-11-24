package com.tacademy.sadajo.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;

public class MypageBuyActivity extends AppCompatActivity {

    int tabNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_buy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundResource(R.drawable.tool_06_deal); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ViewPager viewPager = (ViewPager) findViewById(R.id.mypage_viewpager);
        if (viewPager != null) {
            setupBuySellViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mypage_tab);
        tabLayout.setupWithViewPager(viewPager);

        //select될 탭 설정
        Intent intent = getIntent();
        tabNum = intent.getExtras().getInt("tabNum");
        tabLayout.getTabAt(tabNum).select();
    }


    private void setupBuySellViewPager(ViewPager viewPager) {
        MyPagerAdapter buySellPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        buySellPagerAdapter.appendFragment(BuyListFragment.newInstance(1), "사다조");
        buySellPagerAdapter.appendFragment(BuyListFragment.newInstance(2), "사다줌");
        viewPager.setAdapter(buySellPagerAdapter);
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
