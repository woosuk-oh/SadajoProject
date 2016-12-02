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
    public static String SERVER_URL_INSERT_REQUEST;
    public static String SEARCH_LIST;
    public static String SEARCH_LIST_COUNTRY;
    public static String SEARCH_LIST_DETAIL;


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
        SERVER_URL_INSERT_REQUEST =
                HOST_URL + "/request";

        SEARCH_LIST =
                HOST_URL + "/goods";
        SEARCH_LIST_COUNTRY =
                HOST_URL + SEARCH_LIST + "?country=%s"; // 스피너 선택한 값이 있으면 해당 url로 요청.
        SEARCH_LIST_DETAIL =
                HOST_URL + SEARCH_LIST + "/%s";


    }
}
