package com.tacademy.sadajo.network.mypage;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by EUNZY on 2016. 12. 7..
 */

public class MypageJsonParser {

    public static MyPageData getMypageDataParsing(String responsedJSON) {

        MyPageData myPageData = new MyPageData();

        try {
            JSONObject myPage = new JSONObject(responsedJSON);


            myPageData.buyNum = myPage.getInt("req_num");
            myPageData.sellNum = myPage.getInt("carr_num");
            myPageData.targetUserCode = myPage.getInt("code");
            myPageData.targetUserName = myPage.getString("nick");
            myPageData.targetUserImg = myPage.getString("img");
            myPageData.targetUserLocation = myPage.getString("location");


        } catch (JSONException e) {
            Log.e("jsonParser", e.toString());
        }
        return myPageData;
    }
}
   