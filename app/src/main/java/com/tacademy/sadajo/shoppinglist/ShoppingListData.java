package com.tacademy.sadajo.shoppinglist;

/**
 * Created by EUNZY on 2016. 11. 17..
 */

public class ShoppingListData {


    String countryName;
    String travelDate;
    String cityName;
    int productImgae;


    public ShoppingListData() {
    }

    public ShoppingListData(String countryName, String cityName, String travelDate, int productImgae) {
        this.cityName = cityName;
        this.countryName = countryName;
        this.travelDate = travelDate;
        this.productImgae = productImgae;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCityName() {
        return cityName;
    }


    public String getTravelDate() {
        return travelDate;
    }

    public int getProductImage() {
        return productImgae;
    }


}
