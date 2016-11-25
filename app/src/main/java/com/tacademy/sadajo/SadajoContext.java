package com.tacademy.sadajo;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by woosuk on 2016-11-15.
 */

public class SadajoContext extends Application {
    private static android.content.Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;


        Typekit.getInstance()
                .addCustom1(Typekit.createFromAsset(this, "NanumSquareBold.ttf"))
                .addCustom2(Typekit.createFromAsset(this, "NanumSquareRegular.ttf"))
                .addCustom3(Typekit.createFromAsset(this, "Gotham-Black.otf"));
    }

    public static android.content.Context getContext() {
        return mContext;
    }

}

