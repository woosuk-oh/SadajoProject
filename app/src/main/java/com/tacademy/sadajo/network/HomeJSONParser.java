//package com.tacademy.sadajo.network;
//
//import android.util.Log;
//
//import com.google.gson.JsonArray;
//import com.tacademy.sadajo.network.Home.HomeDB;
//import com.tacademy.sadajo.network.Home.HomeShoplistDB;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
///**
// * Created by pyoinsoo on 2016-11-21.
// */
//
//public class HomeJSONParser {
//    //홈 화면 JSON으로 파싱하는 곳
//    public static HomeDB getHomeJsonParser(String responsedJSON) {
//
//
//        HomeDB homeDBs = new HomeDB();
//
//
//        try {
//
//            JSONObject home = new JSONObject(responsedJSON); //home을 josnobject로 쓰겠다 초기화.
//
//
//            /* travel 파싱 부분 */
//            JSONObject travel = home.getJSONObject("travel"); //home에 travel이라는 jsonobject 가져와서 넣어줌
//            homeDBs.setTravelInfos() = tra;
//
//
//
//            //JSONArray shoplists = home.getJSONArray("shoplist");  //shoplist라는 jsonArray를 가져와서 JSONArray 형태의 shoplists에 넣어준다.
//
//
//            /* shoplist 파싱 부분 */
//            JSONArray shoplists = home.getJSONArray("shoplist"); //shoplist의 [] 부분을 가져온다. (JSONArray)
//            for (int i = 0; i < shoplists.length(); i++) {// 서버로 부터 받은 shoplist의 갯수 만큼 돌려줌
//                HomeShoplistDB homeShoplistDB = new HomeShoplistDB();
//                JSONObject shoplistsObject = shoplists.getJSONObject(i); //shoplist에서 {} 부분들을 가져온다. (JSONObject)
//
//              /* shoplist 안의 JSON 오브젝트들을 하나씩 파싱함. */
//                homeShoplistDB.setUserName(shoplistsObject.getString("nickname"));
//                homeShoplistDB.setUserId(shoplistsObject.getInt("user_code"));
//                homeShoplistDB.setUserImg(shoplistsObject.getString("user_img"));
//
//                homeDBs.shoplist.add(homeShoplistDB); // 위 3가지 받아온걸 클라이언트 쪽의 ArrayList(homeDB.shoplist)에 add해준다.
//            }
//
//
//            JSONArray tagJsonArray = home.getJSONArray("tag");
//            for (int i = 0; i < tagJsonArray.length(); i++) {
//                homeDBs.tag.add(tagJsonArray.getString(i));
//            }
//            //TODO 게터 세터 만들어줘야함.
//
//
//            HomeShoplistDB shoplistInfo = new HomeShoplistDB();
//
//
//            SONObject shop = shoplists.getJSONObject(i);
//
//            shoplistInfo.userName = shop.getString("nickname");
//            shoplistInfo.userId = shop.getInt("user_code");
//            shoplistInfo.userImg = shop.getString("user_img");
//
//            shoplistInfos.add(shoplistInfo);
//
//
//        } catch (JSONException e) {
//            Log.e("jsonParser", e.toString());
//        }
//        return shoplistInfos;
//    }
//}
