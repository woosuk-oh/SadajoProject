package com.tacademy.sadajo.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.fonts.NanumRegularTextView;
import com.tacademy.sadajo.search.searchlist.SearchListActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;
import com.tsengvn.typekit.TypekitContextWrapper;

import static android.R.attr.id;


public class HomeActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    // ImageView profileImageView;
    TextView countryNameTv;
    TextView scheduleTv;
    TextView dateComeTv;
    TextView dateGoTv;
    TextView goTv;
    TextView comeTv;
    TextView cardView2Tv;
    TextView card2CountryTv;
    Button scheduleBtn;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;

    HomeUserRecyclerViewAdapter homeUserRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setBackgroundResource(R.drawable.tool_01_main); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //back icon


        //바텀바 gone
//        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frameBottomBar);
//        frameLayout.setVisibility(View.GONE);

        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        homeBtn.setSelected(true);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));
        shoppingListBtn = (ImageButton) findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn = (ImageButton) findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn = (ImageButton) findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));


        dateGoTv = (TextView) findViewById(R.id.dateGoTv);
        dateComeTv = (TextView) findViewById(R.id.dateComeTv);
        scheduleBtn = (Button) findViewById(R.id.scheduleBtn);
        scheduleBtn.setTypeface(new NanumRegularTextView(getApplication()).getTypeface());
        goTv = (TextView) findViewById(R.id.goTv);
        comeTv = (TextView) findViewById(R.id.comeTv);

        cardView2Tv = (TextView) findViewById(R.id.cardView2Tv);
        card2CountryTv = (TextView) findViewById(R.id.card2CountryTv);
        countryNameTv = (TextView) findViewById(R.id.countryNameTv);
        scheduleTv = (TextView) findViewById(R.id.scheduleTv);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);

        scheduleBtn.setOnClickListener(onClickListener);

        button1.setVisibility(View.VISIBLE);

        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        button4.setText("어렵다아아");
        button5.setVisibility(View.VISIBLE);

        button1.setOnClickListener(onClickListener);

//
//
//        FlowLayout flowLayout =(FlowLayout)findViewById(R.id.homeFlowlayout); //두번째 카드뷰 리니어레이아웃
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT);
//
//        //차일드레이아웃 layoutparams
//        ViewGroup.LayoutParams buttonParams =  new ViewGroup.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
//        //buttonParams
//
//
//
//        for(int  i=0; i<10;i++) {
//
//            Button button = new Button(this);
//            button.setLayoutParams(buttonParams);
//           // button.setBackground();
//            button.setText("버트으은");
//            flowLayout.addView(button);
//
//        }
//

        //layout3
        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(45, "bottom");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 4));
        recyclerView.addItemDecoration(decoration);
        homeUserRecyclerViewAdapter = new HomeUserRecyclerViewAdapter(HomeActivity.this, ShoppingListSample.shoppinList);
        recyclerView.setAdapter(homeUserRecyclerViewAdapter);



    }


    View.OnClickListener onClickListener = new View.OnClickListener() {

        Intent intent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.scheduleBtn:
                    ScheduleRegisterDialog dialog = new ScheduleRegisterDialog(HomeActivity.this);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.show();
                    break;

                case R.id.button1:
                    intent = new Intent(HomeActivity.this, SearchListActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
