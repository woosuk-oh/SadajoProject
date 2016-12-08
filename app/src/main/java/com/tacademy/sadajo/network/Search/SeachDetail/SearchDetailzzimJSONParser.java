package com.tacademy.sadajo.network.Search.SeachDetail;

import org.json.JSONException;
import org.json.JSONObject;


public class SearchDetailzzimJSONParser {
    //홈 화면 JSON으로 파싱하는 곳
    public static SearchDetailzzimDB getSearchDetailzzimJsonParser(String responsedJSON) {




        SearchDetailzzimDB searchDetailzzimDB = new SearchDetailzzimDB();
        try {


            JSONObject searchDetailzzim = new JSONObject(responsedJSON);


            searchDetailzzimDB.setZzimcount(searchDetailzzim.optInt("likeCount"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchDetailzzimDB;
    }
}