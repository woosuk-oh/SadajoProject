package com.tacademy.sadajo.shoppinglist;

/**
 * Created by EUNZY on 2016. 11. 17..
 */

public class ShoppingListData {

    String countryName;
    String travelDate;
    int    countryImage;


    public ShoppingListData() {
    }

    public ShoppingListData(String countryName, String travelDate,int countryImage) {
        this.countryName = countryName;
        this.travelDate = travelDate;
        this.countryImage = countryImage;
    }

    public String getCountryName() {
        return countryName;
    }


    public String getTravelDate() {
        return travelDate;
    }

    public int getCountryImage() {
        return countryImage;
    }


}
