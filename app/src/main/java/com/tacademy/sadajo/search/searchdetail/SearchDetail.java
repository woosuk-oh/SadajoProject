package com.tacademy.sadajo.search.searchdetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailDB;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailJSONParser;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailTipsJSONParser;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailshoppingDB;
import com.tacademy.sadajo.network.Search.SeachDetail.SearchDetailzzimDB;
import com.tacademy.sadajo.network.Search.SeachDetail.TipsContainer;
import com.tacademy.sadajo.search.searchlist.SearchListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.tacademy.sadajo.SadajoContext.getContext;
import static com.tacademy.sadajo.network.NetworkDefineConstant.SEARCH_LIST_DETAIL;

/**
 * Created by woosuk on 2016-11-14.
 */


public class SearchDetail extends BaseActivity implements ViewPager.OnPageChangeListener {
    ViewPager searchDetailViewPager;
    TextView itemcount;
    TextView country;
    TextView contenttext;
    TextView unit;
    LinearLayout detailhashbutton;
    ImageView countryimg;
    EditText tipsinput;

    TextView zzimtext;
    TextView shoptext;



    int userAccount; //쉐어드 프리페어런스로 받기 전 초기화.

    SearchDetailzzimDB zzimcountint = new SearchDetailzzimDB(); // 찜한 카운트
    SearchDetailshoppingDB shoppingcountint = new SearchDetailshoppingDB(); // 쇼핑리스트 담은 카운트


    private LinearLayoutManager layoutManager2; // 댓글달면 scrolltobottm 하기 위해 해줌.

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


    NestedScrollView scrollView;


    ArrayList<Integer> imageList = new ArrayList<>();

    Button zzim; //찜하기 버튼
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


        scrollView = (NestedScrollView) findViewById(R.id.detail_body_scrollview);

        /* onCreate에서 생성할 위젯 (텍스트뷰) */
        final TextView itemID = (TextView) findViewById(R.id.search_detail_item_name);
        itemID.setSelected(true);

        zzimtext = (TextView) findViewById(R.id.detail_zzim_text);
        shoptext = (TextView) findViewById(R.id.detail_shopping_text);
        country = (TextView) findViewById(R.id.search_detail_country_name);
        countryimg = (ImageView) findViewById(R.id.cuntry_detail_image);
        contenttext = (TextView) findViewById(R.id.search_detail_content_text);
        unit = (TextView) findViewById(R.id.detail_price_unit);
        tipsinput = (EditText) findViewById(R.id.search_detail_comment_ed);
        tipsinput.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        tipsinput.setInputType(InputType.TYPE_CLASS_TEXT);


        // SharedPreference 아이디값 userAccount로 가져옴.
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        userAccount = sharedPreferenceUtil.getAccessToken();

        tipsinput.post(new Runnable() {
            @Override
            public void run() {
                tipsinput.setOnEditorActionListener(new TextView.OnEditorActionListener() { //입력창 입력후 엔터누르면.
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        Log.d("엔터여부", "엔터눌림1");

                        switch (i) {
                            case EditorInfo.IME_ACTION_SEARCH:
                                new TipsInputAsync().execute(tipsinput.getText().toString());
                                tipsinput.setText("");
                                Log.d("엔터여부", "서치 눌림림");
                                break;
                            default:
                                new TipsInputAsync().execute(tipsinput.getText().toString());
                                Log.d("엔터여부", "엔터눌림2");
                                tipsinput.setText("");
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

        Glide.with(getContext())
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

        layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler4.setLayoutManager(layoutManager2);


        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler5.setLayoutManager(layoutManager);

//        detailscroll = (ScrollView) findViewById(R.id.detail_body_scrollview); // scrolltobottom 먹이기 위해서 했는데 안됌..



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

            // searchDetailDB = new SearchDetailDB();
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
            zzimtext.setText(String.valueOf(s.getZzimcount()));
            shoptext.setText(String.valueOf(s.getShopcount()));


    /*        // 상품 판매 국가명 연결 [12.05 11:31 수정 -> 인텐트로 가져온 값을 onCreate에서 대신 setText 해줌]
            country.setText(searchDetailDB.getGoods_country());*/

            // 쇼퍼맨에게 부탁해볼까요? 리싸이클러뷰 연결. (인자값 보냄)
            mAdapter = new DetailShopermanRecyclerAdapter(getContext(), s.shoperman);
            mRecycler.setAdapter(mAdapter);
            mRecycler.setNestedScrollingEnabled(false); // 리싸이클러뷰 스크롤 내리거나 올리는거 빡빡한 경우 세팅해줌.
            mAdapter.notifyDataSetChanged();

            // 얼마에 구매했어요? 리싸이클러뷰 연결
            mAdapter2 = new DetailPriceItemsRecyclerAdapter(s.tag_price, getContext());
            mRecycler2.setAdapter(mAdapter2);
            mRecycler2.setNestedScrollingEnabled(false); // 리싸이클러뷰 스크롤 내리거나 올리는거 빡빡한 경우 세팅해줌.
            mAdapter2.notifyDataSetChanged();

            // 얼마에 구매했어요?2번째 부분 리싸이클러뷰 연결
            mAdapter3 = new DetailPriceItems2RecyclerAdapter(s.price, getContext());
            mRecycler3.setAdapter(mAdapter3);
            mRecycler3.setNestedScrollingEnabled(false); // 리싸이클러뷰 스크롤 내리거나 올리는거 빡빡한 경우 세팅해줌.
            mAdapter3.notifyDataSetChanged();

            unit.setText(s.getUnit().toString());

            // 쇼퍼맨이 알려주는 구매 TIP (댓글)
            mAdapter4 = new DetailCommentRecyclerAdapter(s.tips, getContext());
            mRecycler4.setAdapter(mAdapter4);
            mRecycler4.setNestedScrollingEnabled(false); // 리싸이클러뷰 스크롤 내리거나 올리는거 빡빡한 경우 세팅해줌.
            mAdapter4.notifyDataSetChanged();


            // 어디서 살 수 있어요?
            mAdapter5 = new DetailItemLocationRecyclerAdapter(s.sell_place, getContext());
            mRecycler5.setAdapter(mAdapter5);
            mAdapter5.notifyDataSetChanged();

            // PagerAdapter 연결.
            searchDetailPagerAdapter.setImageList(s.getGoods_img()); // 네트워크로 부터 받아온 goods_img를 페이저어댑터 메소드에서 set해주기 위해 인자값으로 보내줌.
            searchDetailPagerAdapter.notifyDataSetChanged();


            // 상품의 이미지 갯수 가져와서 setText.
            img_count = s.getGoods_img().size();
            itemcount.setText(String.valueOf(1) + " / " + img_count);


            // 해시태그 가져와서 갯수만큼 for문 돌려서, createTagButton 커스텀 메소드로 버튼 동적 생성


            ArrayList hashArray;
            hashArray = s.getHashtag();
            if (s.getHashtag() != null) {
                Log.d("해시태그:", "해시태그(SearchDetail)" + s.getHashtag().get(0).toString());

                int hashArraySize = hashArray.size();
                Log.d("해시태그사이즈:", "해시태그 사이즈(SearchDetail)" + hashArraySize);

                for (int i = 0; i < hashArraySize; i++) {
                    detailhashbutton.addView(createTagButton(hashArray.get(i).toString(), i));
                }
            }
        }
    }


    public class TipsInputAsync extends AsyncTask<String, Void, TipsContainer> {


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


            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user_code", "" + userAccount)
                        .add("content", String.valueOf(inputValue))
                        .build();

                Log.d("요청한값", "입력해서 요청한값(TIPS):" + inputValue);

                Request request = new Request.Builder()
                        .url(NetworkDefineConstant.SEARCH_LIST_DETAIL_TIPS + "/" + itemidValue)
                        .post(postBody)
                        .build();
                Log.d("요청한url", "입력된 url:" + (NetworkDefineConstant.SEARCH_LIST_DETAIL_TIPS + "/" + itemidValue).toString());

                response = toServer.newCall(request).execute();

                if (response.isSuccessful()) {
                    String returnMessage = response.body().string();
                    searchDetailTipsDB = SearchDetailTipsJSONParser.getSearchDetailTipsJsonParser(returnMessage); //만들어둔 파서로 returedMessage를 넣어서 파싱하여 homeDB에 값을 넣음.

                }
            } catch (Exception e) {
                Log.e("파싱에러", e.toString());
            } finally {
                if (response != null) {
                    response.close();
                }
            }


            return searchDetailTipsDB;
        }

        @Override
        protected void onPostExecute(TipsContainer s) {
            super.onPostExecute(s);

            mAdapter4 = new DetailCommentRecyclerAdapter(s.tips, getContext());
            mRecycler4.setAdapter(mAdapter4);
            mAdapter4.notifyDataSetChanged();
            mRecycler4.setNestedScrollingEnabled(false);

            scrollView.post(new Runnable() { // 댓글달리면 밑으로 포커스 이동.

                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });




           /* HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.detail_body_scrollview);
            TextView textView = (TextView) findViewById(R.id.detail_comment_body);
            int x, y;
            x = textView.getLeft();
            y = textView.getTop();
            hsv.scrollTo(x, y);



            mAdapter4.setTranscriptMode(RecyclerView.TRANSCRIPT_MODE_ALWAYS_SCROLL);


            scrollToBottom();
*/
 /*           // TODO 스크롤 내리기 안됌
            mAdapter4.notifyItemMoved(1,5);
            mRecycler4.scrollTo(300, 300);
*/
        /*    scrollToPosition(mRecycler4.getAdapter().getItemCount());
            itemexpandarea = (LinearLayout) v.findViewById(R.id.item_expand_area);

            itemexpandarea.*/

            //mAdapter4.getItemCount()-1);


        }


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        String type;
        String shopType;
        int count;
        int shopCount;
        String result;
        String listcode;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.detail_zzim_button:
                    //Toast.makeText(SearchDetail.this, "찜하기 완료!", Toast.LENGTH_SHORT).show();


                    zzim.post(new Runnable() {
                        @Override
                        public void run() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    OkHttpClient toServer;
                                    toServer = OkHttpInitManager.getOkHttpClient();
                                    Response response = null; // 응답


                                    toServer = OkHttpInitManager.getOkHttpClient();

                                    RequestBody postBody = new FormBody.Builder()
                                            .add("user", "" + userAccount)
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


                                            JSONObject jsonObject = new JSONObject(returedMessage);
                                            type = jsonObject.optString("type"); //type결과 가져오기
                                            count = jsonObject.optInt("likeCount"); //type결과 가져오기

                                            //zzimcountint = SearchDetailzzimJSONParser.getSearchDetailzzimJsonParser(returedMessage);


                                            //Log.d("찜", "insertID 값 테스트. 들어갔는지??" + zzimcountint.getZzimcount());
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {


                                                    if (type.equals("failed")) {
                                                        Toast toast = new Toast(SadajoContext.getContext());
                                                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                        toast.setDuration(Toast.LENGTH_SHORT);
                                                        ImageView aleady = new ImageView(SadajoContext.getContext());
                                                        aleady.setImageResource(R.drawable.search_toast1);
                                                        toast.setView(aleady);

                                                        toast.show();
                                                        // zzimtext.setText(String.valueOf(count));


                                                    } else {
                                                        Toast toast = new Toast(SadajoContext.getContext());
                                                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                        toast.setDuration(Toast.LENGTH_SHORT);
                                                        ImageView zzimtoast = new ImageView(SadajoContext.getContext());
                                                        zzimtoast.setImageResource(R.drawable.search_toast4);
                                                        toast.setView(zzimtoast);
                                                        toast.show();
                                                        zzimtext.setText(String.valueOf(count));


                                                    }
                                                }
                                            });

                                            Log.d("찜카운트", "" + zzimcountint.getZzimcount());
                                            //TODO 서버에서 goods(goods_code)별 count, state(눌림,안눌림) 처리가 완료되면 paser 수정, TextView에 적용 필요, selector 또한.
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Toast.makeText(SearchDetail.this, "일시적 네트워크 에러로 찜하기 실패", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } finally

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


                case R.id.detail_shopping_button: //쇼핑리스트 클릭한 경우우

                    shopping.post(new Runnable() {
                        @Override
                        public void run() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    OkHttpClient toServer;
                                    toServer = OkHttpInitManager.getOkHttpClient();
                                    Response response = null; // 응답


                                    toServer = OkHttpInitManager.getOkHttpClient();

                                    RequestBody postBody = new FormBody.Builder()
                                            .add("user", "" + userAccount)
                                            .add("goods", itemidValue)
                                            .build();

                                    final Request request = new Request.Builder()
                                            .url(String.format(NetworkDefineConstant.SEARCH_LIST_DETAIL_SHOPPING))
                                            .post(postBody)
                                            .build();


                                    //동기 방식
                                    try

                                    {
                                        response = toServer.newCall(request).execute();


                                        if (response.isSuccessful()) { //연결에 성공하면

                                            String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.


                                            JSONObject jsonObject = new JSONObject(returedMessage);
                                            shopType = jsonObject.optString("type"); //type결과 가져오기
                                            result = jsonObject.optString("insertResult");
                                            shopCount = jsonObject.optInt("shopCount");

                                            Log.e("shopbutton", returedMessage);


//                                            shoppingcountint = SearchDetailshoppingJSONParser.getSearchDetailshoppingJsonParser(returedMessage);
//                                            Log.d("담기", "insertID 값 테스트. 들어갔는지??" + shoppingcountint.getShoppingcount());

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {


                                                    if (shopType.equals("failed")) {
                                                        //이미 담겼을 때
                                                        Toast toast = new Toast(SadajoContext.getContext());
                                                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                        toast.setDuration(Toast.LENGTH_SHORT);
                                                        ImageView shoppingtoast = new ImageView(SadajoContext.getContext());
                                                        shoppingtoast.setImageResource(R.drawable.search_toast2);
                                                        toast.setView(shoppingtoast);
                                                        toast.show();
                                                    } else if (result.equals("failed")) {
                                                        //여행리스트가없을때
                                                        Toast toast = new Toast(SadajoContext.getContext());
                                                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                        toast.setDuration(Toast.LENGTH_SHORT);
                                                        ImageView shoppingtoast = new ImageView(SadajoContext.getContext());
                                                        shoppingtoast.setImageResource(R.drawable.search_toast5);
                                                        toast.setView(shoppingtoast);
                                                        toast.show();
                                                    } else if(shopType.equals("ok")){
                                                        //쇼핑리스트담기성공
                                                        Toast toast1 = new Toast(SadajoContext.getContext());
                                                        toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                        toast1.setDuration(Toast.LENGTH_SHORT);
                                                        ImageView shoppingtoast = new ImageView(SadajoContext.getContext());
                                                        shoppingtoast.setImageResource(R.drawable.search_toast3);
                                                        toast1.setView(shoppingtoast);
                                                        toast1.show();

                                                        shoptext.setText(String.valueOf(shopCount));

                                                    }

//                                                    if (!shoppingcountint.result.equals("failed")) {
//                                                        shoptext.setText(String.valueOf(shoppingcountint.getShoppingcount()));
//
//                                                        Toast toast = new Toast(SadajoContext.getContext());
//                                                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                                                        toast.setDuration(Toast.LENGTH_SHORT);
//                                                        ImageView shoppingtoast = new ImageView(SadajoContext.getContext());
//                                                        shoppingtoast.setImageResource(R.drawable.search_toast3);
//                                                        toast.setView(shoppingtoast);
//                                                        toast.show();
//
//
//                                                        // Toast.makeText(SearchDetail.this, "쇼핑리스트에 담겼습니다", Toast.LENGTH_SHORT).show();
//                                                    } else if (shoppingcountint.result.equals("failed")) {
//                                                        Toast.makeText(SearchDetail.this, "여행 일정을 먼저 등록해주세요.", Toast.LENGTH_SHORT).show();
//                                                    }
                                                }
                                            });

                                            //TODO 서버에서 goods(goods_code)별 count, state(눌림,안눌림) 처리가 완료되면 paser 수정, TextView에 적용 필요, selector 또한.
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Toast.makeText(SearchDetail.this, "일시적 네트워크 에러로 찜하기 실패", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } finally

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
            }
            ;
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
        itemcount.setText(String.valueOf(position + 1) + " / " + img_count);
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
            Glide.with(getContext())
                    .load(imageList.get(position))
                    .centerCrop()

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


    Button.OnClickListener buttonClickListener = new View.OnClickListener() { // 해시태그 tag Button ClickListener
        @Override
        public void onClick(View view) {
            Intent intent;
            Context context = SearchDetail.this;
            String str;
            str = searchDetailDB.getHashtag().get(view.getId()).toString();
            intent = new Intent(context, SearchListActivity.class);
            intent.putExtra("tag", str); //tag String 넘겨줌
            Log.e("tag", str);
            startActivity(intent);
            finish();

        }
    };


    public Button createTagButton(String str, int i) {
        Button button = new Button(getContext());
        button.setText(str); //서버로부터 받아온 tag text set
        button.setBackgroundResource(R.drawable.tag_button_file); //tag ninepatch background적용

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = 85;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        button.setPadding(15, 0, 15, 0); // left,right padding : 3
        params.setMargins(0, 7, 15, 7); // top, right margin : 15

        button.setGravity(Gravity.CENTER); //gravity : center
        button.setTextSize(13);// textsize : 13sp
        button.setTextColor(getResources().getColor(R.color.blackFont));
        button.setTypeface(null, Typeface.NORMAL);//textstyle : Nanum M
        button.setLayoutParams(params);
        button.setTag("HomeTag");
        button.setId(i);
        button.setOnClickListener(buttonClickListener);
        return button;
//        list_item_hash_button.addView(button); // button added


    }
}
