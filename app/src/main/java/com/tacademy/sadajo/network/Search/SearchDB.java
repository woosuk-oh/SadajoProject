package com.tacademy.sadajo.network.Search;

import java.util.ArrayList;

/**
 * Created by woosuk on 2016-11-14.
 */

public class SearchDB {
    public int count;
    public ArrayList<String> hashtag;
    public ArrayList<String> sell_place;
    public ArrayList<String> tag_price;


  //  public ArrayList<SearchDB> searchDBs; // 재귀호출
    public ArrayList<SearchGoodsDB> searchGoodsDBs; //goods에서 data 제이슨 어레이리스트.



    public ArrayList<SearchGoodsDB> getSearchGoodsDBs() {
        return searchGoodsDBs;
    }

    public void setSearchGoodsDBs(ArrayList<SearchGoodsDB> searchGoodsDBs) {
        this.searchGoodsDBs = searchGoodsDBs;
    }

    /*public void setSearchDBs(ArrayList<SearchDB> searchDBs){
        this.searchDBs = searchDBs;

    }
    public ArrayList<SearchDB> getSearchDBs(){
        return searchDBs;
    }*/




    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }




    public ArrayList<String> getHashtag() {
        return hashtag;
    }

    public void setHashtag(ArrayList<String> hashtag) {
        this.hashtag = hashtag;
    }

    public ArrayList<String> getSell_place() {
        return sell_place;
    }

    public void setSell_place(ArrayList<String> sell_place) {
        this.sell_place = sell_place;
    }

    public ArrayList<String> getTag_price() {
        return tag_price;
    }

    public void setTag_price(ArrayList<String> tag_price) {
        this.tag_price = tag_price;
    }



}
