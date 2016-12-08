package com.tacademy.sadajo;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by EUNZY on 2016. 12. 7..
 */

public class SharedPreferenceUtil {
    private static Context mContext;
    private static SharedPreferences mPrefs;
    private static SharedPreferences.Editor mEditor;

    private static final String USER_TOKEN = "Token";


    public SharedPreferenceUtil(Context context) {
        mContext = context;
        mPrefs = mContext.getSharedPreferences("userInfo", MODE_PRIVATE);
        mEditor = mPrefs.edit();

    }
//
//    //토큰 저장하기
//    public static void setAccessToken(String token) {
//
//        mEditor.putString(USER_TOKEN, token);
//        mEditor.commit();
//    }
//
//    //토큰 가져오기
//    public static String getAccessToken() {
//
//
//        return mPref.getString(USER_TOKEN, "");
//
//    }


    //토큰 저장하기
    public static void setAccessToken(int token) {

        mEditor.putInt(USER_TOKEN, token);
        mEditor.commit();
    }

    //토큰 가져오기
    public static int getAccessToken() {


        return mPrefs.getInt(USER_TOKEN,0);

    }

    //토큰 삭제
    public static void removeAccessToken() {

        mEditor.remove(USER_TOKEN);
        mEditor.commit();


    }

    private static final String FCM_TOKEN_KEY = "fcmTokenKey";
    private static final String UUID_KEY = "uuidKey";


    public  String getFcmTokenKey() {
        return mPrefs.getString(FCM_TOKEN_KEY,"");
    }

    public void setUuidKey(String uuid){
        mEditor.putString(UUID_KEY, uuid);
        mEditor.commit();
    }
    public String getUuidKey() {
        return mPrefs.getString(UUID_KEY, "");
    }

}