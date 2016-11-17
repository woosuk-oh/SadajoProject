package com.tacademy.sadajo.search.searchlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.chatting.ChattingActivity;
import com.tacademy.sadajo.home.HomeActivity;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListActivity;

import java.util.ArrayList;

import static com.tacademy.sadajo.SadajoContext.getContext;

public class SearchListActivity extends AppCompatActivity {


   // private CollapsingSwipeRefreshLayout swiper;
    private RecyclerView mRecycler;

    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager layoutManager;
    Context mContext;

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    private SearchListRecyclerAdapter mAdapter;

    //param 1번째는  이미지의 resource 값
    private ArrayList<Integer> itemsOfData;
    private Animation inAnim;
    private Animation outAnim;
    private TextView customTitle;
    private LinearLayout customBar;



    // 커스텀 바 setting
    private int columnCount;
    //커스텀 바의 현재 단말기 화면의 높이에 적절히 세팅
    private int customBarHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mContext = getContext();


        // 리싸이클러뷰 xml 붙이기.
        mRecycler = (RecyclerView) findViewById(R.id.search_recycler_view);


        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(layoutManager); //리싸이클러뷰의 레이아웃매니저 적용

        ArrayList<ItemArrayList> items = new ArrayList<>();

        items.add(new ItemArrayList(R.drawable.sample1, "산타노벨라 향수"));
        items.add(new ItemArrayList(R.drawable.sample2, "샘플2"));
        items.add(new ItemArrayList(R.drawable.sample3, "샘플3"));
        items.add(new ItemArrayList(R.drawable.sample4, "샘플4"));
        items.add(new ItemArrayList(R.drawable.sample5, "샘플5"));

        mAdapter = new SearchListRecyclerAdapter(items, mContext);
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();







        //        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //       setSupportActionBar(toolbar);
//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);

        //////////////////////
        //  리프레쉬 할때 출력되는 새로고침 애니메이션.
        // //확장 가능한 TitleBar(~~액션바를 붙이지 않아도)   //
        //////////////////////
        inAnim = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_top);
        outAnim = AnimationUtils.loadAnimation(this, R.anim.abc_slide_out_top);

        customBar = (LinearLayout) findViewById(R.id.collapsing_toolbar);


        // ActionBar의 추천사이즈로 커스텀을 설정함.(리사이클뷰의 아이템 데코레이션 값)
        TypedValue typeValue = new TypedValue();
        if (getTheme().resolveAttribute(R.attr.actionBarSize, typeValue, true)) {
            //현재 단말기 해상도에 적절한 크기를 자동으로 세팅
            customBarHeight = TypedValue.complexToDimensionPixelSize(typeValue.data,
                    getResources().getDisplayMetrics());
        }

        customTitle = (TextView) findViewById(R.id.custom_title);




/*






        //////////////////////////////
        //  Setup Swipe To Refresh  //
        //////////////////////////////
        swiper = (CollapsingSwipeRefreshLayout) findViewById(R.id.swipe_container);
        */
/*
          Swipe Icon 크기 및 컬러설정
         *//*

        swiper.setSize(SwipeRefreshLayout.LARGE);
        swiper.setColorSchemeResources(

                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light
        );
        //Swipe 발생시
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                */
/*
                  발생시(2000초)
                 *//*

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (itemsOfData != null && mAdapter != null) {

                            // 다시 네트워크로 부터 데이터 가져와야됌.


                            mAdapter.notifyDataSetChanged(); // 새로고침 후 데이터 셋 체인지 해준다.

                        }
                        swiper.setRefreshing(false); // 데이터셋 해주고 리프래쉬
                    }
                }, 2000);
            }
        });


        /////////////////////////////////////////////
        //  스크롤 리스터 등록 //
        /////////////////////////////////////////////
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            //스크롤의 수평/수직의 갯수--여기선 Vertical이므로 dy의 값은 수직의 값이 됨(+-값)
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 3) {
                    if (customBar.getVisibility() == View.VISIBLE)
                        hideCustomBar();

                } else if (dy < -3) {
                    if (customBar.getVisibility() == View.GONE)
                        showCustomBar();
                }
            }
        });

        mRecycler.setAdapter(mAdapter);

        //스크롤될 뷰(여기서는 리사이클러뷰)를 스위프에 등록
        swiper.setTargetScrollableView(mRecycler);



*/



        // 바텀 탭바

        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(mClickListener);
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(mClickListener);
        searchBtn.setSelected(true);
        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(mClickListener);
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(mClickListener);
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(mClickListener);




    } // end OnCreate.

        private void hideCustomBar() {
            customBar.startAnimation(outAnim);
            customBar.setVisibility(View.GONE);
        }
        private void showCustomBar() {
            customBar.startAnimation(inAnim);
            customBar.setVisibility(View.VISIBLE);
        }

// 하단 탭바 클릭 시

    ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.homeBtn :
                    searchBtn.setSelected(false);
                    intent =  new Intent(SearchListActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn :
                    intent =  new Intent(SearchListActivity.this, SearchListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn :
                    searchBtn.setSelected(false);

                    intent =  new Intent(SearchListActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chattingBtn:
                    searchBtn.setSelected(false);

                    intent =  new Intent(SearchListActivity.this, ChattingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageBtn :
                    searchBtn.setSelected(false);

                    intent =  new Intent(SearchListActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };



}
