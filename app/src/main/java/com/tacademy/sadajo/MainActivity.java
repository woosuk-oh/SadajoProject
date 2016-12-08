package com.tacademy.sadajo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.tacademy.sadajo.home.HomeActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    ImageButton kakaoLoginImageButton;
    ImageButton faceBookLoginImageButton;
    /*
      Facebook API에서  onActiviteResult 콜백을 담당 및 관리하는 클래스
    */
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //반드시 초기화 해야한다.
        FacebookSdk.sdkInitialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
}
