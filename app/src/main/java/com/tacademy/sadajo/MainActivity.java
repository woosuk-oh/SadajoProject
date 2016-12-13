package com.tacademy.sadajo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.tacademy.sadajo.home.HomeActivity;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

public class MainActivity extends BaseActivity implements View.OnClickListener, ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    ImageButton kakaoLoginImageButton;
    ImageButton faceBookLoginImageButton;
    /*
      Facebook API에서  onActiviteResult 콜백을 담당 및 관리하는 클래스
    */
    CallbackManager callbackManager;


    /*LBS*/
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    //TextView txtOutputLat, txtOutputLon, myCountryName;
    Location mLastLocation;
    String lat, lon, mycountry, mycity;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    /**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        buildGoogleApiClient();

        //반드시 초기화 해야한다.
        FacebookSdk.sdkInitialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*LBS*/
       // txtOutputLat = (TextView) findViewById(R.id.textView);
        //txtOutputLon = (TextView) findViewById(R.id.textView2);
        //myCountryName = (TextView) findViewById(R.id.textView3);
        /**/

        callbackManager = CallbackManager.Factory.create();

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1600);

        faceBookLoginImageButton = (ImageButton) findViewById(R.id.faceBookLoginImageButton);
        kakaoLoginImageButton = (ImageButton) findViewById(R.id.kakaoLoginImageButton);
        faceBookLoginImageButton.setOnClickListener(this);
        kakaoLoginImageButton.setOnClickListener(this);

        //등록된 LoginManager의 CallbackManager를 통해 Facebook 로그인으로 이동하며
        // 로그인이 성공/실패/취소에 해당하는 registerCallback 메소드가 실행된다.
        //이 버튼은 콜백을 통해 자동으로 페북의 OAuth Server로 연동된다.


        faceBookLoginImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인을 하고자 하는 페북사용자의 role을 선언한다.
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                        Arrays.asList("public_profile","email", "user_friends"));
            }
        });

        try {
            //FaceBook에 등록된 현재 애플리케이션의 서명을 통한 SHA 암호화 값(Key Hash)을 얻어온다.
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.tacademy.sadajo",  //Facebook 개발자에 등록된 내 애플리케이션의 Package Name
                    PackageManager.GET_SIGNATURES); //내 팩키지를 이용한 서명을 만드기 위함
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("FBLoginError", "NameNotFound", e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("FBLoginError", "NoSuchAlgorithm", e);
        }

        /*
          Facebook 로그인 대한 콜백이벤트 등록(반드시 Main Thread에서 선언해야 한다.)
         */
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            public void onSuccess(LoginResult loginResult) {
                //성공하면 토큰정보를 이용해 다양한 값을 설정또는 알아낼수 있다
                        /*final AccessToken token = AccessToken.getCurrentAccessToken();
                        token.getUserId();
                        token.getLastRefresh();
                        token.getApplicationId();
                        token.getExpires();*/

                //해당 토큰을 통해 발급받은 사용자 ID 또는 Token값을
                //가져온다.
                String userId = loginResult.getAccessToken().getUserId();
                Log.e("fbUserId", String.valueOf(userId));

                String accessToken = loginResult.getAccessToken().getToken();
                Log.e("accessToken", String.valueOf(accessToken));

                //Preference에 저장한다.
                SharedPreferenceUtil.getInstance().setFacebookId(userId);
                SharedPreferenceUtil.getInstance().setFacebookToken(accessToken);

                //MainActivity로 전달한다.
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("token", accessToken);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //페북의 Activity Callback을 담당하는 메소드
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.faceBookLoginImageButton:
                intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.kakaoLoginImageButton:
                intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
// Handler handler = new Handler();


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect(); // 순서2. GoogleApiClient 연결
        Log.d("onStart", "onStart에서 mGoogleApiClient를 연결요청함.");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                        checkPermission(); // 순서4. 권한 확인 및 저장해둔 위경도 데이터 받기. 1.5초후 실행.
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) { //마시멜로우 버전과 같거나 크고 다른앱위에 그리기 권한 없으면 실행됨.
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivity(myIntent);
            Log.d("오버레이 권한", "checkPermission 오버레이 권한 검사. 없어서 설정보내기");

        } else {

            Log.d("로케이션 퍼미션 갖고 있냐?", "" + checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION));
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) { // 권한이 없는경우

            /*requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그*/

                if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    // 사용자가 임의로 권한 취소한 경우.
                    Toast.makeText(this, "위치 서비스를 사용하기 위해 권한을 설정해주십시오.", Toast.LENGTH_SHORT).show();
                    //권한 재요청
                    Log.d("checkPermission", "사용자가 취소한 권한 재요청");
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그
                } else {
                    // 최초로 권한 요청
                    Log.d("checkPermission", "최초 권한 요청");
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그
                }
            } else {
                // 사용 권한이 있는경우
                Log.d("checkPermission", "권한 확인됨");
                getLocationData();
            }
        }
    }
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
      // txtOutputLat.setText(lat);
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
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
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

                /*    if (mycountry.equals("South Korea")) {
                        myCountryName.setText("KOREA");
                    }else{
                        myCountryName.setText(mycountry);
                    }
*/


                    Log.d("반환 받은 위치값", "반환 받은 위치값:" + addresses_local_en.get(0));
                    updateUI();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("getLocationData", "getLocationData에서 마지막 위치 정상적으로 받아오지 못함 혹은 mLastLocation에 꺼내오질 못함.. 위치가 GPS만 되어있는 경우 예외처리.");

                // 받아온 값이 없어서 디폴트로 KOREA 넣어줌.
           //     myCountryName.setText("KOREA");

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
