package com.tacademy.sadajo.search.searchlist;

import android.os.AsyncTask;
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

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.funtion.SearchBarDeleteButton;
import com.tacademy.sadajo.network.Search.SearchDB;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
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
    SearchDB searchDB;


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


        ArrayList<ItemArrayList> items = new ArrayList<>();

        items.add(new ItemArrayList(R.drawable.image_1, "산타노벨라 향수"));
        items.add(new ItemArrayList(R.drawable.image_1, "샘플2"));
        items.add(new ItemArrayList(R.drawable.image_1, "샘플3"));
        items.add(new ItemArrayList(R.drawable.image_1, "샘플4"));
        items.add(new ItemArrayList(R.drawable.image_1, "샘플5"));

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


    } // end OnCreate.


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
        protected SearchDB doInBackground(Void... voids) {
            Response response = null; // 응답
            OkHttpClient toServer;
            searchDB = new SearchDB();
            OkHttpClient client = new OkHttpClient();

            /* get 방식으로 받기 */

      /*      toServer = OkHttpInitManager.getOkHttpClient();*/
            String url_test = "1"; // get방식에서 마지막에 넣는 id값. 테스트.
            String url = String.format(SEARCH_LIST, url_test); //TODO 변수명 수정.
            //get 방식이므로 FormBody는 없고, 정보를 url에만 담음.


                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });

                /*

                Response response = client.newCall(request).execute();
                return response.body().string();

            Response response = client.newCall(request).execute();*/

            return searchDB;


        }

        @Override
        protected void onPostExecute(SearchDB searchDB) {
            super.onPostExecute(searchDB);
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
