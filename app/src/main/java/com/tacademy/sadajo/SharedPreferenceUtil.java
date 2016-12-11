package com.tacademy.sadajo;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by EUNZY on 2016. 12. 7..
 */

public class SharedPreferenceUtil {

    private static SharedPreferenceUtil instance;

    public static SharedPreferenceUtil getInstance() {
        if (instance == null) {
            instance = new SharedPreferenceUtil(SadajoContext.getContext());
        }
        return instance;
    }

    private static Context mContext;
    private static SharedPreferences mPrefs;
    private static SharedPreferences.Editor mEditor;

    final static  String USER_TOKEN = "Token";


    /*
     서버로 넘길 ID 또는 토큰 값
     */
    private static final String FIELD_FACEBOOK_ID = "facebookId";
    private static final String FIELD_FACEBOOK_TOKEN_KEY = "facebookToken";

    public void setFacebookId(String id) {
        mEditor.putString(FIELD_FACEBOOK_ID, id);
        mEditor.commit();
    }

    public String getFaceBookId() {
        return mPrefs.getString(FIELD_FACEBOOK_ID, "");
    }

    public void setFacebookToken(String token) {
        mEditor.putString(FIELD_FACEBOOK_TOKEN_KEY, token);
    }

    public String getFieldFacebookTokenKey() {
        return mPrefs.getString(FIELD_FACEBOOK_TOKEN_KEY, "");
    }


        public SharedPreferenceUtil(Context context) {
            mContext = context;
            mPrefs = mContext.getSharedPreferences("userInfo", MODE_PRIVATE);
            mEditor = mPrefs.edit();

        }


    //토큰 저장하기
    public static void setAccessToken(int token) {

        mEditor.putInt(USER_TOKEN, token);
        mEditor.commit();
    }

    //토큰 가져오기
    public static int getAccessToken() {


        return mPrefs.getInt(USER_TOKEN, 0);

    }

    //토큰 삭제
    public static void removeAccessToken() {

        mEditor.remove(USER_TOKEN);
        mEditor.commit();


    }

    public static void setAccessToClient(int token) {

        mEditor.putInt("toclient", token);
        mEditor.commit();
    }

    //토큰 가져오기
    public static int getAccessToClient() {


        return mPrefs.getInt("toclient", 0);

    }

    //토큰 삭제
    public static void removeAccessToClient() {

        mEditor.remove("toclient");
        mEditor.commit();


    }


    private static final String FCM_TOKEN_KEY = "fcmTokenKey";
    private static final String UUID_KEY = "uuidKey";


    public String getFcmTokenKey() {
        return mPrefs.getString(FCM_TOKEN_KEY, "");
    }

    public void setUuidKey(String uuid) {
        mEditor.putString(UUID_KEY, uuid);
        mEditor.commit();
    }

    public String getUuidKey() {
        return mPrefs.getString(UUID_KEY, "");
    }

}
