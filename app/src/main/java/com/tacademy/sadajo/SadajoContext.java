package com.tacademy.sadajo;

import android.app.Application;
import android.content.Context;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tsengvn.typekit.Typekit;

import java.net.URISyntaxException;

/**
 * Created by woosuk on 2016-11-15.
 */

public class SadajoContext extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(mContext, "fonts/NanumSquareRegular.ttf"))//나눔M
                .addBold(Typekit.createFromAsset(mContext, "fonts/NanumSquareBold.ttf"))//나눔B
                .addItalic(Typekit.createFromAsset(mContext, "fonts/Gotham-Black.otf"));//고담B




    }

    public static Context getContext() {
        return mContext;
    }
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(NetworkDefineConstant.HOST_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

}

