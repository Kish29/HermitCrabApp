package com.kish2.hermitcrabapp.bean;

import java.util.Date;

public class Inform {

    private String title;

    private Date date;

    private String imgSrc;

    @Override
    public String toString() {
        return "Inform{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
