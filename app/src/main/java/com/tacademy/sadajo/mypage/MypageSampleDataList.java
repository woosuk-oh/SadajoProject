package com.tacademy.sadajo.mypage;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 12. 12..
 */

public class MypageSampleDataList {
    public static ArrayList<MypageItemData> itemDataArrayList;

    static {
        itemDataArrayList = new ArrayList<MypageItemData>();
        itemDataArrayList.add(new MypageItemData("필로소피 버블바쓰", "https://s3.ap-northeast-2.amazonaws.com/sadajoimage/goods_img/USA_008.jpg", "https://s3.ap-northeast-2.amazonaws.com/sadajobox/flag/usa.png", "미국", "USA_008"));
        itemDataArrayList.add(new MypageItemData("로이히 츠보코 동전파스", "https://s3.ap-northeast-2.amazonaws.com/sadajoimage/goods_img/JPN_005.jpg", "https://s3.ap-northeast-2.amazonaws.com/sadajobox/flag/jpn.png", "일본", "JPN_005"));
        itemDataArrayList.add(new MypageItemData("크랩트리 앤 에블린 핸드크림", "https://s3.ap-northeast-2.amazonaws.com/sadajoimage/goods_img/HKG_003.jpg", "https://s3.ap-northeast-2.amazonaws.com/sadajobox/flag/hkg.png", "홍콩", "HKG_003"));
    }


}
