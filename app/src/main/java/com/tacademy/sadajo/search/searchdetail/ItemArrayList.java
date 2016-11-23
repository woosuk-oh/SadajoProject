package com.tacademy.sadajo.search.searchdetail;

/**
 * Created by woosuk on 2016-11-15.
 */



// 화면에 출력할 데이터를 담은 객체
public class ItemArrayList {


    int image;
    String itemname;

    public int getImage(){
        return image;

    }

    public String getitemname(){
        return itemname;
    }
    public ItemArrayList(int image, String itemname){
        this.image= image;
        this.itemname= itemname;
    }

}

