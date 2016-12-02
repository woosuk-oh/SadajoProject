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
    //푸쉬 테스트

    RecyclerView.LayoutManager layoutManager;
    private DetailShopermanRecyclerAdapter mAdapter;
    private DetailPriceItemsRecyclerAdapter mAdapter2;
    private DetailItemLocationRecyclerAdapter mAdapter3;
    private DetailCommentRecyclerAdapter mAdapter4;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private RecyclerView mRecycler;
    private RecyclerView mRecycler2;
    private RecyclerView mRecycler3;
    private RecyclerView mRecycler4;
    ArrayList<Integer> imageList = new ArrayList<>();

    Button zzim;
    Button shopping;

    Toolbar searchDetailToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail_body);


//        final ImageView itemImage = (ImageView) findViewById(R.id.item_image);
        final TextView itemID = (TextView) findViewById(R.id.search_detail_item_name);
        country = (TextView) findViewById(R.id.search_detail_country_name);


        Intent intent = getIntent();

        String itemidValue; // searchListActivity에서 넘겨준 키("key") + 값(itemName.getText().toString()) 을 "key"로 받아옴
        String itemValue;
        String countryValue;

        itemidValue = intent.getExtras().getString("key");
        itemValue = intent.getExtras().getString("key2");
        countryValue = intent.getExtras().getString("key3");
        itemID.setText(itemValue); // 인텐트로 받아온 아이템의 id와 아이템의 이름 중 이름을 setText 해준다.
        country.setText(countryValue);




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





        itemcount = (TextView) findViewById(R.id.itemcount);
        itemcount.setText(String.valueOf(1) + "/4");
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
        mAdapter = new DetailShopermanRecyclerAdapter(items, this);


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


        /*Async 내릴때 아이템의 id값인 itemidValue 보내줘야 해당 아이디로 get요청함.*/
        new AsyncSearchDetailRequest().execute(itemidValue);
    }


    public class AsyncSearchDetailRequest extends AsyncTask<String, Void, SearchDetailDB> {


        //첫번째 Void: doInBackgorund로 보내는
        //두번째 Void: Progress
        //세번째 onPostExecute에서 사용할 파라미터값.


        String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected SearchDetailDB doInBackground(String... params) {
            OkHttpClient toServer;
            toServer = OkHttpInitManager.getOkHttpClient();
            String itemidValue = params[0];
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
        protected void onPostExecute(SearchDetailDB searchDetailDB) {
            super.onPostExecute(searchDetailDB);


            country.setText(searchDetailDB.getGoods_country());



            mAdapter = new DetailShopermanRecyclerAdapter(SearchDetail.this, searcDB.searchGoodsDBs); // TODO 쇼퍼맨에게 부탁해볼까요에 맞는 데이터모델로 수정.
            mRecycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();


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
        itemcount.setText(String.valueOf(position + 1) + "/4");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class SearchDetailPagerAdapter extends PagerAdapter {


        public SearchDetailPagerAdapter() {
            imageList.add(R.drawable.detail_item_img_sample);
            imageList.add(R.drawable.detail_item_img_sample_2);
            imageList.add(R.drawable.detail_item_img_sample_3);

            Glide.with(SadajoContext.getContext())
                    .load(.get(position).getItem_img())
                    .into(holder.itemImage);
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
    }
}
