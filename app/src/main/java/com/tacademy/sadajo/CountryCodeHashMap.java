package com.tacademy.sadajo;

import java.util.HashMap;

/**
 * Created by EUNZY on 2016. 11. 29..
 */

public class CountryCodeHashMap {
    static HashMap<String, String> countryCodeHash = new HashMap<>();

    static {
        countryCodeHash.put("미국", "USA");
        countryCodeHash.put("일본", "JPN");
        countryCodeHash.put("홍콩", "HKG");
        countryCodeHash.put("이탈리아","ITA");
        countryCodeHash.put("프랑스","FRA");
        countryCodeHash.put("태국", "THA");
        countryCodeHash.put("호주", "AUS");
        countryCodeHash.put("중국", "CHN");
        countryCodeHash.put("필리핀", "PHL");
        countryCodeHash.put("대만", "TWN");
        countryCodeHash.put("한국", "KOR");

    }

    static HashMap<String, String> countryCodeHash2 = new HashMap<>();

    static { // TODO 작성하다 말았음 -> 나중에 서버에서 받는대로 뿌리면 되기때문에
        countryCodeHash2.put("USA", "AMERICA");
        countryCodeHash2.put("JPN", "");
        countryCodeHash2.put("홍콩", "HKG");
        countryCodeHash2.put("이탈리아","ITA");
        countryCodeHash2.put("프랑스","FRA");
        countryCodeHash2.put("태국", "THA");
        countryCodeHash2.put("호주", "AUS");
        countryCodeHash2.put("중국", "CHN");
        countryCodeHash2.put("필리핀", "PHL");
        countryCodeHash2.put("대만", "TWN");

    }

    public String getCountryCode(String key) {
        return countryCodeHash.get(key);
    }
}
