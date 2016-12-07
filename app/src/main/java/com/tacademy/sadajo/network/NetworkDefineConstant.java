package com.tacademy.sadajo.network;


public class NetworkDefineConstant {

    public static final String HOST_URL = "http://52.78.89.88:3001"; // 테스트 서버임.
  //  public static final String HOST_URL = "http://52.78.89.88:3000";

    public static String SERVER_URL_REQUEST_HOME;
    public static String SERVER_URL_REQUEST_SHOPLIST;
    public static String SERVER_URL_REQUEST_LIKELIST;
    public static String SERVER_URL_REQUST_CHATLIST;
    public static String SERVER_URL_REQUST_CHATTINGLIST;
    public static String SERVER_URL_REQUST_LIKEDETAIL;
    public static String SERVER_URL_REQUST_SHOPDETAIL;
    public static String SERVER_URL_REQUST_MYPAGE;


    public static String SERVER_URL_INSERT_SCHEDULE;
    public static String SERVER_URL_INSERT_REQUEST;

    public static String SEARCH_LIST;
    public static String SEARCH_LIST_COUNTRY;
    public static String SEARCH_LIST_DETAIL;
    public static String SEARCH_LIST_DETAIL_ZZIM;


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
        SERVER_URL_REQUST_CHATTINGLIST =
                HOST_URL + "/chatlist";
        SERVER_URL_REQUST_LIKEDETAIL =
                HOST_URL + "/likedetail";
        SERVER_URL_REQUST_SHOPDETAIL =
                HOST_URL + "/shopdetail";
        SERVER_URL_REQUST_MYPAGE =
                HOST_URL + "/userpage";
        SEARCH_LIST =
                HOST_URL + "/goodslist";
        SEARCH_LIST_COUNTRY =
                HOST_URL + "/goodslist" + "?country=%s"; // 스피너 선택한 값이 있으면 해당 url로 요청.


        SEARCH_LIST_DETAIL =
                HOST_URL + "/goods" + "/%s";

        SEARCH_LIST_DETAIL_ZZIM =
                HOST_URL + "/like";

    }
}
