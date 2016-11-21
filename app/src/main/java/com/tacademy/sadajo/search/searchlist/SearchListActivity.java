package com.tacademy.sadajo.search.searchlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.chatting.ChattingActivity;
import com.tacademy.sadajo.funtion.SearchBarDeleteButton;
import com.tacademy.sadajo.home.HomeActivity;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListActivity;

import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {


    // private CollapsingSwipeRefreshLayout swiper;
    private RecyclerView mRecycler;

    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager layoutManager;

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;


    private SearchListRecyclerAdapter mAdapter;

    //param 1번째는  이미지의 resource 값

    private Animation inAnim;
    private Animation outAnim;
    private TextView customTitle;
    private EditText searchBar;
    private Button searchBarClear;

    private LinearLayout customBar;


    // 커스텀 바 setting
    private int columnCount;
    //커스텀 바의 현재 단말기 화면의 높이에 적절히 세팅
    private int customBarHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 리싸이클러뷰 xml 붙이기.
        mRecycler = (RecyclerView) findViewById(R.id.search_recycler_view);


        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager); //리싸이클러뷰의 레이아웃매니저 적용


        ArrayList<ItemArrayList> items = new ArrayList<>();

        items.add(new ItemArrayList(R.drawable.sample1, "산타노벨라 향수"));
        items.add(new ItemArrayList(R.drawable.sample2, "샘플2"));
        items.add(new ItemArrayList(R.drawable.sample3, "샘플3"));
        items.add(new ItemArrayList(R.drawable.sample4, "샘플4"));
        items.add(new ItemArrayList(R.drawable.sample5, "샘플5"));

        mAdapter = new SearchListRecyclerAdapter(items, this);
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        searchBar = (EditText) findViewById(R.id.search_search_et);
        searchBarClear = (Button) findViewById(R.id.search_search_bt);

        SearchBarDeleteButton.setRemovableET(searchBar, searchBarClear);  // Search Bar 에딧텍스트에 글자 입력시 x버튼 노출, 누르면 공백을 setText해줌.

  /*      searchBar.setOnFocusChangeListener(FocusListener); // Search Bar 클릭 시, 이미지 변경*/

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


        // 바텀 탭바

        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(mClickListener);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(mClickListener);
        searchBtn.setSelected(true);
        shoppingListBtn = (ImageButton) findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(mClickListener);
        chattingBtn = (ImageButton) findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(mClickListener);
        mypageBtn = (ImageButton) findViewById(R.id.mypageBtn);
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


  /*  View.OnFocusChangeListener FocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            if (view == searchBar) { // 뷰가 서치바를 가지면
                if (hasFocus) { //서치바에 포커스가 있을 시
               *//* mTextMessage.setText("Touch-1Set Focus");*//*
                    searchBar.setBackgroundResource(R.drawable.search_bar_onfocus);
                } else {
             *//*   mTextMessage.setText("Touch-1Kill Focus");*//*
                    searchBar.setBackgroundResource(R.drawable.search_bar);
                }
            }
        }
    };*/


// 하단 탭바 클릭 시

    ImageButton.OnClickListener mClickListener = new View.OnClickListener() {
        Intent intent;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.homeBtn:
                    searchBtn.setSelected(false);
                    intent = new Intent(SearchListActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.searchBtn:
                    intent = new Intent(SearchListActivity.this, SearchListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shoppingListBtn:
                    searchBtn.setSelected(false);

                    intent = new Intent(SearchListActivity.this, ShoppingListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chattingBtn:
                    searchBtn.setSelected(false);

                    intent = new Intent(SearchListActivity.this, ChattingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mypageBtn:
                    searchBtn.setSelected(false);

                    intent = new Intent(SearchListActivity.this, MyPageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };


}
