package com.tacademy.sadajo.network.shoppinglist;

import java.util.HashMap;

/**
 * Created by EUNZY on 2016. 11. 28..
 */

public class CountryEngHashMap {


    //국문국가명,영문국가명 hash map
    static HashMap<String,String> countryEngHash;
    static {
        countryEngHash = new HashMap<>();
        countryEngHash.put("미국","USA");
        countryEngHash.put("일본","JAPAN");
        countryEngHash.put("홍콩","HONGKONG");
        countryEngHash.put("프랑스","FRANCE");
        countryEngHash.put("이탈리아","ITALY");
        countryEngHash.put("태국","THAILAND");
        countryEngHash.put("호주","AUSTRALIA");
        countryEngHash.put("중국","CHINA");
        countryEngHash.put("필리핀","PHILIPPINES");
        countryEngHash.put("대만","TAIWAN");

    }

    public static String  getCountryEngName(String key) {
        return countryEngHash.get(key);
    }

}
