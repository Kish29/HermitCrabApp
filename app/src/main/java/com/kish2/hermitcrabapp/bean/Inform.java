package com.kish2.hermitcrabapp.bean;

import com.airbnb.lottie.LottieAnimationView;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Inform {

    private String title;

    private Date date;

    private int picInt;

    private LottieAnimationView favor;

    public int getPicInt() {
        return picInt;
    }

    @NotNull
    @Override
    public String toString() {
        return "Inform{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", picInt=" + picInt +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }

    public void setPicInt(int picInt) {
        this.picInt = picInt;
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
