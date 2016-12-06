package com.tacademy.sadajo.network.Home;

/**
 * Created by EUNZY on 2016. 11. 29..
 */

public class ScheduleEntityObject {
    public int user;
    public String country; //국가
    public String city; //도시
    public String start; //여행시작
    public String end; //여행끝


    public ScheduleEntityObject() {
    }

    public ScheduleEntityObject(int user, String country,
                                String city, String start, String end
    ) {
        super();
        this.user = user;
        this.country = country;
        this.city = city;
        this.start = start;
        this.end = end;

    }
}
