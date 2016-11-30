/*
package com.sdf.example.woosuk.lbstest;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

*/
/**
 * Created by woosuk on 2016-11-29.
 *//*


public class GooglePlayConnect {
    public GoogleApiClient.Builder setGoogleServiceBuilder() {
//Google Api Client 생성

        GoogleApiClient.Builder mGoogleApiClientBuilder = new GoogleApiClient.Builder(this.MainActivity);

        mGoogleApiClientBuilder.addApi(LocationServices.API);//Fused Location Provider API 사용요청


//Google Client Connection Callback 클래스

        CallbackConnectedGoogleService callbackConnectedGoogleService = new CallbackConnectedGoogleService(this);

        mGoogleApiClientBuilder.addConnectionCallbacks(callbackConnectedGoogleService);

        mGoogleApiClientBuilder.addOnConnectionFailedListener(callbackConnectedGoogleService);


        GoogleApiClient mGoogleApiClient = mGoogleApiClientBuilder.build();

        mGoogleApiClient.connect();


        return mGoogleApiClientBuilder;

    }
}
*/
