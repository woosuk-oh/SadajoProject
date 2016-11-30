package com.sdf.example.woosuk.lbstest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
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

http://www.androidwarriors.com/2015/10/fused-location-provider-in-android.html

TODO 퍼미션은 잘 동작하는것 같은데 google play랑 연결하는부분, 위경도 받아오는 부분, 위경도 연결해서 setText 하는부분 코드 위치 재확인 필요.

1. GoogleApiClient 빌드함
2. GoogleApiClient 연결함
3. 연결되면 wifi와 gps로 부터 정보 받아옴
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

        } else {
            // not connected to the internet

            Toast.makeText(this, "네트워크가 연결되지 않았습니다. 네트워크 연결 확인해주세요", Toast.LENGTH_SHORT).show();

            finish();
        }





    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect(); // 순서2. GoogleApiClient 연결

    }

    @Override
    protected void onResume() { // onStart 이후 실행되는곳. + 액티비티 다시 불러오면 실행되는곳
        super.onResume();


        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet

            if (isLocationEnabled(this)) {
                checkPermission(); // 순서4. 권한 확인 및 저장해둔 위경도 데이터 받기.
            } else {
                Toast.makeText(this, "설정에서 위치를 켜주세요.", Toast.LENGTH_SHORT).show();
                finish(); // 위치 꺼져있으면 destroy. (mGoogleApiClient는 disconnect.
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
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그
            } else {
                // 최초로 권한 요청
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그
            }
        } else {
            // 사용 권한이 있는경우

            getLocationData();
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

            if(mGoogleApiClient != null) {
                mGoogleApiClient.disconnect(); //mGoogleApiClient가 connect 되어있는 상태여야됨.
            }
        }

    }

    void updateUI() {
        txtOutputLat.setText(lat);
        txtOutputLon.setText(lon);

        Log.d("받은 위도", "" + lat);
        Log.d("받은 경도", "" + lon);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (isLocationEnabled(this)) {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //GPS, WIFI로 위치 받음.
            mLocationRequest.setInterval(10000);
        } else {
            Toast.makeText(this, "설정에서 위치를 켜주세요.", Toast.LENGTH_SHORT).show();
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




    @TargetApi(Build.VERSION_CODES.M)
    public void getLocationData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("권한 확인", "권한 없음.");

            Toast.makeText(this, "권한을 승인해야만 서비스 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
            finish();
           /* requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS); //안드로이드에서 제공하는 권한 요청 다이얼로그*//**//**/

        } else {

            // 저장한 위치 받아오기.
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
                lat = String.valueOf(mLastLocation.getLatitude());
                lon = String.valueOf(mLastLocation.getLongitude());

                updateUI();
            }

        }
    }
























     /*  오버레이 관련 퍼온것


     public void startOverlayWindowService(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(context)) {
            getView().onObtainingPermissionOverlayWindow();

        } else {
            getView().onStartOverlay();
        }
    }
    private void initWindowLayout(LayoutInflater layoutInflater) {
        windowView = layoutInflater.inflate(R.layout.window_view, null);
        windowViewLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                30, 30, // X, Y 좌표
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        windowViewLayoutParams.gravity = Gravity.TOP | Gravity.START;
        windowManager.addView(windowView, windowViewLayoutParams);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onObtainingPermissionOverlayWindow() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQ_CODE_OVERLAY_PERMISSION);
    }*/


}
