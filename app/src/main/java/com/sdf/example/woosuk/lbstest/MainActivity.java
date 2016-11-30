package com.sdf.example.woosuk.lbstest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;


/*

참고 사이트:

외국사이트 fusedLoaction 관련(중요,쉬움): http://www.androidwarriors.com/2015/10/fused-location-provider-in-android.html
마시멜로우 대응 오버레이 관련: http://thdev.tech/androiddev/2016/05/08/Android-Overlay-Permission.html

** 이슈 대응 **
1. 마시멜로우 위치 셀프 퍼미션 대응
2. 마시멜로우 화면 오버레이 설정 이동 대응
3. 네트워크 OFF 상태 대응
4. 위치 OFF 상태 대응
5. onResume 대응
6. 액티비티 라이프사이클 중 "GoogleApiClient.connect" 사용에 따른 라이프 "onConnected" 추가 대응
7. 6번에 따른 Destory 대응 (diconnect 대응)

** FusedLoacation 프로세스 **
1. GoogleApiClient 빌드함
2. GoogleApiClient 연결함
3. 연결되면 퍼미션 체크 후 wifi와 gps (PRIORITY_HIGH_ACCURACY)로 부터 정보 받아옴
4.


*/


public class MainActivity extends AppCompatActivity implements ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    TextView txtOutputLat, txtOutputLon;
    Location mLastLocation;
    String lat, lon;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtOutputLat = (TextView) findViewById(R.id.textView);
        txtOutputLon = (TextView) findViewById(R.id.textView2);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet

            buildGoogleApiClient(); // 순서 1. GoogleApiClient 빌드.
            Log.d("onCreate", "onCreate에서 네트워크 ON 확인하여 GoogleAPI빌드 진행.");

        } else {
            // not connected to the internet

            Toast.makeText(this, "네트워크가 연결되지 않았습니다. 네트워크 연결 확인해주세요", Toast.LENGTH_SHORT).show();
            Log.d("onCreate", "onCreate에서 네트워크 OFF 확인되어 finish함.");
            finish();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect(); // 순서2. GoogleApiClient 연결
        Log.d("onStart", "onStart에서 mGoogleApiClient를 연결요청함.");

    }

    @Override
    protected void onResume() { // onStart 이후 실행되는곳. + 액티비티 다시 불러오면 실행되는곳
        super.onResume();


        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet

            if (isLocationEnabled(this)) {
                Log.d("onResume", "onResume에서 위치 ON을 파악함.");


                if(mGoogleApiClient == null) {
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
                },1500);



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
        txtOutputLat.setText(lat);
        txtOutputLon.setText(lon);

        Log.d("받은 위도", "" + lat);
        Log.d("받은 경도", "" + lon);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.d("onConnected", "mGoogleClient 가 연결되었음.");

        if (isLocationEnabled(this)) {
            Log.d("위치 켜져있음:", "");
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //GPS, WIFI로 위치 받음.


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
                Log.d("getLocationData", "getLocationData에서 받아온 위치 데이터 정확도:"+mLastLocation.getAccuracy());

                updateUI();
            }else{
                Log.d("getLocationData", "getLocationData에서 마지막 위치 정상적으로 받아오지 못함 혹은 mLastLocation에 꺼내오질 못함.. 때문에 다시한번 요청하고 저장.");
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);

                lat = String.valueOf(mLastLocation.getLatitude());
                lon = String.valueOf(mLastLocation.getLongitude());

                updateUI();
            }

        }
    }
}
