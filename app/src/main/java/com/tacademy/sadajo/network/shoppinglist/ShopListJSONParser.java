package com.tacademy.sadajo.network.shoppinglist;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 28..
 */

public class ShopListJSONParser {

    public static ArrayList<ShopListDB> getShopListParsing(String responsedJSON) {

        ArrayList<ShopListDB> shopListDBs = new ArrayList<>();

        try {
            JSONObject shopList = new JSONObject(responsedJSON);
            JSONArray lists = shopList.getJSONArray("list");



            int listSize = lists.length();
            for (int i = 0; i < listSize; i++) {


                ShopListDB shopListDB = new ShopListDB();
                JSONObject list = lists.getJSONObject(i);

                shopListDB.countryCode = list.getString("country_name_kor");
                shopListDB.cityCode = list.getString("city_name_kor");
                shopListDB.startDate = list.getString("start_date");
                shopListDB.endDate = list.getString("end_date");
                //image Parsing 추가해야함


                shopListDBs.add(shopListDB);
            }


        } catch (JSONException e)

        {
            Log.e("jsonParser", e.toString());
        }

        return shopListDBs;
    }
}
