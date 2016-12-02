package com.tacademy.sadajo.network.Search;

/**
 * Created by woosuk on 2016-11-28.
 */

public class SearchGoodsDB {

    public int item_id;
    public String goods_code;
    public String country;
    public String goods_name;
    public int click;
    public String regdate;
    public String item_img;

    public String getItem_img() {
        return item_img;

    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getGoods_code() {
        return goods_code;
    }

    public void setGoods_code(String goods_code) {
        this.goods_code = goods_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
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
}
