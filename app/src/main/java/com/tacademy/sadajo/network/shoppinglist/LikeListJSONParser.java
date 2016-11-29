package com.tacademy.sadajo.network.shoppinglist;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 28..
 */

public class LikeListJSONParser {

    public static ArrayList<ShopListDB> getLikeListParsing(String responsedJSON) {

        ArrayList<ShopListDB> listDBs = new ArrayList<>();

        try {
            JSONObject shopList = new JSONObject(responsedJSON);
            JSONArray lists = shopList.getJSONArray("list");


            int listSize = lists.length();
            for (int i = 0; i < listSize; i++) {
                ShopListDB shopListDB = new ShopListDB();
                JSONObject list = lists.getJSONObject(i);

                shopListDB.countryNameKor = list.getString("country_name_kor");
                shopListDB.countryNameEng = list.getString("country_name_eng");
                shopListDB.goodsCount = list.getInt("goods_num");
                shopListDB.listCode = list.getInt("list_code");
                shopListDB.img = list.getString("img");


                listDBs.add(shopListDB);
            }


        } catch (JSONException e)

        {
            Log.e("jsonParser", e.toString());
        }

        return listDBs;
    }
}
