package com.tacademy.sadajo.network.Search.SeachDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchDetailTipsJSONParser {
    //홈 화면 JSON으로 파싱하는 곳
    public static TipsContainer getSearchDetailTipsJsonParser(String responsedJSON) {






       //TipsDB tipsDBs = new TipsDB();
        TipsContainer tipsContainer = new TipsContainer();
       ArrayList<TipsDB> tipsDBs = new ArrayList<>();


        try {
            JSONObject tips = new JSONObject(responsedJSON);

            /* 팁 파싱 부분 */
            JSONArray tipsArray = tips.getJSONArray("data"); //tips 제이슨 어레이 받아오고
            int tipsArraySize = tipsArray.length(); // 받아온 제이슨어레이의 크기만큼



            for (int i = 0; i < tipsArraySize; i++) { // Array 갯수만큼 뽑아내기 위해 반복문 돌려서 제이슨 오브젝트안의 객체들을 저장한다.
                TipsDB tipsDB = new TipsDB();
                JSONObject tip = tipsArray.getJSONObject(i);


                    tipsDB.setUser_code(tip.optInt("user_code"));
                    tipsDB.setTips_content(tip.optString("content"));
                    tipsDB.setTips_user_img(tip.optString("user_img"));
                    tipsDB.setRegdate(tip.optString("regdate"));



                tipsDBs.add(tipsDB);
            }
            tipsContainer.setTips(tipsDBs);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tipsContainer;
    }
}