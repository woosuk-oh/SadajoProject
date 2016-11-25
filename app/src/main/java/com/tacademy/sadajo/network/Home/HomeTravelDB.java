package com.tacademy.sadajo.network.Home;

import java.util.Date;

/**
 * Created by woosuk on 2016-11-14.
 */

public class HomeTravelDB {
   public String titleCountry;
    public Date startDate;
    public Date endDate;

    public String getTitleCountry() {
        return titleCountry;
    }

    public void setTitleCountry(String titleCountry) {
        this.titleCountry = titleCountry;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
