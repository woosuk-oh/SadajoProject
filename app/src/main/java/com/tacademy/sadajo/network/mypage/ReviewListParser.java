package com.tacademy.sadajo.network.mypage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 12. 12..
 */

public class ReviewListParser {

    public static ArrayList<MypageReview> getReviewListParsing(String responsedJSON) {

        ArrayList<MypageReview> listDBs = new ArrayList<>();

        try {
             JSONArray jsonArray = new JSONArray(responsedJSON);




            int arraySize = jsonArray.length();
            for (int i = 0; i < arraySize; i++) {
                MypageReview mypageReview = new MypageReview();
                JSONObject list = jsonArray.getJSONObject(i);

                mypageReview.content = list.getString("content");
                mypageReview.userImg = list.getString("img");
                mypageReview.userName = list.getString("nick");
                mypageReview.countryName = list.getString("country");
                mypageReview.productName = list.getString("goods_name");
                mypageReview.date = list.getString("regdate");
                mypageReview.reviewCode = list.getInt("req_code");




                listDBs.add(mypageReview);
            }


        } catch (JSONException e)

        {
            Log.e("jsonParser", e.toString());
        }

        return listDBs;
    }
}