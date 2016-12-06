package com.tacademy.sadajo.network.Search.SeachDetail;

/**
 * Created by woosuk on 2016-12-02.
 */
public class TipsDB {

    public String writer;
    public String tips_content;
    public String tips_user_img;
    public String regdate;

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getTips_user_img() {
        return tips_user_img;
    }

    public void setTips_user_img(String tips_user_img) {
        this.tips_user_img = tips_user_img;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTips_content() {
        return tips_content;
    }

    public void setTips_content(String tips_content) {
        this.tips_content = tips_content;
    }
}
