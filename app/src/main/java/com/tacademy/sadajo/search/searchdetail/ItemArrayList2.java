package com.tacademy.sadajo.search.searchdetail;

/**
 * Created by woosuk on 2016-11-15.
 */



// 화면에 출력할 데이터를 담은 객체
public class ItemArrayList2 {


    int image;
    String itemtext;
    String itemtext2;

    public int getImage(){
        return image;

    }

    public String getitemtext(){
        return itemtext;
    }
    public String getitemtext2(){
        return itemtext2;
    }

    public ItemArrayList2(int image, String itemtext, String itemtext2){
        this.image= image;
        this.itemtext= itemtext;
        this.itemtext2= itemtext2;

    }

}

