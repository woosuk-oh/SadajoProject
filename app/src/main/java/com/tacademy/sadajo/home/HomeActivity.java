package com.tacademy.sadajo.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.fonts.NanumRegularTextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class HomeActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;
    TextView countryNameTv;
    TextView scheduleTv;
    TextView dateComeTv;
    TextView dateGoTv;
    TextView goTv;
    TextView comeTv;
    TextView cardView2Tv;
    TextView card2CountryTv;
    Button scheduleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("");//툴바 타이틀명공백
         Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);

//           TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);



        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        homeBtn.setSelected(true);
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));
        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));


        dateGoTv = (TextView)findViewById(R.id.dateGoTv);
        dateComeTv = (TextView)findViewById(R.id.dateComeTv);
        scheduleBtn = (Button)findViewById(R.id.scheduleBtn);
        scheduleBtn.setTypeface(new NanumRegularTextView(getApplication()).getTypeface());
        goTv =(TextView)findViewById(R.id.goTv);
        comeTv =(TextView)findViewById(R.id.comeTv);

        cardView2Tv = (TextView)findViewById(R.id.cardView2Tv);
        card2CountryTv = (TextView)findViewById(R.id.card2CountryTv);


        countryNameTv = (TextView)findViewById(R.id.countryNameTv);
        scheduleTv = (TextView)findViewById(R.id.scheduleTv);


        LinearLayout linearLayout =(LinearLayout)findViewById(R.id.cardView2LL); //두번째 카드뷰 리니어레이아웃
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT);

        //차일드레이아웃 layoutparams
        ViewGroup.LayoutParams buttonParams =  new ViewGroup.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        //buttonParams

        LinearLayout linearLayout2 =new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(linearLayout2);



        Button button = new Button(this);
        button.setLayoutParams(buttonParams);

        button.setText("버트으은");
        linearLayout2.addView(button);


        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleRegisterDialog dialog =new ScheduleRegisterDialog(HomeActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

            }
        });


    }


}
