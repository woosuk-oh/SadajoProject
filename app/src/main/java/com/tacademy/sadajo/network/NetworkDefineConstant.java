package com.tacademy.sadajo.network;

/**
 * Created by cbk10 on 2016-11-08.
 */

public class NetworkDefineConstant {

    public static final String HOST_URL = "http://52.78.89.88:3000";
    // public static final String GENRE_REQUEST_URL= "http://apis.skplanetx.com/melon/genres?version=1";

    //요청 URL path
    public static String SERVER_URL_REQUEST_HOME;
    public static String SERVER_URL_REQUEST_SHOPLIST;
    public static String SERVER_URL_REQUEST_LIKELIST;
    public static String SERVER_URL_INSERT_SCHEDULE;

    /*public static String SERVER_URL_REQUEST_GENRE_CHART;*/

    static {
        SERVER_URL_REQUEST_HOME =
                HOST_URL + "/home";
        SERVER_URL_REQUEST_SHOPLIST =
                HOST_URL + "/shoplist";
        SERVER_URL_REQUEST_LIKELIST =
                HOST_URL + "/likelist";
        SERVER_URL_INSERT_SCHEDULE =
                HOST_URL + "/registerSchedule";


    }
}
