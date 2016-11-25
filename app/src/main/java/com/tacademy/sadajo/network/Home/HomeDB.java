package com.tacademy.sadajo.network.Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by woosuk on 2016-11-14.
 */

public class HomeDB {
    public String msg;
    public ArrayList<HomeTravelDB> travelInfos = new ArrayList<>();
    public String travelCountry;
    public String countryImg;
    public ArrayList<String> tag = new ArrayList<>();
    public ArrayList<HomeShoplistDB> shoplist = new ArrayList<>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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


    public ArrayList<HomeTravelDB> getTravelInfos() {
        return travelInfos;
    }

    public void setTravelInfos(ArrayList<HomeTravelDB> travelInfos) {
        this.travelInfos = travelInfos;
    }

}
