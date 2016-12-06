package com.tacademy.sadajo.network.Search;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pyoinsoo on 2016-11-21.
 */

public class SearchJSONParser {
    //홈 화면 JSON으로 파싱하는 곳
    public static SearchDB getSearchJsonParser(String responsedJSON) {


        SearchDB searchDBs = new SearchDB();
        //ArrayList<SearchDB> searchDBArrayList = new ArrayList<>();
        ArrayList<SearchGoodsDB> searchGoodsDBs = new ArrayList<>();


        //ArrayList<HomeShoplistDB> homeShopListDBs = new ArrayList<>();
        try {
        JSONObject search = new JSONObject(responsedJSON);


            searchDBs.setCount(search.getInt("count"));

            JSONArray datas = search.getJSONArray("data"); //data의 [] 부분을 가져온다. (JSONArray)

            for (int i = 0; i < datas.length(); i++) {// 서버로 부터 받은 data의 갯수 만큼 반복
                SearchGoodsDB searchGoodsDB = new SearchGoodsDB();
                JSONObject data = datas.getJSONObject(i); //datas를 data 단위로 쪼갬. (제이슨 오브젝트를 꺼내옴)

                searchGoodsDB.setItem_id(data.optInt("id"));
                searchGoodsDB.setGoods_code(data.optString("goods_code"));
                searchGoodsDB.setGoods_name(data.optString("goods_name"));
                searchGoodsDB.setCountry(data.optString("country"));
                searchGoodsDB.setCountry_img(data.optString("country_img"));
                searchGoodsDB.setClick(data.optInt("click"));
                searchGoodsDB.setRegdate(data.optString("regdate"));
                searchGoodsDB.setItem_img(data.optString("goods_img"));


                searchGoodsDBs.add(searchGoodsDB);
            }
            searchDBs.setSearchGoodsDBs(searchGoodsDBs);




/*        searchDBArrayList.add(searchDBs); //SearchDB에서 재귀호출한 searchDBs를 어레이형태로 새로만든 어레이리스트에 넣는다.
         searchDBs.setSearchDBs(searchDBArrayList); // searchDB에 어레이리스트형태로 데이터를 넣어준다*/


            Log.d("search list:",""+searchDBs.getCount());

        }
     catch (JSONException e) {
        Log.e("jsonParser", e.toString());
    }
        return searchDBs;
   }
}
