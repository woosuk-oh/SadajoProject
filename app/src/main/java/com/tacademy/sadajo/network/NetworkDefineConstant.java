package com.tacademy.sadajo.network;


public class NetworkDefineConstant {

    public static final String HOST_URL = "http://52.78.89.88:3000";

    public static String SERVER_URL_REQUEST_HOME;
    public static String SERVER_URL_REQUEST_SHOPLIST;
    public static String SERVER_URL_REQUEST_LIKELIST;
    public static String SERVER_URL_INSERT_SCHEDULE;
    public static String SERVER_URL_INSERT_REQUEST;
    public static String SERVER_URL_REQUST_CHATLIST;


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
        SERVER_URL_REQUST_CHATLIST =
                HOST_URL + "/chat";


    }
}
