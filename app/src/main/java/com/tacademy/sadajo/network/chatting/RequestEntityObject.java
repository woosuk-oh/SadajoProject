package com.tacademy.sadajo.network.chatting;

/**
 * Created by EUNZY on 2016. 11. 30..
 */

public class RequestEntityObject {


    public int buyer;
    public int seller;
    public String countryName;
    public String productName;
    public String productPrice;
    public String productOpt;


    public RequestEntityObject() {
    }

    public RequestEntityObject(int buyer, int seller,String countryName,
                               String productName, String productPrice, String productOpt
    ) {
        super();
        this.buyer = buyer;
        this.seller = seller;
        this.countryName = countryName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productOpt = productOpt;

    }

}
