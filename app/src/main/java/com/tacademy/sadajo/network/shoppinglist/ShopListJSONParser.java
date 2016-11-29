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

                shopListDB.countryNameKor = list.getString("country_name_kor");
                shopListDB.countryNameEng = list.getString("country_name_eng");
                shopListDB.cityName = list.getString("city_name_kor");
                shopListDB.startDate = list.getString("start_date");
                shopListDB.endDate = list.getString("end_date");
                shopListDB.goodsCount = list.getInt("goods_num");
                shopListDB.img = list.getString("img");
                shopListDB.listCode = list.getInt("list_code");


                shopListDBs.add(shopListDB);
            }


        } catch (JSONException e)

        {
            Log.e("jsonParser", e.toString());
        }

        return shopListDBs;
    }
}
