package com.tacademy.sadajo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by EUNZY on 2016. 12. 7..
 */

public class SharedPreferenceUtil
{

    private static SharedPreferenceUtil instance;

    public static SharedPreferenceUtil getInstance() {
        if (instance == null) {
            instance = new SharedPreferenceUtil();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    public SharedPreferenceUtil() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(SadajoContext.getContext());
        mEditor = mPrefs.edit();
    }
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





    public static void putSharedPreference
            (Context context, String key, int value)
    {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    public static int getSharedPreference
            (Context context, String key)
    {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getInt(key,0);
    }
}