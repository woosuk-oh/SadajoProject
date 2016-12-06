package com.tacademy.sadajo.search.searchdetail;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailDB;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailJSONParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.tacademy.sadajo.network.NetworkDefineConstant.SEARCH_LIST_DETAIL;

/**
 * Created by woosuk on 2016-11-14.
 */

public class SearchDetail extends BaseActivity implements ViewPager.OnPageChangeListener {
    ViewPager searchDetailViewPager;
    TextView itemcount;
    TextView country;
    TextView contenttext;
    //푸쉬 테스트

    RecyclerView.LayoutManager layoutManager;
    private DetailShopermanRecyclerAdapter mAdapter;
    private DetailPriceItemsRecyclerAdapter mAdapter2;
    private DetailPriceItems2RecyclerAdapter mAdapter3;
    private DetailCommentRecyclerAdapter mAdapter4;
    private DetailItemLocationRecyclerAdapter mAdapter5;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private RecyclerView mRecycler;
    private RecyclerView mRecycler2;
    private RecyclerView mRecycler3;
    private RecyclerView mRecycler4;
    private RecyclerView mRecycler5;


    ArrayList<Integer> imageList = new ArrayList<>();

    Button zzim;
    Button shopping;

    Toolbar searchDetailToolbar;

    SearchDetailPagerAdapter searchDetailPagerAdapter;
    SearchDetailDB searchDetailDB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail_body);

        /* onCreate에서 생성할 위젯 (텍스트뷰) */
        final TextView itemID = (TextView) findViewById(R.id.search_detail_item_name);
        country = (TextView) findViewById(R.id.search_detail_country_name);
        contenttext = (TextView) findViewById(R.id.search_detail_content_text);


        /* 상품의 ID값과 상품명, 판매가능 국가명을 인텐트로 가져옴 */
        Intent intent = getIntent();
        int itemidValue; // searchListActivity에서 넘겨준 키("key") + 값(itemName.getText().toString()) 을 "key"로 받아옴
        String itemValue;
        String countryValue;

        itemidValue = intent.getExtras().getInt("key");
        itemValue = intent.getExtras().getString("key2");
        countryValue = intent.getExtras().getString("key3");

        /* onCreate에서 진행하는 setText */
        itemID.setText(itemValue); // 인텐트로 받아온 아이템의 id와 아이템의 이름 중 이름을 setText 해준다.
        country.setText(countryValue);


        /* 상단 콜랩싱 부분 */
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout1);
        collapsingToolbarLayout.setTitle(itemValue);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent)); // 콜랩스바 펼쳐져있을때 텍스트 투명
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white)); // 콜랩스바 콜랩스됐을때 텍스트 흰색


        searchDetailToolbar = (Toolbar) findViewById(R.id.search_detail_body_toolbar);
        setSupportActionBar(searchDetailToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.search_back_icon2);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);



        // 상품의 이미지 갯수 카운트 변수.
        itemcount = (TextView) findViewById(R.id.itemcount);


        // 이미지부분 뷰페이저 초기화 부분.
        searchDetailViewPager = (ViewPager) findViewById(R.id.search_detail_viewpager);
        searchDetailViewPager.addOnPageChangeListener(this);
        searchDetailPagerAdapter = new SearchDetailPagerAdapter();
        searchDetailViewPager.setAdapter(searchDetailPagerAdapter);

        /* 리싸이클러뷰 onCreate에서 그려줌.*/
        mRecycler = (RecyclerView) findViewById(R.id.search_detail_shopperuser_recyclerview);
        mRecycler2 = (RecyclerView) findViewById(R.id.search_detail_itemprice_recyclerview);
        mRecycler3 = (RecyclerView) findViewById(R.id.search_detail_itemprice2_recyclerview);

        mRecycler4 = (RecyclerView) findViewById(R.id.search_detail_comment_recyclerview);
        mRecycler5 = (RecyclerView) findViewById(R.id.search_detail_location_recyclerview);


        /* 디테일 페이지에서 필요한 리싸이클러뷰들 레이아웃 적용 */
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecycler.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler2.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler3.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler4.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler5.setLayoutManager(layoutManager);




        /* 콜랩싱바에 있는 버튼 (찜하기, 담기) 위젯들. */
        zzim = (Button) findViewById(R.id.detail_zzim_button);
        zzim.setOnClickListener(onClickListener);

        shopping = (Button) findViewById(R.id.detail_shopping_button);
        shopping.setOnClickListener(onClickListener);


        /*Async 내릴때 아이템의 id값인 itemidValue 보내줘야 해당 아이디로 get요청함.*/
        new AsyncSearchDetailRequest().execute(itemidValue);
    }


    public class AsyncSearchDetailRequest extends AsyncTask<Integer, Void, SearchDetailDB> {
    /*     인자값 설명
        첫번째 Void: doInBackgorund로 보내는
        두번째 Void: Progress
        세번째 onPostExecute에서 사용할 파라미터값.*/


        String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected SearchDetailDB doInBackground(Integer... params) {
            OkHttpClient toServer;
            toServer = OkHttpInitManager.getOkHttpClient();
            int itemidValue = params[0];
            Response response = null; // 응답

            SearchDetailDB searchDetailDB = new SearchDetailDB();
            url = String.format(SEARCH_LIST_DETAIL, itemidValue);

            try {
            /* get 방식으로 받기 */
                //String url = String.format(SEARCH_LIST, countryId);


                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Log.e("url3", "" + url);
                response = toServer.newCall(request).execute();

                if (response.isSuccessful()) { //연결에 성공하면
                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    Log.e("SearchDetail Body:", returedMessage);

                    searchDetailDB = SearchDetailJSONParser.getSearchDetailJsonParser(returedMessage); //만들어둔 파서로 returedMessage를 넣어서 파싱하여 homeDB에 값을 넣음.

                } else { // 연결에 실패하면
                    Log.e("요청/응답", response.message().toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            return searchDetailDB;


        }

        @Override
        protected void onPostExecute(SearchDetailDB s) {
            super.onPostExecute(s);


            contenttext.setText(s.getGoods_content().toString());
            Log.d("컨텐트",""+s.getGoods_content().toString());

    /*        // 상품 판매 국가명 연결 [12.05 11:31 수정 -> 인텐트로 가져온 값을 onCreate에서 대신 setText 해줌]
            country.setText(searchDetailDB.getGoods_country());*/

            // 쇼퍼맨에게 부탁해볼까요? 리싸이클러뷰 연결. (인자값 보냄)
            mAdapter = new DetailShopermanRecyclerAdapter(SadajoContext.getContext(), s.shoperman);
            mRecycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            // 얼마에 구매했어요? 리싸이클러뷰 연결
            mAdapter2 = new DetailPriceItemsRecyclerAdapter(s.tag_price, SadajoContext.getContext());
            Log.d("tag_price","얼마에 구매했어요?"+s.tag_price.get(0));
            mRecycler2.setAdapter(mAdapter2);
            mAdapter2.notifyDataSetChanged();

            // TODO 구매한 가격 (price) 는 따로 for문 돌려야 할듯
            mAdapter3 = new DetailPriceItems2RecyclerAdapter(s.price, SadajoContext.getContext());
            mRecycler3.setAdapter(mAdapter3);
            mAdapter3.notifyDataSetChanged();


            // 쇼퍼맨이 알려주는 구매 TIP (댓글)
            mAdapter4 = new DetailCommentRecyclerAdapter(s.tips, SadajoContext.getContext());
            mRecycler4.setAdapter(mAdapter4);
            mAdapter4.notifyDataSetChanged();

            // 어디서 살 수 있어요?
            mAdapter5 = new DetailItemLocationRecyclerAdapter(s.sell_place,SadajoContext.getContext());
            mRecycler5.setAdapter(mAdapter5);
            mAdapter5.notifyDataSetChanged();

            // PagerAdapter 연결.
            searchDetailPagerAdapter.setImageList(s.getGoods_img()); // 네트워크로 부터 받아온 goods_img를 페이저어댑터 메소드에서 set해주기 위해 인자값으로 보내줌.
            searchDetailPagerAdapter.notifyDataSetChanged();


            // 상품의 이미지 갯수 가져와서 setText.
            itemcount.setText(String.valueOf(1) + s.getGoods_img().size());
        }
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
    public boolean onOptionsItemSelected(MenuItem item) { //뒤로가기 버튼 눌렀을때.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        itemcount.setText(String.valueOf(position + 1) + searchDetailDB.getGoods_img().size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

/* 이미지 뷰페이지 + 페이저 어댑터 */
    private class SearchDetailPagerAdapter extends PagerAdapter {
        private ArrayList<String> imageList = new ArrayList<>();

        public SearchDetailPagerAdapter() {
        }

    /* 페이저 어댑터에서    onBindView 역할 + */
        @Override
        public Object instantiateItem(ViewGroup container, int position) { // onBindView
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_detail_view_pager_item, container, false);
            ImageView itemImg = (ImageView) view.findViewById(R.id.search_detail_viewpager_item_img);
            Glide.with(SadajoContext.getContext())
                    .load(imageList.get(position))
                    .into(itemImg);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setImageList(ArrayList<String> imageList){// 어댑터로 셋 해줄 메소드 생성
            this.imageList = imageList;
        }
    }
}
