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
        ArrayList<ShopermanDB> shoperContainer = new ArrayList<>();

        try {


            JSONObject searchDetail = new JSONObject(responsedJSON);


            searchDetailDB.setItem_id(searchDetail.optInt("id"));
            searchDetailDB.setUnit(searchDetail.optString("unit"));

            searchDetailDB.setGoods_code(searchDetail.optString("goods_code"));
            searchDetailDB.setGoods_content(searchDetail.optString("content"));
            searchDetailDB.setGoods_name(searchDetail.optString("goods_name"));
            searchDetailDB.setGoods_country(searchDetail.optString("country"));
            searchDetailDB.setClick(searchDetail.optInt("click"));
            searchDetailDB.setRegdate(searchDetail.optString("regdate"));


            /* 이미지 파싱 부분 */
            JSONArray searchDetailImg = searchDetail.getJSONArray("goods_img");
            int imgArraySize = searchDetailImg.length();
         //   ArrayList<String> goodsimgs = new ArrayList<>();

            for(int i=0; i<imgArraySize; i++ ) {

                searchDetailDB.goods_img.add(searchDetailImg.getString(i));
            }
        //    searchDetailDB.setGoods_img(goodsimgs);



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
            JSONArray tagprice = searchDetail.getJSONArray("tag_price");
            int priceArraySize = tagprice.length();

            for (int i = 0; i < priceArraySize; i++) {
                searchDetailDB.tag_price.add(tagprice.getString(i));
            }


            /* 얼마에 구매할 수 있나요 파싱 부분2 */
            JSONArray price = searchDetail.getJSONArray("price");
            int price2ArraySize = price.length();

            for(int i =0; i< price2ArraySize; i++){
                searchDetailDB.price.add(price.getString(i));
            }

            /* TODO 쇼퍼맨에게 부탁해볼까요? 만들어줘야됌. */
            JSONArray shopermanArray = searchDetail.getJSONArray("shoperman");
            int shopermanArraySize = shopermanArray.length();
            for (int i=0; i< shopermanArraySize; i++){
                ShopermanDB shoperDB = new ShopermanDB();
                JSONObject shoper = shopermanArray.getJSONObject(i);

                shoperDB.setUser_id(shoper.getString("user_id"));
                shoperDB.setUser_img(shoper.getString("user_img"));
                shoperDB.setUser_name(shoper.getString("user_name"));

                shoperContainer.add(shoperDB);
            }
            searchDetailDB.setShoperman(shoperContainer);
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