package com.tacademy.sadajo;

import java.util.HashMap;

/**
 * Created by EUNZY on 2016. 11. 29..
 */

public  class CountryCodeHashMap {
   static HashMap<String,String> countryCodeHash = new HashMap<>();
    static {
        countryCodeHash.put("USA","미국");
        countryCodeHash.put("JPN","일본");
        countryCodeHash.put("HKG","홍콩");
        countryCodeHash.put("ITA","이탈리아");
        countryCodeHash.put("THA","태국");
        countryCodeHash.put("AUS","호주");
        countryCodeHash.put("CHN","중국");
        countryCodeHash.put("PHL","필리핀");
        countryCodeHash.put("PHL","대만");

    }

    public String getCountryCode(String name){


        return countryCodeHash.get(name);
    }
}
