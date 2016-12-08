package com.tacademy.sadajo.network.mypage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by woosuk
 */


public class DealListJsonParser {

    public static ArrayList<DealListData> getDealListDataParsing(String responsedJSON) {

        ArrayList<DealListData> deallistdatas = new ArrayList<>();

        try {
            JSONArray listArray = new JSONArray(responsedJSON);
            int listSize = listArray.length();
            for (int i = 0; i < listSize; i++) {
                DealListData deallistdata = new DealListData();

            JSONObject deals = listArray.getJSONObject(i);

                deallistdata.country_name = deals.getString("country");
                deallistdata.country_img = deals.getString("img");
                deallistdata.carr_img = deals.getString("carr_img");
                deallistdata.thedate = deals.getString("thedate");
                deallistdata.goods_name = deals.getString("goods_name");
                deallistdata.req_code = deals.getInt("req_code");

                deallistdatas.add(deallistdata);
            }
        } catch (JSONException e) {
            Log.e("jsonParser", e.toString());
        }
        return deallistdatas;
    }
}
   