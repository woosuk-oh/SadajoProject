package com.tacademy.sadajo.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.Home.HomeDB;
import com.tacademy.sadajo.network.Home.HomeJSONParser;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.search.searchlist.SearchListActivity;

import org.apmem.tools.layouts.FlowLayout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.ProgressDialog.show;
import static android.graphics.Typeface.NORMAL;


public class HomeActivity extends BaseActivity  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    int stateval = 0;

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;
    private int userAccount;

    Toolbar toolbar;
    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    // ImageView profileImageView;
    TextView countryNameTextView;
    TextView comeDateTextView;
    TextView departDateTextView;
    Button scheduleRegisterButton;

    LinearLayout register;


    ImageView cardView2CountryFlagImageView;
    ImageView cardView3CountryFlagImageView;

    TextView cardView2CountryTextView;
    TextView cardView3CountryTextView;


    FlowLayout flowLayout;
    RecyclerView recyclerView;

    HomeUserRecyclerViewAdapter homeUserRecyclerViewAdapter;
    HomeDB homeDB;
    ProgressDialog progressDialog1;


    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    //TextView txtOutputLat, txtOutputLon, myCountryName;
    Location mLastLocation;
    String lat, lon, mycountry, mycity;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        buildGoogleApiClient(); // 순서 1. GoogleApiClient 빌드.


        setContentView(R.layout.activity_home);

        setBottomButtonClickListener();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setBackgroundResource(R.drawable.tool_01_main); //toolbar image
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        setToolbar(false);


        countryNameTextView = (TextView) findViewById(R.id.countryNameTextView);//국가명

        departDateTextView = (TextView) findViewById(R.id.departDateTextView); //떠나요날짜

        comeDateTextView = (TextView) findViewById(R.id.comeDateTextView); //돌아와요날짜
        scheduleRegisterButton = (Button) findViewById(R.id.scheduleRegisterButton); //일정등록버튼
        register = (LinearLayout) findViewById(R.id.register);
        cardView2CountryTextView = (TextView) findViewById(R.id.cardView2CountryTextView);//두번째카드뷰 국가명
        cardView3CountryTextView = (TextView) findViewById(R.id.cardView3CountryTextView);//세번째카드뷰 국가명
        cardView2CountryFlagImageView = (ImageView) findViewById(R.id.cardView2CountryFlagImageView);//두번째 카드뷰 flag이미지
        cardView3CountryFlagImageView = (ImageView) findViewById(R.id.cardView3countryFlagImageView);//세번째 카드뷰 flag이미지

        flowLayout = (FlowLayout) findViewById(R.id.flowLayout); //taglist layout
        recyclerView = (RecyclerView) findViewById(R.id.anoter_shoplist_recyclerView); //쇼핑리스트 리사이클러뷰

        scheduleRegisterButton.setOnClickListener(onClickListener);
        register.setOnClickListener(onClickListener);


        //layout3
        recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 4));
        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(45, "bottom");//리사이클러뷰 아이템간 간격
        recyclerView.addItemDecoration(decoration);


        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(SadajoContext.getContext());
        sharedPreferenceUtil.setAccessToken(12);
        userAccount = sharedPreferenceUtil.getAccessToken();

        String fcmToken = FirebaseInstanceId.getInstance().getToken(); //푸시 토큰받아옴

        Log.e("Home Activity :", String.valueOf(userAccount));
//         Log.e("Home fcmToken :", fcmToken);
        // 페이스북 아이디 됐는지 확인
        Log.d("페북로그인", "가져온 페북아이디:" + sharedPreferenceUtil.getFaceBookId());


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new AsyncHomeRequest().execute();

    }

    public class AsyncHomeRequest extends AsyncTask<Void, Void, HomeDB> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = show(HomeActivity.this,
                    "서버입력중", "잠시만 기다려 주세요 ...", true);
        }

        @Override
        protected HomeDB doInBackground(Void... homeDBs) {
            Response response = null; //응답 담당
            OkHttpClient toServer;
            homeDB = new HomeDB();


            try {
                toServer = OkHttpInitManager.getOkHttpClient();


                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userAccount))
                        .build();


                Request request = new Request.Builder()
                        .url(String.format(NetworkDefineConstant.SERVER_URL_REQUEST_HOME))
                        .post(postBody)
                        .build();


                //동기 방식
                response = toServer.newCall(request).execute();


                if (response.isSuccessful()) { //연결에 성공하면

                    String returedMessage = response.body().string(); // okhttp로 부터 받아온 데이터 json을 스트링형태로 변환하여 returendMessage에 담아둠. 이때, home부분의 모든 오브젝트를 가져와 담아둠.
                    //  Log.e("wooseokLog", returedMessage);
                    homeDB = HomeJSONParser.getHomeJsonParser(returedMessage); //만들어둔 파서로 returedMessage를 넣어서 파싱하여 homeDB에 값을 넣음.

                } else { // 연결에 실패하면
                    Log.e("요청/응답", response.message().toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                networkfail.sendEmptyMessage(0);
                progressDialog.dismiss();
            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(SadajoContext.getContext(),
//                        "서버와의 통신 연결이 원활치 않습니다.", Toast.LENGTH_SHORT).show();
//                  progressDialog.dismiss();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            return homeDB;


        }

        @Override
        protected void onPostExecute(HomeDB s) {
            // progressDialog.dismiss();
            super.onPostExecute(s);
            progressDialog.dismiss();

//            if (homeDB != null) {


            if (s != null) { //서버로부터 msg를 받았으면.
                progressDialog.dismiss();
                Log.e("progerss", "progress");
                cardView2CountryTextView.setText(s.getTravelCountry()); // 추천리스트 : 해당 국가
                cardView3CountryTextView.setText(s.getTravelCountry()); // 추천리스트2(다른 쇼퍼맨 쇼핑리스트) : 해당 국가


                countryNameTextView.setText(s.travelInfos.titleCountry); // 국가명 받아옴.

                departDateTextView.setText(s.travelInfos.getStartDate()); // 떠나요

                comeDateTextView.setText(s.travelInfos.getEndDate()); //돌아와요



                // TODO 나중에 서버 되면 수정.
                homeUserRecyclerViewAdapter = new HomeUserRecyclerViewAdapter(HomeActivity.this, s.shoplist, mycountry, mycity);
/*
/........... 바꿔줘야됌. 이부분은 홈에서 다른 유저 눌렀을경우임.
                if(mycountry != null) {
                    //getLocationData();
                    homeUserRecyclerViewAdapter = new HomeUserRecyclerViewAdapter(HomeActivity.this, s.shoplist, mycountry, mycity);
                }
                else {
                    homeUserRecyclerViewAdapter = new HomeUserRecyclerViewAdapter(HomeActivity.this, s.shoplist, "korea가라", "seoul가라");
                }
                    recyclerView.setAdapter(homeUserRecyclerViewAdapter);*/



                String flagUrl = s.getCountryImg();
                Glide.with(SadajoContext.getContext())
                        .load(flagUrl)
                        .into(cardView2CountryFlagImageView);
                Glide.with(SadajoContext.getContext())
                        .load(flagUrl)
                        .into(cardView3CountryFlagImageView);


                //tag button 동적생성 & setText
                int tagCount = s.getTag().size();
                for (int i = 0; i < tagCount; i++) {

                    createTagButton(s.getTag().get(i), i);

                }


            }
        }
    }

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

    View.OnClickListener onClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View view) {
            ScheduleDialogFragment dialog = new ScheduleDialogFragment();
            switch (view.getId()) {
                case R.id.scheduleRegisterButton:

                    dialog.show(getFragmentManager(), "scheduleDialog");
                    break;
                case R.id.register:
                    dialog.show(getFragmentManager(), "scheduleDialog");
                    break;


            }
        }
    };


    Button.OnClickListener buttonClickListener = new View.OnClickListener() { //tag Button ClickListener
        @Override
        public void onClick(View view) {
            Intent intent;
            Context context = HomeActivity.this;
            String str;
            str = homeDB.getTag().get(view.getId()).toString();
            intent = new Intent(context, SearchListActivity.class);
            intent.putExtra("tag", str); //tag String 넘겨줌
            Log.e("tag", str);
            startActivity(intent);


        }
    };


    //Button 생성 메소드
    public void createTagButton(String str, int i) {
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = 69;

        Button button = new Button(this);
        button.setText(str); //서버로부터 받아온 tag text set
        button.setBackgroundResource(R.drawable.tag_button_file); //tag ninepatch background적용
        FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(width, height);
        button.setPadding(15, 0, 15, 0); // left,right padding : 3
        params.setMargins(0, 0, 45, 45); // bottom, right margin : 15
        button.setGravity(Gravity.CENTER); //gravity : center
        button.setTextSize(13);// textsize : 13sp
        button.setTypeface(button.getTypeface(), NORMAL);
        button.setLayoutParams(params);
        button.setTag("HomeTag");
        button.setId(i);
        button.setOnClickListener(buttonClickListener);
        flowLayout.addView(button); // button 추가
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        long intervalTime = currentTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = currentTime;
            Toast.makeText(getApplicationContext(),
                    "'뒤로' 버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }


    public void setToolbar(boolean b) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(b); //back icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //클릭시 뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    Handler networkfail = new Handler() {// 핸들러
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "네트워크 통신 장애",
                    Toast.LENGTH_LONG).show();

        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect(); // 순서2. GoogleApiClient 연결
        Log.d("onStart", "onStart에서 mGoogleApiClient를 연결요청함.");


    }

    @Override
    protected void onResume() { // onStart 이후 실행되는곳. + 액티비티 다시 불러오면 실행되는곳
        super.onResume();

/*        mGoogleApiClient.connect(); // 순서2. GoogleApiClient 연결
        Log.d("onResume", "onResume에서 mGoogleApiClient를 연결요청함.");*/

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet

            if (isLocationEnabled(this)) {
                Log.d("onResume", "onResume에서 위치 ON을 파악함.");


                if (mGoogleApiClient == null) {
                    Log.d("onResume", "onResume에서 mGoogleApiClient가 Null임을 확인하여 GoogleApiClient 빌드함..");


                    buildGoogleApiClient();
                    Log.d("onResume", "onResume에서 GoogleApiClient 빌드함..");

                    mGoogleApiClient.connect();
                    Log.d("onResume", "onResume에서 mGoogleApiClient 연결요청함.");
                }

                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d("홈액티비티mycountry","홈액티비티 mycountry: "+mycountry);
                        if(stateval == 0){

                           // stateval=1; // 처음시작할때만 여기서 실행.
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(SadajoContext.getContext())) { //마시멜로우 버전과 같거나 크고 다른앱위에 그리기 권한 없으면 실행됨.
                                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                                startActivity(myIntent);
                                Log.d("오버레이 권한", "checkPermission 오버레이 권한 검사. 없어서 설정보내기");
                                Toast.makeText(SadajoContext.getContext(), "다른앱 위에 그리기를 허용해주십시오.", Toast.LENGTH_SHORT).show();
                                // 마시멜로 M버전 이상+오버레이 안한경우이면 실행되는곳.



                              //  checkPermission(); // 순서4. 권한 확인 및 저장해둔 위경도 데이터 받기. 1.5초후 실행.

                                // 위치정보를 SharedPreference에 저장한다.
                                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(SadajoContext.getContext());
                                sharedPreferenceUtil.setLocaleArea(mycountry+","+mycity);
                                Log.d("홈액티비티mycountry","홈액티비티 mycountry: "+mycountry);

                            }else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                                // 마시멜로 M버전 미만.
                                getLocationData(); //권한 체크 없이 바로 위치 가져옴.
                                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(SadajoContext.getContext());
                                sharedPreferenceUtil.setLocaleArea(mycountry+","+mycity);
                                Log.d("홈액티비티mycountry","홈액티비티 mycountry: "+mycountry);
                            }
                        }
                    }


                }, 1500);



            } else {
                Toast.makeText(this, "설정에서 위치를 켜주세요.", Toast.LENGTH_SHORT).show();
                finish(); // 위치 꺼져있으면 destroy. (mGoogleApiClient는 disconnect.
                Log.d("onResume", "onResume에서 위치 꺼져있음을 파악하여 finish함.");
            }

        } else {
            // not connected to the internet

            Toast.makeText(this, "네트워크가 연결되지 않았습니다. 네트워크 연결 확인해주세요", Toast.LENGTH_SHORT).show();

            finish();
        }


    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {

  /*      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) { //마시멜로우 버전과 같거나 크고 다른앱위에 그리기 권한 없으면 실행됨.
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivity(myIntent);
            Log.d("오버레이 권한", "checkPermission 오버레이 권한 검사. 없어서 설정보내기");
            Toast.makeText(this, "다른앱 위에 그리기를 허용해주십시오.", Toast.LENGTH_SHORT).show();




        } else {*/

            Log.d("로케이션 퍼미션 갖고 있냐?", "" + checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) { // 권한이 없는경우

            /*requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그*/

                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    // 사용자가 임의로 권한 취소한 경우.
                    Toast.makeText(this, "위치 서비스를 사용하기 위해 권한을 설정해주십시오.", Toast.LENGTH_SHORT).show();
                    //권한 재요청
                    Log.d("checkPermission", "사용자가 취소한 권한 재요청");
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그
                } else {
                    // 최초로 권한 요청
                    Log.d("checkPermission", "최초 권한 요청");
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그
                }
            } else {
                // 사용 권한이 있는경우
                Log.d("checkPermission", "권한 확인됨");
                getLocationData();
            }
        }
  /*  }*/

    public static boolean isLocationEnabled(Context context) { // 위치 on/off 여부 확인
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) { // 요청한 권한 체크의 결과값
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                    Log.d("사용자가 해당권한을 허가", "");
                    getLocationData();


                } else {

                    Log.d("사용자가 해당권한을 거부", "");
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                   /* requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그*/

                    Toast.makeText(this, "권한 거부하였기에 종료.", Toast.LENGTH_SHORT).show();
                    finish();


                }
                return;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) { // connected to the internet

            if (mGoogleApiClient != null) {
                mGoogleApiClient.disconnect(); //mGoogleApiClient가 connect 되어있는 상태여야됨.'
                Log.d("onDestroy", "onDestroy에서 네트워크 OFF로 확인되어 mGoogleApiClient 연결해제하고 종료 ");
            }
        }
        Log.d("mGoogleApiClient 걍 꺼짐", "");

    }

    void updateUI() {
        //  txtOutputLat.setText(lat);
        //  txtOutputLon.setText(lon);


        Log.d("받은 위도", "" + lat);
        Log.d("받은 경도", "" + lon);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.d("onConnected", "mGoogleClient 가 연결되었음.");

        if (isLocationEnabled(this)) {
            Log.d("위치 켜져있음:", "");
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER); //GPS, WIFI로 위치 받음.

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 마시멜로 M버전 이상이면 실행되는곳.



                checkPermission(); // 순서4. 권한 확인 및 저장해둔 위경도 데이터 받기. 1.5초후 실행.

                // 위치정보를 SharedPreference에 저장한다.
                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(SadajoContext.getContext());
                sharedPreferenceUtil.setLocaleArea(mycountry+","+mycity);
                Log.d("홈액티비티mycountry","홈액티비티 mycountry: "+mycountry);

            }else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                // 마시멜로 M버전 미만.
                getLocationData(); //권한 체크 없이 바로 위치 가져옴.
                SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(SadajoContext.getContext());
                sharedPreferenceUtil.setLocaleArea(mycountry+","+mycity);
                Log.d("홈액티비티mycountry","홈액티비티 mycountry: "+mycountry);
            }



            // mLocationRequest.setInterval(5000); 위치정보 업데이트 '주기'

        } else {
            Toast.makeText(this, "설정에서 위치를 켜주세요.", Toast.LENGTH_SHORT).show();

            Log.d("onConnected:", "위치 꺼져있음을 확인함");
            finish();
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        buildGoogleApiClient();


    }

    @Override
    public void onLocationChanged(Location location) {

    }


    @TargetApi(Build.VERSION_CODES.M) //체크 셀프 퍼미션 사용에 따른 마시멜로우 대응
    public void getLocationData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("getLocationData", "권한 없음.");

            Toast.makeText(this, "권한을 승인해야만 서비스 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
            finish();
           /* requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그*//**//**/

        } else {


            Log.d("getLocationData", "getLocationData에서 마지막 권한 확인 완료.");


            // 저장한 위치 받아오기.
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Log.d("getLocationData", "getLocationData에서 위치 업데이트 요청함 (requestLocationUpdates).");

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            Log.d("getLocationData", "getLocationData에서 마지막 위치 받아옴 (mLastLocation).");


            if (mLastLocation != null) {

                Log.d("getLocationData", "getLocationData에서 마지막 위치 (mLastLocation) 정상적으로 받아와서 lat, lon에 저장함..");
                lat = String.valueOf(mLastLocation.getLatitude());
                lon = String.valueOf(mLastLocation.getLongitude());
                Log.d("getLocationData", "getLocationData에서 받아온 위치 데이터 정확도:" + mLastLocation.getAccuracy());



             /* 위치 주소 Address를 통해 반환 받아옴 */
                Geocoder geoCoder_local = new Geocoder(this, Locale.getDefault());
                Geocoder geoCoder_kr = new Geocoder(this, Locale.KOREAN);
                Geocoder geoCoder_en = new Geocoder(this, Locale.ENGLISH);


                try {

                    List<Address> addresses_local // 현재 위치에 맞게 자동으로 언어 로케일 맞춰줌.
                            = geoCoder_local.getFromLocation(mLastLocation.getLatitude(),
                            mLastLocation.getLongitude(), 10);

                    List<Address> addresses_local_kr // 한글. 안되면 위에서 Locale.KOREA로 수정해보기.
                            = geoCoder_kr.getFromLocation(mLastLocation.getLatitude(),
                            mLastLocation.getLongitude(), 10);

                    List<Address> addresses_local_en // 영문으로 반환 받음.
                            = geoCoder_en.getFromLocation(mLastLocation.getLatitude(),
                            mLastLocation.getLongitude(), 10);

                    mycountry = addresses_local_en.get(0).getCountryName();
                    mycity = addresses_local_en.get(0).getAdminArea();


                    Log.d("현재 국가명", "" + mycountry);
                    Log.d("현재 도시명", "" + mycity);

                    // 대문자로 변환
                    // myCountryName.setText(mycountry.toUpperCase());

             /*       if (mycountry.equals("South Korea")) {
                        myCountryName.setText("KOREA");
                    }else{
                        myCountryName.setText(mycountry);
                    }*/



                    Log.d("반환 받은 위치값", "반환 받은 위치값:" + addresses_local_en.get(0));
                    updateUI();



                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("getLocationData", "getLocationData에서 마지막 위치 정상적으로 받아오지 못함 혹은 mLastLocation에 꺼내오질 못함.. 위치가 GPS만 되어있는 경우 예외처리.");

                // 받아온 값이 없어서 디폴트로 KOREA 넣어줌.
                // myCountryName.setText("KOREA");

                mycountry = "Korea";
                mycity = "seoul";


              /*
                Log.d("getLocationData", "getLocationData에서 마지막 위치 정상적으로 받아오지 못함 혹은 mLastLocation에 꺼내오질 못함.. 때문에 다시한번 요청하고 저장.");

               //mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);

                lat = String.valueOf(mLastLocation.getLatitude());
                lon = String.valueOf(mLastLocation.getLongitude());*/


            }


        }
    }
}
