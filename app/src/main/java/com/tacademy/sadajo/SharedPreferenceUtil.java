package com.tacademy.sadajo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by EUNZY on 2016. 12. 7..
 */

public class SharedPreferenceUtil
{
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