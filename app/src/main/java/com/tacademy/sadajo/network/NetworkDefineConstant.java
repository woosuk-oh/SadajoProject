package com.tacademy.sadajo.network;

/**
 * Created by cbk10 on 2016-11-08.
 */

public class NetworkDefineConstant {
    public static final String HOST_URL = "http://52.78.234.20:3000";
   // public static final String GENRE_REQUEST_URL= "http://apis.skplanetx.com/melon/genres?version=1";

    //요청 URL path
    public static String SERVER_URL_REQUEST_HOME;
    /*public static String SERVER_URL_REQUEST_GENRE_CHART;*/

    static{
        SERVER_URL_REQUEST_HOME =
                HOST_URL + "/home";
//        SERVER_URL_REQUEST_GENRE_CHART =
//                HOST_URL + "/%s?version=1&page=%s&count=10";
    }
}