package com.kish2.hermitcrabapp.bean;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Inform {

    private String imgUrl;

    private Date date;

//    private String date;

    private String weekDay;

    private String title;

    private String address;

    @NotNull
    @Override
    public String toString() {
        return "Inform{" +
                "imgUrl='" + imgUrl + '\'' +
                ", date=" + date +
                ", weekDay='" + weekDay + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }

    private String imgSrc;

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
