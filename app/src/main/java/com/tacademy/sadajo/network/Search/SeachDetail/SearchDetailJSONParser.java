package com.tacademy.sadajo.network.Search.SeachDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchDetailJSONParser {
    //홈 화면 JSON으로 파싱하는 곳
    public static SearchDetailDB getSearchDetailJsonParser(String responsedJSON) {


        SearchDetailDB searchDetailDB = new SearchDetailDB();
        ArrayList<TipsDB> tipsContainer = new ArrayList<>(); //배열에 있는 TipsDB 객체들 하나씩을 담기위한 Container


        try {


            JSONObject searchDetail = new JSONObject(responsedJSON);


            searchDetailDB.setItem_id(searchDetail.getInt("id"));

            searchDetailDB.setGoods_code(searchDetail.getString("goods_code"));
            searchDetailDB.setGoods_content(searchDetail.getString("goods_content"));
            searchDetailDB.setGoods_name(searchDetail.getString("goods_name"));
            searchDetailDB.setGoods_country(searchDetail.getString("country"));
            searchDetailDB.setClick(searchDetail.getInt("click"));
            searchDetailDB.setRegdate(searchDetail.getString("regdate"));


            /* 이미지 파싱 부분 */
            JSONArray searchDetailImg = searchDetail.getJSONArray("goods_img");
            int imgArraySize = searchDetailImg.length();

            for(int i=0; i<imgArraySize; i++ ) {

                searchDetailDB.goods_img.add(searchDetailImg.getString(i));
            }


            /* 해시태그 파싱 부분 */
            JSONArray hash = searchDetail.getJSONArray("hashtag");
            int hashArraySize = hash.length();

            for (int i = 0; i < hashArraySize; i++) {
                searchDetailDB.hashtag.add(hash.getString(i));
            }

            /* 어디서 구매할 수 있나요? 파싱 부분 */
            JSONArray place = searchDetail.getJSONArray("sell_place");
            int placeArraySize = place.length();

            for (int i = 0; i < placeArraySize; i++) {
                searchDetailDB.sell_place.add(place.getString(i));
            }

            /* 얼마에 구매할 수 있나요 파싱 부분 */
            JSONArray price = searchDetail.getJSONArray("tag_price");
            int priceArraySize = price.length();

            for (int i = 0; i < priceArraySize; i++) {
                searchDetailDB.price.add(price.getString(i));
            }

            /* 팁 파싱 부분 */
            JSONArray tipsArray = searchDetail.getJSONArray("tips"); //tips 제이슨 어레이 받아오고
            int tipsArraySize = tipsArray.length(); // 받아온 제이슨어레이의 크기만큼


            for (int i = 0; i < tipsArraySize; i++) { // Array 갯수만큼 뽑아내기 위해 반복문 돌려서 제이슨 오브젝트안의 객체들을 저장한다.
                TipsDB tipsDB = new TipsDB();
                JSONObject tips = tipsArray.getJSONObject(i);

                tipsDB.setWriter(tips.getString("writer"));
                tipsDB.setTips_content(tips.getString("content"));
                tipsDB.setTips_user_img(tips.getString("user_img"));
                tipsDB.setRegdate(tips.getString("regdate"));

                tipsContainer.add(tipsDB); // 생성한 ArrayList<tips>인 tipContainer에 tipsDB 객체(Writer, tips_content) 반복문을 통해 담는다.
            }
            searchDetailDB.setTips(tipsContainer);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchDetailDB;
    }
}