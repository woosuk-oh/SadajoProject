package com.tacademy.sadajo;

import android.app.Application;

/**
 * Created by woosuk on 2016-11-15.
 */

public class SadajoContext extends Application {
    private static android.content.Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;


    }

    public static android.content.Context getContext() {
        return mContext;
    }

}

