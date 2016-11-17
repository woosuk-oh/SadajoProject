package com.tacademy.sadajo.shoppinglist;

/**
 * Created by EUNZY on 2016. 11. 17..
 */

public class ShoppingListData {

    String countryName;
    String travelDate;


    public ShoppingListData() {
    }

    public ShoppingListData(String countryName, String travelDate) {
        this.countryName = countryName;
        this.travelDate = travelDate;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }
}
