package com.tacademy.sadajo.network.mypage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by woosuk
 */


public class BuyListJsonParser {

    public static ArrayList<BuyListData> getBuyListDataParsing(String responsedJSON) {

        ArrayList<BuyListData> buylistdatas = new ArrayList<>();

        try {
            JSONArray listArray = new JSONArray(responsedJSON);
            int listSize = listArray.length();
            for (int i = 0; i < listSize; i++) {
                BuyListData buylistdata = new BuyListData();

            JSONObject buys = listArray.getJSONObject(i);

                buylistdata.country_name = buys.getString("country");
                buylistdata.country_img = buys.getString("img");
                buylistdata.carr_img = buys.getString("carr_img");
                buylistdata.nick = buys.getString("nick");
                buylistdata.thedate = buys.getString("thedate");
                buylistdata.goods_name = buys.getString("goods_name");
                buylistdata.req_code = buys.getInt("req_code");

                buylistdatas.add(buylistdata);
            }
        } catch (JSONException e) {
            Log.e("jsonParser", e.toString());
        }
        return buylistdatas;
    }
}
   