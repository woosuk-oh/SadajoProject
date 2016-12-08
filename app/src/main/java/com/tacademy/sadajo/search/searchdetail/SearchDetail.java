package com.tacademy.sadajo.search.searchdetail;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailDB;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailJSONParser;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailTipsJSONParser;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailzzimDB;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailzzimJSONParser;
import com.tacademy.sadajo.network.Search.SeachDetail.TipsContainer;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.tacademy.sadajo.network.NetworkDefineConstant.SEARCH_LIST_DETAIL;

/**
 * Created by woosuk on 2016-11-14.
 */


// TODO 여기서 좋아요, 담기 부분 누를때마다 서버콜, 포스트방식으로 -> user, goods (goods_code임)


public class SearchDetail extends BaseActivity implements ViewPager.OnPageChangeListener {
    ViewPager searchDetailViewPager;
    TextView itemcount;
    TextView country;
    TextView contenttext;
    TextView unit;
    LinearLayout detailhashbutton;
    ImageView countryimg;
    EditText tipsinput;


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

    int img_count; // 가져온 아이템의 이미지 갯수

    String itemidValue; // searchListActivity에서 넘겨준 키("key") + 값(itemName.getText().toString()) 을 "key"로 받아옴


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail_body);

        /* onCreate에서 생성할 위젯 (텍스트뷰) */
        final TextView itemID = (TextView) findViewById(R.id.search_detail_item_name);
        itemID.setSelected(true);
        country = (TextView) findViewById(R.id.search_detail_country_name);
        countryimg = (ImageView) findViewById(R.id.cuntry_detail_image);
        contenttext = (TextView) findViewById(R.id.search_detail_content_text);
        unit = (TextView) findViewById(R.id.detail_price_unit);
        tipsinput = (EditText) findViewById(R.id.search_detail_comment_ed);
        tipsinput.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        tipsinput.setInputType(InputType.TYPE_CLASS_TEXT);

        tipsinput.post(new Runnable() {
            @Override
            public void run() {
                tipsinput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ //입력창 입력후 엔터누르면.
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        Log.d("엔터여부","엔터눌림1");

                        switch (i){
                            case EditorInfo.IME_ACTION_SEARCH:
                                new TipsInputAsync().execute(tipsinput.getText().toString());
                                Log.d("엔터여부","서치 눌림림");
                                break;
                            default:
                                new TipsInputAsync().execute(tipsinput.getText().toString());
                                Log.d("엔터여부","엔터눌림2");
                                return false;
                        }

                        return true;
                    }
                });
            }

        });




        /* 상품의 ID값과 상품명, 판매가능 국가명을 인텐트로 가져옴 */
        Intent intent = getIntent();

        String itemValue;
        String countryValue;
        String countryimgresource;

        itemidValue = intent.getExtras().getString("key");
        itemValue = intent.getExtras().getString("key2");
        countryValue = intent.getExtras().getString("key3");
        countryimgresource = intent.getExtras().getString("key4");

        /* onCreate에서 진행하는 setText */
        itemID.setText(itemValue); // 인텐트로 받아온 아이템의 id와 아이템의 이름 중 이름을 setText 해준다.
        country.setText(countryValue);

        Glide.with(SadajoContext.getContext())
                .load(countryimgresource)
                .into(countryimg);

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


        detailhashbutton = (LinearLayout) findViewById(R.id.list_detail_hash_button);


        /*Async 내릴때 아이템의 id값인 itemidValue 보내줘야 해당 아이디로 get요청함.*/
        new AsyncSearchDetailRequest().execute(itemidValue);
    }


    public class AsyncSearchDetailRequest extends AsyncTask<String, Void, SearchDetailDB> {
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
              //  Toast.makeText(SearchDetail.this, "서버와의 연결이 원활치 않음", Toast.LENGTH_SHORT).show();
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


    /*        // 상품 판매 국가명 연결 [12.05 11:31 수정 -> 인텐트로 가져온 값을 onCreate에서 대신 setText 해줌]
            country.setText(searchDetailDB.getGoods_country());*/

            // 쇼퍼맨에게 부탁해볼까요? 리싸이클러뷰 연결. (인자값 보냄)
            mAdapter = new DetailShopermanRecyclerAdapter(SadajoContext.getContext(), s.shoperman);
            mRecycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            // 얼마에 구매했어요? 리싸이클러뷰 연결
            mAdapter2 = new DetailPriceItemsRecyclerAdapter(s.tag_price, SadajoContext.getContext());
            mRecycler2.setAdapter(mAdapter2);
            mAdapter2.notifyDataSetChanged();

            // 얼마에 구매했어요?2번째 부분 리싸이클러뷰 연결
            mAdapter3 = new DetailPriceItems2RecyclerAdapter(s.price, SadajoContext.getContext());
            mRecycler3.setAdapter(mAdapter3);
            mAdapter3.notifyDataSetChanged();

            unit.setText(s.getUnit().toString());

            // 쇼퍼맨이 알려주는 구매 TIP (댓글)
            mAdapter4 = new DetailCommentRecyclerAdapter(s.tips, SadajoContext.getContext());
            mRecycler4.setAdapter(mAdapter4);
            mAdapter4.notifyDataSetChanged();



            // 어디서 살 수 있어요?
            mAdapter5 = new DetailItemLocationRecyclerAdapter(s.sell_place, SadajoContext.getContext());
            mRecycler5.setAdapter(mAdapter5);
            mAdapter5.notifyDataSetChanged();

            // PagerAdapter 연결.
            searchDetailPagerAdapter.setImageList(s.getGoods_img()); // 네트워크로 부터 받아온 goods_img를 페이저어댑터 메소드에서 set해주기 위해 인자값으로 보내줌.
            searchDetailPagerAdapter.notifyDataSetChanged();


            // 상품의 이미지 갯수 가져와서 setText.
            img_count = s.getGoods_img().size();
            itemcount.setText(String.valueOf(1)+" / " + img_count);


            // 해시태그 가져와서 갯수만큼 for문 돌려서, createTagButton 커스텀 메소드로 버튼 동적 생성


            ArrayList hashArray;
            hashArray = s.getHashtag();
            Log.d("해시태그:","해시태그(SearchDetail)"+s.getHashtag().get(1).toString());

            int hashArraySize = hashArray.size();
            Log.d("해시태그사이즈:","해시태그 사이즈(SearchDetail)"+hashArraySize);

            for (int i = 0; i < hashArraySize; i++) {
                detailhashbutton.addView(createTagButton(hashArray.get(i).toString(), i));
            }
        }
    }



    public class TipsInputAsync extends AsyncTask<String, Void, TipsContainer>{
// TODO 서버에서 TIPS만 내려주면 onPostExcute에서 실행할 TIPS전용 파서 만들어야됌.

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected TipsContainer doInBackground(String... strings) {
            String inputValue = strings[0];

            OkHttpClient toServer;
            toServer = OkHttpInitManager.getOkHttpClient();
            okhttp3.Response response = null;

            TipsContainer searchDetailTipsDB = new TipsContainer();


            try{
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user_code", String.valueOf(21)) //TODO 쉐어드프리페어런스로 적용.
                        .add("content",String.valueOf(inputValue))
                        .build();

                Log.d("요청한값","입력해서 요청한값(TIPS):"+inputValue);

                Request request = new Request.Builder()
                        .url(NetworkDefineConstant.SEARCH_LIST_DETAIL_TIPS + "/"+ itemidValue)
                        .post(postBody)
                        .build();
                Log.d("요청한url","입력된 url:"+(NetworkDefineConstant.SEARCH_LIST_DETAIL_TIPS+"/"+ itemidValue).toString());

                response = toServer.newCall(request).execute();

                if (response.isSuccessful()){
                    String returnMessage = response.body().string();
                    searchDetailTipsDB = SearchDetailTipsJSONParser.getSearchDetailTipsJsonParser(returnMessage); //만들어둔 파서로 returedMessage를 넣어서 파싱하여 homeDB에 값을 넣음.

                }
            }catch (Exception e){
                Log.e("파싱에러", e.toString());
            }finally {
                if(response != null){
                    response.close();
                }
            }


            return searchDetailTipsDB;
        }

        @Override
        protected void onPostExecute(TipsContainer s) {
            super.onPostExecute(s);

                mAdapter4 = new DetailCommentRecyclerAdapter(s.tips, SadajoContext.getContext());
                mRecycler4.setAdapter(mAdapter4);
                mAdapter4.notifyDataSetChanged();
                scrollToBottom();


        }


    }






            View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.detail_zzim_button:
                    Toast.makeText(SearchDetail.this, "찜하기 완료!", Toast.LENGTH_SHORT).show();



                    zzim.post(new Runnable() {
                        @Override
                        public void run() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                OkHttpClient toServer;
                                toServer=OkHttpInitManager.getOkHttpClient();
                                Response response = null; // 응답


                                SearchDetailzzimDB zzimcountint = new SearchDetailzzimDB();
                                toServer=OkHttpInitManager.getOkHttpClient();

                                RequestBody postBody = new FormBody.Builder()
                                        .add("user", "1")
                                        .add("goods", itemidValue)
                                        .build();

                                Request request = new Request.Builder()
                                        .url(String.format(NetworkDefineConstant.SEARCH_LIST_DETAIL_ZZIM))
                                        .post(postBody)
                                        .build();



                                //동기 방식
                                try

                                {
                                    response = toServer.newCall(request).execute();


                                    if (response.isSuccessful()) { //연결에 성공하면

                                        String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.

                                        zzimcountint = SearchDetailzzimJSONParser.getSearchDetailzzimJsonParser(returedMessage);
                                        Log.d("찜","insertID 값 테스트. 들어갔는지??"+zzimcountint.getZzimcount());

                                        //TODO 서버에서 goods(goods_code)별 count, state(눌림,안눌림) 처리가 완료되면 paser 수정, TextView에 적용 필요, selector 또한.
                                    }

                                }catch(IOException e)
                                {
                                    e.printStackTrace();
                                    Toast.makeText(SearchDetail.this, "일시적 네트워크 에러로 찜하기 실패", Toast.LENGTH_SHORT).show();
                                }

                                finally

                                {
                                    if (response != null) {
                                        response.close();
                                    }
                                }

                                return;
                                }
                            }).start();
                        }
                    });
                    break;


                // TODO 위 찜하기 처럼 .post내려주고 파서 만들기. (서버 완료되면)
                case R.id.detail_shopping_button: //쇼핑리스트 클릭한 경우우

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
        itemcount.setText(String.valueOf(position + 1)+" / " + img_count);
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
                    .centerCrop()
                    .thumbnail(0.1f)
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

        public void setImageList(ArrayList<String> imageList) {// 어댑터로 셋 해줄 메소드 생성
            this.imageList = imageList;
        }
    }


    // TODO 댓글 작성 완료하면 다시 받아와서 스크롤 포지션 내려야됌.
    private void scrollToBottom() {
        mRecycler4.scrollToPosition(mAdapter4.getItemCount() - 1);
    }


    public Button createTagButton(String str, int i) {
        Button button = new Button(SadajoContext.getContext());
        button.setText(str); //서버로부터 받아온 tag text set
        button.setBackgroundResource(R.drawable.tag_button_file); //tag ninepatch background적용

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = 69;


        // TODO 버튼 셋 온클릭 리스너 달아줘야함.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        button.setPadding(15, 0, 15, 0); // left,right padding : 3
        params.setMargins(0, 0, 45, 45); // top, right margin : 15
        button.setGravity(Gravity.CENTER); //gravity : center
        button.setTextSize(13);// textsize : 13sp
        button.setTypeface(null, Typeface.NORMAL);//textstyle : Nanum M
        button.setLayoutParams(params);
        button.setTag("HomeTag");
        button.setId(i);
        //button.setOnClickListener(buttonClickListener);
        return button;
//        list_item_hash_button.addView(button); // button added


    }
}
