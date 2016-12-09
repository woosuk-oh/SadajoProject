package com.tacademy.sadajo.search.searchlist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
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
import android.widget.Toast;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.funtion.SearchBarDeleteButton;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.Search.SearchDB;
import com.tacademy.sadajo.network.Search.SearchJSONParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.MotionEvent.ACTION_DOWN;
import static com.tacademy.sadajo.network.NetworkDefineConstant.SEARCH_LIST;
import static com.tacademy.sadajo.network.NetworkDefineConstant.SEARCH_LIST_COUNTRY;

public class SearchListActivity extends BaseActivity {



    String url;
    String sortvalue = ""; //인기순 최신순을 onResume에서도 사용하기 위해 전역변수로 빼둠. 기본 초기화값으로 공백 세팅

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
    TextView popularity; // 인기순
    TextView latest; // 최신순


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


    OkHttpClient client = new OkHttpClient();

    // 커스텀 바 setting
    private int columnCount;
    //커스텀 바의 현재 단말기 화면의 높이에 적절히 세팅
    private int customBarHeight;

    int tagCount;
    int cnt = 0; // 검색창 용
    private LinearLayoutManager searchlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setBottomButtonClickListener();

        //인기순, 최신순 selector 관련. res/color/sesarch_list_selector도 참고 필요. activity_search.xml의 인기순, 최신순 부분 setTextColor 참고 필요.



     /*   //맨처음 기본 셋팅.
        popularity.setPressed(true);
        latest.setPressed(false);*/

        popularity = (TextView) findViewById(R.id.popularity);


        popularity.setOnTouchListener(new View.OnTouchListener() {



            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case ACTION_DOWN:
                        popularity.setPressed(true);
                        latest.setPressed(false);
                        sortvalue= "click=1";
                        new AsyncSearchRequest().execute(countrySpinner.getSelectedItem().toString(), searchBar.getText().toString(), sortvalue);
                        Log.d("조건문","인기순 눌림 : 1");

                        break;
                }
                return false;
            }
        });
        latest = (TextView) findViewById(R.id.latest);
        latest.setOnTouchListener(new View.OnTouchListener(){


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    // When the user clicks the TextView
                    case ACTION_DOWN:
                        latest.setPressed(true);
                        popularity.setPressed(false);
                        Log.d("조건문","최신순 눌림 : 2");
                        sortvalue= "date=1";

                        new AsyncSearchRequest().execute(countrySpinner.getSelectedItem().toString(), searchBar.getText().toString(), sortvalue);
                        break;


                }
                return false;
            }
        });
       /* ClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setClickable(true);
                view.setPressed(true);

                latest.setTextColor(getResources().getColorStateList(R.color.search_list_selector));
                new AsyncSearchRequest().execute(countrySpinner.getSelectedItem().toString(), searchBar.getText().toString(), "date=1");

            }
        });*/


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

                if(sortvalue.equals("")){
                    popularity.setPressed(true); // 초기값으로 '눌렸다' 세팅. (초기 정렬은 url이 goods이지만 인기순이며, 눌렸다라는 표시로 빨간색 처리를 위함)
                }
                new AsyncSearchRequest().execute(countrySpinner.getSelectedItem().toString(), searchBar.getText().toString(),sortvalue);
                if(sortvalue.equals("")){
                    popularity.setPressed(true); // 초기값으로 '눌렸다' 세팅. (초기 정렬은 url이 goods이지만 인기순이며, 눌렸다라는 표시로 빨간색 처리를 위함)
                }
                Log.d("조건문","스피너 눌림: 3 " + countrySpinner.getSelectedItem().toString());
                //Log.e("spinner:", "" + countrySpinner.getSelectedItem().toString());
                //Log.d("서치바", "" + searchBar.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Intent intent = getIntent();
        String hashtag ="";

        if(intent.hasExtra("tag")){ // tag라는 key값으로 받은 intent의 밸류가 있는지 체크.
            hashtag = intent.getExtras().getString("tag");

            if (!hashtag.equals("")) {
                searchBar.setText(hashtag);
            }
    Log.d("해시태그", "해시태그값" + hashtag);
}

        /* 초성 검색 부분 */
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.select_searchlist_country_item, getResources().getStringArray(R.array.country)); // 스피너 레이아웃 기본으로 제공.

        countrySpinner.setAdapter(spinnerAdapter);


       // searchBar.setHint("검색어를 입력 바람");

        searchBar.addTextChangedListener(new TextWatcher() {
                                             @Override
                                             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                             }

                                             @Override
                                             public void onTextChanged(CharSequence s, int start, int before, int count) {
              /*  if (cnt == 0) {
                    searchlayout.setStackFromEnd(false);
                }
*/
                                               //  if (!TextUtils.isEmpty(searchBar.getText().toString())) {
                                                    // Log.d("서치바", "서치바에 입력된 값이 있냐?" + !TextUtils.isEmpty(searchBar.getText().toString()));
                                                     new AsyncSearchRequest().execute(countrySpinner.getSelectedItem().toString(), searchBar.getText().toString(), sortvalue);
                                                     Log.d("조건문","서치바 값 입력됨 : 4"+searchBar.getText().toString());

                                                 if(sortvalue.equals("")){
                                                     popularity.setPressed(true); // 초기값으로 '눌렸다' 세팅. (초기 정렬은 url이 goods이지만 인기순이며, 눌렸다라는 표시로 빨간색 처리를 위함)
                                                 }
                                                      cnt++;
                                                 //}
                                             }

                                             @Override
                                             public void afterTextChanged(Editable editable) {
                                             }
                                         }
        );


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

    @Override
    public void onResume() {
        super.onResume();


        if(sortvalue.equals("")){
            popularity.setPressed(true); // 초기값으로 '눌렸다' 세팅. (초기 정렬은 url이 goods이지만 인기순이며, 눌렸다라는 표시로 빨간색 처리를 위함)
        }else if(sortvalue.equals("/click=1")){
            popularity.setPressed(true);
        }else if(sortvalue.equals("/date=1")){
            latest.setPressed(true);
        }
       // popularity.setTextColor(getResources().getColor(pink));

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                new AsyncSearchRequest().execute(countrySpinner.getSelectedItem().toString(), searchBar.getText().toString(), sortvalue);

                if(sortvalue.equals("")){
                    popularity.setPressed(true); // 초기값으로 '눌렸다' 세팅. (초기 정렬은 url이 goods이지만 인기순이며, 눌렸다라는 표시로 빨간색 처리를 위함)
                }
                Log.d("조건문","onResume 상태임. : 5");


                //Log.e("spinner:", "" + countrySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


/*       if(mAdapter.size() > 0){
            mAdapter.clearData();
            melonRecyclerViewAdapter.notifyDataSetChanged();
        }*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    public class AsyncSearchRequest extends AsyncTask<String, Void, SearchDB> {


        //첫번째 Void: doInBackgorund로 보내는
        //두번째 Void: Progress
        //세번째 onPostExecute에서 사용할 파라미터값.



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
            String param1 = params[0]; // 입력받은 스피너 값
            String param2 = params[1]; // 입력받은 editText 값 (serachbar)
            String param3 = params[2]; // 입력받은 인기순, 최신순


            Log.e("선택한 스피너", "" + params[0].toString());
            if (param1.equals("전세계")) { //스피너에서 선택한 값이 "전세계" 이면
                url = SEARCH_LIST; //url에 /goods를 넣어줌.
                Log.d("URL1","입력한 URL 주소:"+url);

      /*          if (!param3.equals("")) { // 전세계인 상태에서 인기순이나 최신순을 누른 경우.
                    url = url + "?"+param3;
                    Log.d("URL1-1","입력한 URL 주소:"+url);
                }

                // 에딧 텍스트에서 입력한 값 가져와서
                if (!TextUtils.isEmpty(param2)) { // EditText에 입력한 값이 넘어와서 그 값이 공백이 아닐경우.
                    Log.d("입력한 파람스", "" + param2);
                    url = url + "?name=" + param2;  //  url = url + "?name=" + "시세이도";
                    Log.d("URL1-2","입력한 URL 주소:"+url);

                }*/
                if(param2.equals("") && !param3.equals("")){
                    url = url + "?"+ param3; //검색창 안에 입력이 공백이고 인기,최신순 누른 경우.
                    Log.d("URL1-1","검색값 없고, 인기,최신순 누른경우.:"+url);
                }

                if(param2.equals("") && param3.equals("")){
                    //검색 값 없고, 인기,최신순 안누른 경우
                    url = SEARCH_LIST;
                    Log.d("URL1-4","검색값 없고, 인기,최신순 없는 경우.:"+url);
                }

                if(!param2.equals("") && !param3.equals("")){
                    url = url + "?name="+param2+"&"+param3+"&hashtag="+param2; //검색창 안에 입력 값있고 인기,최신순 누른 경우.
                    Log.d("URL1-5","검색창 안에 입력 값있고 인기,최신순 인 경우:"+url);
                }

                if(!param2.equals("") && param3.equals("")){
                    // 검색 값 있고 최신,인기순 안누른 경우
                    url = url + "?name="+param2+"&hashtag="+param2;
                    Log.d("URL1-6","검색 값 있고 최신,인기순 안누른 경우:"+url);
                }





            } else { // 스피너에서 선택한 값이 있다면
                countryId = param1;
                url = String.format(SEARCH_LIST_COUNTRY, countryId); //get 방식이므로 FormBody는 없고, 정보를 url에만 담음.
                Log.d("URL2","입력한 URL 주소:"+url);

                if (!param3.equals("")) { //인기순 혹은 최신순 누른경우
                    url = url + "&"+param3;
                    Log.d("URL2-1","스피너 전세계x + 인기순or최신순 + URL 주소:"+url);

                    if(param2.equals("")){ // 검색값 없는 경우
                        url = url;
                        Log.d("URL2-2"," URL 주소:"+url);
                    }
                    if(!param2.equals("")){ // 검색값 있는 경우
                        url = url + "&name="+param2+"&hashtag="+param2;
                        Log.d("URL2-3","URL 주소:"+url);
                    }
                }
                else if (param3.equals("")) { // 인기 최신순 안누른 경우
                    url = url;
                    Log.d("URL3","URL 주소:"+url);
                    if(param2.equals("")){ // 검색값 입력 없음.
                        url = url;
                        Log.d("URL3-1","URL 주소:"+url);
                    }
                    if(!param2.equals("")){ // 검색값 입력됨
                        url = url + "&name="+ param2+"&hashtag="+param2;
                        Log.d("URL3-2","URL 주소:"+url);
                    }
                }
            }

            try {
                //get 방식으로 받기
                //  String url = String.format(SEARCH_LIST, countryId);


                Request request = new Request.Builder()
                        .url(url)
                        .build();

                response = toServer.newCall(request).execute();

                if (response.isSuccessful()) { //연결에 성공하면
                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                  //  Log.e("searchActivity", returedMessage);

                    searchDB = SearchJSONParser.getSearchJsonParser(returedMessage); //만들어둔 파서로 returedMessage를 넣어서 파싱하여 homeDB에 값을 넣음.

                } else { // 연결에 실패하면
                    Log.e("요청/응답", response.message().toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(SadajoContext.getContext(), "서버와의 연결이 원활치 않음", Toast.LENGTH_SHORT).show();
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

