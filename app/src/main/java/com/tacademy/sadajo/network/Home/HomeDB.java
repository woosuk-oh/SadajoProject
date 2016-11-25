package com.tacademy.sadajo.network.Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by woosuk on 2016-11-14.
 */

public class HomeDB {
    public String msg;
    public ArrayList<HomeTravelDB> travelInfos;
    public String travelCountry;
    public String countryImg;
    public ArrayList<String> tag;
    public ArrayList<HomeShoplistDB> shoplist;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<HomeTravelDB> getTravelInfos() {
        return travelInfos;
    }

    public void setTravelInfos(ArrayList<HomeTravelDB> travelInfos) {
        this.travelInfos = travelInfos;
    }

    public String getTravelCountry() {
        return travelCountry;
    }

    public void setTravelCountry(String travelCountry) {
        this.travelCountry = travelCountry;
    }

    public String getCountryImg() {
        return countryImg;
    }

    public void setCountryImg(String countryImg) {
        this.countryImg = countryImg;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public ArrayList<HomeShoplistDB> getShoplist() {
        return shoplist;
    }

    public void setShoplist(ArrayList<HomeShoplistDB> shoplist) {
        this.shoplist = shoplist;
    }
    // 나중에 gson 사용시 게터 세터 해줘야할듯?



}
