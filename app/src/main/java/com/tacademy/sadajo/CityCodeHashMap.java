package com.tacademy.sadajo;

import java.util.HashMap;

/**
 * Created by EUNZY on 2016. 11. 29..
 */

public class CityCodeHashMap {
    static HashMap<String, String> cityCodeHash = new HashMap<>();
    static {
        cityCodeHash.put("뉴욕", "NYC");
        cityCodeHash.put("워싱턴", "JPN");
        cityCodeHash.put("로스엔젤레스", "HKG");
        cityCodeHash.put("보스턴", "ITA");
        cityCodeHash.put("하와이", "ITA");
        cityCodeHash.put("괌", "ITA");
        cityCodeHash.put("도쿄", "ITA");
        cityCodeHash.put("오사카", "ITA");
        cityCodeHash.put("삿포로", "ITA");
        cityCodeHash.put("홍콩", "ITA");
        cityCodeHash.put("파리", "ITA");
        cityCodeHash.put("리용", "ITA");
        cityCodeHash.put("마르세이유", "ITA");
        cityCodeHash.put("보르도", "ITA");
        cityCodeHash.put("로마", "ITA");
        cityCodeHash.put("밀라노", "ITA");
        cityCodeHash.put("피렌체", "ITA");
        cityCodeHash.put("베네치아", "ITA");
        cityCodeHash.put("피사", "ITA");
        cityCodeHash.put("나폴리", "ITA");
        cityCodeHash.put("방콕", "ITA");
        cityCodeHash.put("방콕", "ITA");
        cityCodeHash.put("파타야", "ITA");
        cityCodeHash.put("푸켓", "ITA");
        cityCodeHash.put("치앙마이", "ITA");
        cityCodeHash.put("코사무이", "ITA");
        cityCodeHash.put("시드니", "ITA");
        cityCodeHash.put("브리즈번", "ITA");
        cityCodeHash.put("캔버라", "ITA");
        cityCodeHash.put("다윈", "ITA");
        cityCodeHash.put("퍼스", "ITA");
        cityCodeHash.put("베이징", "ITA");
        cityCodeHash.put("상하이", "ITA");
        cityCodeHash.put("톈진", "ITA");
        cityCodeHash.put("충칭", "ITA");
        cityCodeHash.put("마닐라", "ITA");
        cityCodeHash.put("세부", "ITA");
        cityCodeHash.put("보라카이", "ITA");
        cityCodeHash.put("타이베이", "ITA");
        cityCodeHash.put("가오슝", "ITA");
        cityCodeHash.put("타이중", "ITA");

    }

    public String getCityCode(String key) {
        return cityCodeHash.get(key);
    }
}

