package com.tacademy.sadajo.network.Search.SeachDetail;

import java.util.ArrayList;


//TODO 데이터 모델 작성필요


public class SearchDetailDB {


    public int item_id;
    public ArrayList<String> goods_img;
    public String goods_code;
    public String goods_name;


    public String goods_content;
    public String goods_country;
    public int click;
    public String regdate;
    public ArrayList<String> hashtag;

    public ArrayList<ShopermanDB> shoperman;
    public String unit;

    public ArrayList<String> sell_place;
    public ArrayList<String> tag_price;
    public ArrayList<String> price;

    public ArrayList<TipsDB> tips;



    public String getGoods_content() {
        return goods_content;
    }

    public void setGoods_content(String goods_content) {
        this.goods_content = goods_content;
    }

    public ArrayList<ShopermanDB> getShoperman() {
        return shoperman;
    }

    public void setShoperman(ArrayList<ShopermanDB> shoperman) {
        this.shoperman = shoperman;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public ArrayList<String> getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(ArrayList<String> goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_code() {
        return goods_code;
    }

    public void setGoods_code(String goods_code) {
        this.goods_code = goods_code;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_country() {
        return goods_country;
    }

    public void setGoods_country(String goods_country) {
        this.goods_country = goods_country;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
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

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }

    public ArrayList<TipsDB> getTips() {
        return tips;
    }

    public void setTips(ArrayList<TipsDB> tips) {
        this.tips = tips;
    }
}