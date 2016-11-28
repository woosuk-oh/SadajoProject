package com.tacademy.sadajo.search.searchdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.R;

import java.util.ArrayList;

/**
 * Created by woosuk on 2016-11-14.
 */

public class SearchDetail extends BaseActivity implements ViewPager.OnPageChangeListener{
    ViewPager searchDetailViewPager;
    TextView itemcount;
    //푸쉬 테스트

    RecyclerView.LayoutManager layoutManager;
    private SearchDetailRecyclerAdapter mAdapter;
    private DetailPriceItemsRecyclerAdapter mAdapter2;
    private DetailItemLocationRecyclerAdapter mAdapter3;
    private DetailCommentRecyclerAdapter mAdapter4;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private RecyclerView mRecycler;
    private RecyclerView mRecycler2;
    private RecyclerView mRecycler3;
    private RecyclerView mRecycler4;

    Button zzim;
    Button shopping;

    Toolbar searchDetailToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail_body);



//        final ImageView itemImage = (ImageView) findViewById(R.id.item_image);
        final TextView itemID = (TextView) findViewById(R.id.search_detail_item_name);

        Intent intent = getIntent();
        String itemValue; // searchListActivity에서 넘겨준 키("key") + 값(itemName.getText().toString()) 을 "key"로 받아옴
        itemValue = intent.getExtras().getString("key");

        itemID.setText(itemValue); //itemID로 셋팅한 TextView에 인텐트로부터 받아온 id를 setText해준다.



        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout1);
        collapsingToolbarLayout.setTitle(itemValue);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent)); // 콜랩스바 펼쳐져있을때 텍스트 투명
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white)); // 콜랩스바 콜랩스됐을때 텍스트 흰색



        searchDetailToolbar = (Toolbar) findViewById(R.id.search_detail_body_toolbar);
        setSupportActionBar(searchDetailToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.search_back_icon2);
       /* actionBar.setTitle(itemValue);*/
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);



        itemcount = (TextView) findViewById(R.id.itemcount);
        itemcount.setText(String.valueOf(1)+"/4");
        searchDetailViewPager = (ViewPager) findViewById(R.id.search_detail_viewpager);
        searchDetailViewPager.addOnPageChangeListener(this);
        SearchDetailPagerAdapter searchDetailPagerAdapter = new SearchDetailPagerAdapter();
        searchDetailViewPager.setAdapter(searchDetailPagerAdapter);





        mRecycler = (RecyclerView) findViewById(R.id.search_detail_shopperuser_recyclerview);
        mRecycler2 = (RecyclerView) findViewById(R.id.search_detail_itemprice_recyclerview);
        mRecycler3 = (RecyclerView) findViewById(R.id.search_detail_location_recyclerview);
        mRecycler4 = (RecyclerView) findViewById(R.id.search_detail_comment_recyclerview);


        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecycler.setLayoutManager(layoutManager); //리싸이클러뷰의 레이아웃매니저 적용

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler2.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler3.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler4.setLayoutManager(layoutManager);




        ArrayList<ItemArrayList> items = new ArrayList<>();

        items.add(new ItemArrayList(R.drawable.sample1, "산타노벨라 향수"));
        items.add(new ItemArrayList(R.drawable.sample2, "샘플2"));
        items.add(new ItemArrayList(R.drawable.sample3, "샘플3"));
        items.add(new ItemArrayList(R.drawable.sample4, "샘플4"));
        items.add(new ItemArrayList(R.drawable.sample5, "샘플5"));
        mAdapter = new SearchDetailRecyclerAdapter(items, this);



        ArrayList<String> items2 = new ArrayList<>();
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));
        items2.add(new String("dd"));

        mAdapter2 = new DetailPriceItemsRecyclerAdapter(items2, this);

        ArrayList<String> items3 = new ArrayList<>();
        items3.add(new String("dd"));
        items3.add(new String("dd"));
        items3.add(new String("dd"));
        items3.add(new String("dd"));
        items3.add(new String("dd"));
        items3.add(new String("dd"));
        items3.add(new String("dd"));

        mAdapter3 = new DetailItemLocationRecyclerAdapter(items3, this);



        ArrayList<ItemArrayList2> items4 = new ArrayList<>();

        items4.add(new ItemArrayList2(R.drawable.sample1, "이번주에 샀는데 두개 사면 하나 20% 할인해주더라구여!!!!!!!!!!!!!!!!!!!", "3시간"));
        items4.add(new ItemArrayList2(R.drawable.sample2, "이번주에 샀는데 두개 사면 하나 20% 할인해주더라구여!!!!!!!!!!!!!!!!!!!", "4시간"));
        items4.add(new ItemArrayList2(R.drawable.sample3, "이번주에 샀는데 두개 사면 하나 20% 할인해주더라구여!!!!!!!!!!!!!!!!!!!", "4시간"));
        items4.add(new ItemArrayList2(R.drawable.sample4, "이번주에 샀는데 두개 사면 하나 20% 할인해주더라구여!!!!!!!!!!!!!!!!!!!", "5시간"));
        items4.add(new ItemArrayList2(R.drawable.sample5, "이번주에 샀는데 두개 사면 하나 20% 할인해주더라구여!!!!!!!!!!!!!!!!!!!", "6시간"));
        mAdapter4 = new DetailCommentRecyclerAdapter(items4, this);


        mRecycler.setAdapter(mAdapter); // 사용자 써클 이미지
        mRecycler2.setAdapter(mAdapter2); // 얼마에 구매하셨어요?
        mRecycler3.setAdapter(mAdapter3); // 어디서 살수있어요?
        mRecycler4.setAdapter(mAdapter4); // 쇼퍼맨이 알려주는 구매 TIP

        mAdapter.notifyDataSetChanged();
        mAdapter2.notifyDataSetChanged();
        mAdapter3.notifyDataSetChanged();
        mAdapter4.notifyDataSetChanged();


        zzim = (Button) findViewById(R.id.detail_zzim_button);
        zzim.setOnClickListener(onClickListener);

        shopping = (Button) findViewById(R.id.detail_shopping_button);
        shopping.setOnClickListener(onClickListener);

    }


    Button.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.detail_zzim_button:
                    Toast.makeText(SearchDetail.this, "찜하기 완료!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.detail_shopping_button:

                    Toast.makeText(SearchDetail.this, "쇼핑리스트에 담겼습니다", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default: return false;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        itemcount.setText(String.valueOf(position+1)+"/4");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }




    private class SearchDetailPagerAdapter extends PagerAdapter{
        ArrayList<Integer> imageList = new ArrayList<>();

        public SearchDetailPagerAdapter() {
            imageList.add(R.drawable.detail_item_img_sample);
            imageList.add(R.drawable.detail_item_img_sample_2);
            imageList.add(R.drawable.detail_item_img_sample_3);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) { // onBindView
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_detail_view_pager_item, container, false);
            ImageView itemImg = (ImageView) view.findViewById(R.id.search_detail_viewpager_item_img);
            itemImg.setImageResource(imageList.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
