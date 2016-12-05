package com.tacademy.sadajo.search.searchlist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.funtion.SearchBarDeleteButton;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.Search.SearchDB;
import com.tacademy.sadajo.network.Search.SearchJSONParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.tacademy.sadajo.network.NetworkDefineConstant.HOST_URL;

public class SearchListActivity extends BaseActivity {


    // private CollapsingSwipeRefreshLayout swiper;
    private RecyclerView mRecycler;


    RecyclerView.LayoutManager layoutManager;

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;
    ArrayList<SearchDB> searchDBs;
    SearchDB searchDB;
  /*  SearchGoodsDB searchGoodsDBs;*/

    Spinner countrySpinner;


    private SearchListRecyclerAdapter mAdapter;

    //param 1번째는  이미지의 resource 값

    private Animation inAnim;
    private Animation outAnim;

    private TextView customTitleText1;
    private TextView customTitleText2;
    private TextView customTitleText3;
    private TextView customTitleText4;
    private TextView customTitleText5;


    private EditText searchBar;
    private Button searchBarClear;

    private LinearLayout customBar;
    //  SearchDB searchDB;

    int tagCount; //해시버튼 카운트용

    OkHttpClient client = new OkHttpClient();

    // 커스텀 바 setting
    private int columnCount;
    //커스텀 바의 현재 단말기 화면의 높이에 적절히 세팅
    private int customBarHeight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setBottomButtonClickListener();


        // 리싸이클러뷰 xml 붙이기.
        mRecycler = (RecyclerView) findViewById(R.id.search_recycler_view);


        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(layoutManager); //리싸이클러뷰의 레이아웃매니저 적용


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

        customBar = (LinearLayout) findViewById(R.id.card_back);


        // ActionBar의 추천사이즈로 커스텀을 설정함.(리사이클뷰의 아이템 데코레이션 값)
        TypedValue typeValue = new TypedValue();
        if (getTheme().resolveAttribute(R.attr.actionBarSize, typeValue, true)) {
            //현재 단말기 해상도에 적절한 크기를 자동으로 세팅
            customBarHeight = TypedValue.complexToDimensionPixelSize(typeValue.data,
                    getResources().getDisplayMetrics());
        }

        customTitleText1 = (TextView) findViewById(R.id.chong);
        customTitleText2 = (TextView) findViewById(R.id.item_count_textview); // 갯수
        customTitleText3 = (TextView) findViewById(R.id.gae);
        customTitleText4 = (TextView) findViewById(R.id.popularity); // 인기순
        customTitleText5 = (TextView) findViewById(R.id.latest); //최신순

        countrySpinner = (Spinner) findViewById(R.id.search_toolbar_spinner1); //국가별 선택 스피너
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                new AsyncSearchRequest().execute(countrySpinner.getSelectedItem().toString());
                Log.e("spinner:", "" + countrySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

/*
        ArrayList<String> spinnerArray;
        spinnerArray = new ArrayList<String>();
        spinnerArray.add("전세계");
        spinnerArray.add("일본");
        spinnerArray.add("미국");
        spinnerArray.add("data3");
*/

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.country)); // 스피너 레이아웃 기본으로 제공.

        countrySpinner.setAdapter(spinnerAdapter);


    } // end OnCreate.

    //bottom Tabbar
    private void setBottomButtonClickListener() {
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));
        shoppingListBtn = (ImageButton) findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn = (ImageButton) findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn = (ImageButton) findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));
        searchBtn.setSelected(true);


    }


    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);

        public void onFail(Request request, IOException exception);
    }


    public class AsyncSearchRequest extends AsyncTask<String, Void, SearchDB> {


        //첫번째 Void: doInBackgorund로 보내는
        //두번째 Void: Progress
        //세번째 onPostExecute에서 사용할 파라미터값.
        private static final String SEARCH_LIST = HOST_URL + "/goods";
        private static final String SEARCH_LIST2 = HOST_URL + "/good?country=%s";
        String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected SearchDB doInBackground(String... params) {
            OkHttpClient toServer;
            toServer = OkHttpInitManager.getOkHttpClient();
            Response response = null; // 응답

            searchDB = new SearchDB();
            String countryId;

            Log.e("선택한 스피너", "" + params[0].toString());


            if (params[0].equals("전세계")) { //스피너에서 선택한 값이 "전세계" 이면
                url = SEARCH_LIST; //url에 /goods를 넣어줌.


            } else { // 스피너에서 선택한 값이
                countryId = params[0].toString();
                url = String.format(SEARCH_LIST2, countryId); //get 방식이므로 FormBody는 없고, 정보를 url에만 담음.

            }

            try {
            /* get 방식으로 받기 */
                //String url = String.format(SEARCH_LIST, countryId);


                Request request = new Request.Builder()
                        .url(url)
                        .build();
               // Log.e("url3", "" + url);
                response = toServer.newCall(request).execute();

                if (response.isSuccessful()) { //연결에 성공하면
                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                 //   Log.e("searchActivity", returedMessage);

                    searchDB = SearchJSONParser.getSearchJsonParser(returedMessage); //만들어둔 파서로 returedMessage를 넣어서 파싱하여 homeDB에 값을 넣음.

                } else { // 연결에 실패하면
                  //  Log.e("요청/응답", response.message().toString());
                }

          /*      client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });*/
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            return searchDB;


        }

        @Override
        protected void onPostExecute(SearchDB searchDB) {
            super.onPostExecute(searchDB);

            customTitleText2.setText(String.valueOf(searchDB.getCount()));

            // Log.d("searchDB",""+searchDB.getSearchGoodsDBs().get(0).toString());

            mAdapter = new SearchListRecyclerAdapter(SearchListActivity.this, searchDB.searchGoodsDBs);
            mRecycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();


        }

        private void hideCustomBar() {
            customBar.startAnimation(outAnim);
            customBar.setVisibility(View.GONE);
        }

        private void showCustomBar() {
            customBar.startAnimation(inAnim);
            customBar.setVisibility(View.VISIBLE);
        }
/*

        //해시태그 버튼 클릭시
        Button.OnClickListener buttonClickListener = new View.OnClickListener() { //tag Button ClickListener
            @Override
            public void onClick(View view) {
                Intent intent;
                Context context = SearchListActivity.this;
                String str;
                str = searchDB.getTag().get(view.getId()).toString();
                intent = new Intent(context, SearchDetail.class);
                intent.putExtra("tag", str); //tag String 넘겨줌
                Log.d("tag", str);
                startActivity(intent);


            }
        };*/

// 하단 탭바 클릭 시
    }

}
