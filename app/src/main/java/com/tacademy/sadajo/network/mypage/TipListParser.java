package com.tacademy.sadajo.network.mypage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 12. 12..
 */

public class TipListParser {

    public static ArrayList<MypageTip> getTipListParsing(String responsedJSON) {

        ArrayList<MypageTip> listDBs = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(responsedJSON);


            int arraySize = jsonArray.length();
            for (int i = 0; i < arraySize; i++) {
                MypageTip mypageTip = new MypageTip();
                JSONObject list = jsonArray.getJSONObject(i);

                mypageTip.content = list.getString("content");
                mypageTip.userImg = list.getString("img");

                mypageTip.goodsCode = list.getString("goods_code");
                mypageTip.date = list.getString("regdate");


                listDBs.add(mypageTip);
            }


        } catch (JSONException e)

        {
            Log.e("jsonParser", e.toString());
        }

        return listDBs;
    }
}