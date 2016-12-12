package com.tacademy.sadajo.mypage;

/**
 * Created by EUNZY on 2016. 12. 12..
 */

public class MypageItemData {
    String productName;
    String productImg;
    String countryFlag;
    String countryName;
    String goodsId;

    public MypageItemData(String productName, String productImg, String countryFlag, String countryName,String goodsId) {
        this.productName =productName;
        this.productImg =productImg;
        this.countryFlag =countryFlag;
        this.countryName =countryName;
        this.goodsId =goodsId;



    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
