package com.tacademy.sadajo.search.searchlist;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class SearchListActivity extends AppCompatActivity {


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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;


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


        // 바텀 탭바

        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));
        searchBtn.setSelected(true);
        shoppingListBtn = (ImageButton) findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn = (ImageButton) findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn = (ImageButton) findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));
        searchBtn.setSelected(true);


        new AsyncSearchRequest().execute();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } // end OnCreate.

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
        homeBtn.setSelected(true);


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SearchList Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }


    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);

        public void onFail(Request request, IOException exception);
    }


    public class AsyncSearchRequest extends AsyncTask<Void, Void, SearchDB> {


        //첫번째 Void: doInBackgorund로 보내는
        //두번째 Void: Progress
        //세번째 onPostExecute에서 사용할 파라미터값.
        private static final String SEARCH_LIST = HOST_URL + "/goods/%s";
        OkHttpClient mClient;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected SearchDB doInBackground(Void... params) {
            OkHttpClient toServer;
            toServer = OkHttpInitManager.getOkHttpClient();
            Response response = null; // 응답
            searchDB = new SearchDB();


            OkHttpClient client = new OkHttpClient();

            try {
            /* get 방식으로 받기 */
                //  String url_test = "1"; // get방식에서 마지막에 넣는 id값. 테스트.
                /*String url = String.format(SEARCH_LIST, url_test); //TODO 변수명 수정.*/
                String url = String.format(SEARCH_LIST, "");
                //get 방식이므로 FormBody는 없고, 정보를 url에만 담음.


                Request request = new Request.Builder()
                        .url(url)
                        .build();

                response = toServer.newCall(request).execute();

                if (response.isSuccessful()) { //연결에 성공하면
                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    Log.e("searchActivity", returedMessage);

                    searchDB = SearchJSONParser.getSearchJsonParser(returedMessage); //만들어둔 파서로 returedMessage를 넣어서 파싱하여 homeDB에 값을 넣음.

                }else { // 연결에 실패하면
                    Log.e("요청/응답", response.message().toString());
                }
          /*      client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
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


    }

    private void hideCustomBar() {
        customBar.startAnimation(outAnim);
        customBar.setVisibility(View.GONE);
    }

    private void showCustomBar() {
        customBar.startAnimation(inAnim);
        customBar.setVisibility(View.VISIBLE);
    }


// 하단 탭바 클릭 시


}
