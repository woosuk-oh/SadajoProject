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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.MyPagerAdapter;
import com.tacademy.sadajo.R;

public class MyPageActivity extends AppCompatActivity {

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    ImageButton buyCountButton;
    ImageButton sellCountButton;

    TextView mypageBuyTextView;

    int viewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_mypage);

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
            mypageBtn.setSelected(true);


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setBackgroundResource(R.drawable.tool_03_mypage); //toolbar image
            getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); //back icon





        sellCountButton = (ImageButton) findViewById(R.id.sellCountButton);
        buyCountButton = (ImageButton) findViewById(R.id.buyCountButton);


        ViewPager viewPager = (ViewPager) findViewById(R.id.mypageViewpager);
        if (viewPager != null) {
            setupMyPageViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mypageTab);
        tabLayout.setupWithViewPager(viewPager);


        //탭레이아웃 탭 셀렉터
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//
//            tabLayout.getTabAt(i).setIcon(R.drawable.selector_home);
//            tabLayout.getTabAt(i).setText("");
//
//
//        }

        sellCountButton.setOnClickListener(clickListener);
        buyCountButton.setOnClickListener(clickListener);

    }


    //사다줌 , 사다조 버튼 거래내역페이지 이동
    ImageButton.OnClickListener clickListener = new View.OnClickListener() {
        Intent intent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sellCountButton:
                    intent = new Intent(MyPageActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 1); //select될 tab값 전달
                    startActivity(intent);
                    break;
                case R.id.buyCountButton:
                    intent = new Intent(MyPageActivity.this, MypageBuyActivity.class);
                    intent.putExtra("tabNum", 0);
                    startActivity(intent);
                    break;

            }
        }
    };


    private void setupMyPageViewPager(ViewPager viewPager) {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.appendFragment(ReviewFragment.newInstance(1), "후기");
        pagerAdapter.appendFragment(TipFragment.newInstance(2), "등록한 TIP");
        pagerAdapter.appendFragment(ItemFragment.newInstance(3), "등록한아이템");
        viewPager.setAdapter(pagerAdapter);
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
