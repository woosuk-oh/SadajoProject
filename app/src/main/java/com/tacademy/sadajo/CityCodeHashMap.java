package com.tacademy.sadajo;

import java.util.HashMap;

/**
 * Created by EUNZY on 2016. 11. 29..
 */

public class CityCodeHashMap {
    static HashMap<String, String> cityCodeHash = new HashMap<>();

    static {
        cityCodeHash.put("뉴욕", "NYC");
        cityCodeHash.put("워싱턴", "WST");
        cityCodeHash.put("로스엔젤레스", "LAC");
        cityCodeHash.put("보스턴", "BOS");
        cityCodeHash.put("하와이", "HWI");
        cityCodeHash.put("괌", "GUA");
        cityCodeHash.put("도쿄", "TOK");
        cityCodeHash.put("나고야", "NAG");
        cityCodeHash.put("오사카", "OSK");
        cityCodeHash.put("삿포로", "SAP");
        cityCodeHash.put("홍콩", "HKC");
        cityCodeHash.put("파리", "PAR");
        cityCodeHash.put("리용", "LYO");
        cityCodeHash.put("마르세이유", "MAR");
        cityCodeHash.put("보르도", "BOR");
        cityCodeHash.put("로마", "ROM");
        cityCodeHash.put("밀라노", "MIL");
        cityCodeHash.put("피렌체", "FRZ");
        cityCodeHash.put("베네치아", "VEZ");
        cityCodeHash.put("피사", "PIS");
        cityCodeHash.put("나폴리", "NAP");
        cityCodeHash.put("방콕", "BNK");
        cityCodeHash.put("파타야", "PTY");
        cityCodeHash.put("푸켓", "PHU");
        cityCodeHash.put("치앙마이", "CHM");
        cityCodeHash.put("코사무이", "KOS");
        cityCodeHash.put("시드니", "SYD");
        cityCodeHash.put("브리즈번", "BRI");
        cityCodeHash.put("캔버라", "CBR");
        cityCodeHash.put("다윈", "DAR");
        cityCodeHash.put("퍼스", "PER");
        cityCodeHash.put("베이징", "BEJ");
        cityCodeHash.put("상하이", "SHA");
        cityCodeHash.put("톈진", "TIJ");
        cityCodeHash.put("충칭", "CHQ");
        cityCodeHash.put("마닐라", "MAN");
        cityCodeHash.put("세부", "CEB");
        cityCodeHash.put("보라카이", "BOC");
        cityCodeHash.put("타이베이", "TPI");
        cityCodeHash.put("가오슝", "KAO");
        cityCodeHash.put("타이중", "TCH");


    }

    public String getCityCode(String key) {
        return cityCodeHash.get(key);
    }
}

