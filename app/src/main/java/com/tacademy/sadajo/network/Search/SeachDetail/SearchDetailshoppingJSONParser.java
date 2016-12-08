package com.tacademy.sadajo.network.Search.SeachDetail;

import org.json.JSONException;
import org.json.JSONObject;


public class SearchDetailshoppingJSONParser {
    //홈 화면 JSON으로 파싱하는 곳
    public static SearchDetailshoppingDB getSearchDetailshoppingJsonParser(String responsedJSON) {




        SearchDetailshoppingDB searchDetailshoppingDB = new SearchDetailshoppingDB();
        try {


            JSONObject searchDetailshopping = new JSONObject(responsedJSON);


            searchDetailshoppingDB.setShoppingcount(searchDetailshopping.optInt("shopCount"));
            searchDetailshoppingDB.setResult(searchDetailshopping.optString("insertResult"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchDetailshoppingDB;
    }
}