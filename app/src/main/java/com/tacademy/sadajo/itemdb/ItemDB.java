package com.tacademy.sadajo.itemdb;

import java.util.List;

/**
 * Created by woosuk on 2016-11-14.
 */

public class ItemDB {
    String item_Id;
    String item_Name;
    List<String> item_img;


    // 11-14. 아이템의 아이디, 이름, 이미지만



    public List<String> getItem_img() {
        return item_img;
    }

    public void setItem_img(List<String> item_img) {
        this.item_img = item_img;
    }

    public String getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(String item_Id) {
        this.item_Id = item_Id;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }


}
