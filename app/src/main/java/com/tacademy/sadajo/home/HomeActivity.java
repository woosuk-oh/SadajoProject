package com.tacademy.sadajo.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.fonts.NanumRegularTextView;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import org.apmem.tools.layouts.FlowLayout;

import static android.R.attr.id;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class HomeActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    // ImageView profileImageView;
    TextView countryNameTextView;
    TextView comeDateTextView;
    TextView departDateTextView;
    Button scheduleRegisterButton;


    ImageView cardView2CountryFlagImageView;
    ImageView cardView3CountryFlagImageView;

    TextView cardView2CountryTextView;
    TextView cardView3CountryTextView;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;

    NinePatchDrawable ninepatch;
    HomeUserRecyclerViewAdapter homeUserRecyclerViewAdapter;
    HomeTagRecyclerViewAdapter homeTagRecyclerViewAdapter;

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

        countryNameTextView = (TextView) findViewById(R.id.countryNameTextView);//국가명

        departDateTextView = (TextView) findViewById(R.id.departDateTextView); //떠나요날짜
        comeDateTextView = (TextView) findViewById(R.id.comeDateTextView); //돌아와요날짜
        scheduleRegisterButton = (Button) findViewById(R.id.scheduleRegisterButton); //일정등록버튼
        scheduleRegisterButton.setTypeface(new NanumRegularTextView(getApplication()).getTypeface());

        cardView2CountryTextView = (TextView) findViewById(R.id.cardView2CountryTextView);//두번째카드뷰 국가명
        cardView3CountryTextView = (TextView) findViewById(R.id.cardView3CountryTextView);//세번째카드뷰 국가명
        cardView2CountryFlagImageView = (ImageView) findViewById(R.id.cardView2CountryFlagImageView);//두번째 카드뷰 flag이미지
        cardView3CountryFlagImageView = (ImageView) findViewById(R.id.cardView3countryFlagImageView);//세번째 카드뷰 flag이미지


        //두번째 카드뷰 버튼들
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);

        scheduleRegisterButton.setOnClickListener(onClickListener);

        button1.setVisibility(View.VISIBLE);

        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        button4.setText("어렵다아아");
        button5.setVisibility(View.VISIBLE);

        button1.setOnClickListener(onClickListener);


        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.tag_button);
        if (image.getNinePatchChunk() != null) {
            byte[] chunk = image.getNinePatchChunk();
            Rect paddingRectangle = new Rect(5, 5, 5, 5);

            ninepatch = new NinePatchDrawable(getResources(), image, chunk, paddingRectangle, null);
        }


        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.homeFlowlayout); //두번째 카드뷰 리니어레이아웃
        RecyclerView homeTagRecyclerView = (RecyclerView) findViewById(R.id.homeTagRecyclerView);
        homeTagRecyclerView.setLayoutManager(flowLayoutManager);
        homeTagRecyclerViewAdapter = new HomeTagRecyclerViewAdapter(HomeActivity.this, ShoppingListSample.shoppinList);
        homeTagRecyclerView.setAdapter(homeTagRecyclerViewAdapter);


//        FlowLayout mFlowLayout =(FlowLayout) findViewById(R.id.id_flowlayout);
//         mFlowLayout.setAdapter(new TagAdapter<String>(mVals)
//        {
//            @Override
//            public View getView(FlowLayout parent, int position, String s)
//            {
//                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
//                        mFlowLayout, false);
//                tv.setText(s);
//                return tv;
//            }
//        });
//
        flowLayout.addView(createDummyTextView("산타마리아노벨라", ninepatch));
        flowLayout.addView(createDummyTextView("이탈리아", ninepatch));
        flowLayout.addView(createDummyTextView("더몰", ninepatch));
        flowLayout.addView(createDummyTextView("프라다", ninepatch));
        flowLayout.addView(createDummyTextView("초콜렛", ninepatch));
        flowLayout.addView(createDummyTextView("로마", ninepatch));
        flowLayout.addView(createDummyTextView("산타마리아노벨라", ninepatch));
        flowLayout.addView(createDummyTextView("산타마리아노벨라", ninepatch));


        //차일드레이아웃 layoutparams
        //  ViewGroup.LayoutParams buttonParams =  new ViewGroup.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        //buttonParams


//        for(int  i=0; i<10;i++) {
//
//            Button button = new Button(this);
//            button.setLayoutParams(buttonParams);
//           // button.setBackground();
//            button.setText("버트으은");
//            flowLayout.addView(button);
//
//        }


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
                case R.id.scheduleRegisterButton:
                    ScheduleRegisterDialog dialog = new ScheduleRegisterDialog(HomeActivity.this);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.show();
                    break;

//                case R.id.button1:
//                    intent = new Intent(HomeActivity.this, SearchListActivity.class);
//                    startActivity(intent);
//                    break;

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


    private View createDummyTextView(String text, NinePatchDrawable ninePatchDrawable) {

        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(13);
        button.setBackground(ninePatchDrawable);
        button.setTypeface(new NanumRegularTextView(getApplication()).getTypeface());
        int heigth = 69;
        ViewGroup.LayoutParams buttonParams = new ViewGroup.LayoutParams(WRAP_CONTENT, 100);
        button.setLayoutParams(buttonParams);

        return button;
    }


}
