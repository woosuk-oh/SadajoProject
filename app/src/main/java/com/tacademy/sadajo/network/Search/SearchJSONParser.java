//package com.tacademy.sadajo.network.Search;
//
//import android.util.Log;
//
//import com.tacademy.sadajo.network.Home.HomeDB;
//import com.tacademy.sadajo.network.Home.HomeShoplistDB;
//import com.tacademy.sadajo.network.Home.HomeTravelDB;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//
///**
// * Created by pyoinsoo on 2016-11-21.
// */
//
//public class SearchJSONParser {
//    //홈 화면 JSON으로 파싱하는 곳
//    public static HomeDB getHomeJsonParser(String responsedJSON) {
//
//
//        HomeDB searchDBs = new HomeDB(); //TODO 이부분 수정부터: HomeDB가 아니라 searchDB 따로 만들어서.
///*        ArrayList<HomeShoplistDB> homeShopListDBs = new ArrayList<>();
//
//        try {
//
//            JSONObject home = new JSONObject(responsedJSON); //home을 josnobject로 쓰겠다 초기화.
//
//
//            homeDBs.setMsg(home.getString("msg")); //msg 파싱부분
//            homeDBs.setTravelCountry(home.getString("travel_country"));
//            homeDBs.setCountryImg(home.getString("country_img")); // country_img 파싱부분
//
//
//
//            *//* travel 파싱 부분 *//*
//            JSONObject travel = home.getJSONObject("travel"); //home에 travel이라는 jsonobject 가져와서 넣어줌
//
//            HomeTravelDB homeTravelDB = new HomeTravelDB();
//            homeTravelDB.setTitleCountry(travel.getString("title_country"));
//            homeTravelDB.setStartDate(travel.getString("start_date"));
//            homeTravelDB.setEndDate(travel.getString("end_date"));
//
//          //  homeDBs.travelInfos.add(homeTravelDB); // 파싱해서 저장한 homeTravelDB를 HomeDB에 있는 travelInfos에 add 시켜줌.
//            homeDBs.setTravelInfos(homeTravelDB);
//
//
//
//            *//* shoplist 파싱 부분 . 배열이라 반복문 돌려줌.*//*
//            JSONArray shoplists = home.getJSONArray("shoplist"); //shoplist의 [] 부분을 가져온다. (JSONArray)
//            for (int i = 0; i < shoplists.length(); i++) {// 서버로 부터 받은 shoplist의 갯수 만큼 반복
//                HomeShoplistDB homeShoplistDB = new HomeShoplistDB();
//                JSONObject shoplistsObject = shoplists.getJSONObject(i); //shoplist에서 {} 부분들을 가져온다. (JSONObject)
//
//              *//* shoplist 안의 JSON 오브젝트들을 하나씩 파싱함. *//*
//                homeShoplistDB.setUserName(shoplistsObject.getString("nickname"));
//                homeShoplistDB.setUserId(shoplistsObject.getInt("user_code"));
//                homeShoplistDB.setUserImg(shoplistsObject.getString("user_img"));
//
//
//                homeShopListDBs.add(homeShoplistDB);
//
//
//
//       //         Log.d("shoplist",""+homeShoplistDB.userName);
//
//      //         homeDBs.shoplist.add(homeShoplistDB); // 위 3가지 받아온걸 클라이언트 쪽의 ArrayList(homeDB.shoplist)에 add해준다.
//
//
//           *//*     for (int a=0; a<homeDBs.getShoplist().size(); a++) {
//                    Log.d("shoplist", "" + homeDBs.getShoplist().get(a));
//                }*//*
//            }
//
//            homeDBs.setShoplist(homeShopListDBs);
//
//
//
//
//
//            *//* tag JSON Array 파싱 부분 *//*
//            JSONArray tagJsonArray = home.getJSONArray("tag");
//            for (int a = 0; a < tagJsonArray.length(); a++) {
//                homeDBs.tag.add(tagJsonArray.getString(a));
//            }*/
//
//
//        } catch (JSONException e) {
//            Log.e("jsonParser", e.toString());
//        }
//        return searchDBs;
//   }
//}
