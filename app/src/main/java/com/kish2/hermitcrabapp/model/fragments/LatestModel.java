package com.kish2.hermitcrabapp.model.fragments;

import android.annotation.SuppressLint;

import com.kish2.hermitcrabapp.bean.Inform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LatestModel {

    public void updateInforms(ArrayList<Inform> informs) {
        /* 测试，添加ListView，查看滑动效果 */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        informs.clear();
        for (int i = 0; i < 1000; i++) {
            Inform inform = new Inform();
            inform.setDate(simpleDateFormat.format(new Date()));
            inform.setTitle("星期日");
            informs.add(inform);
        }
    }
}
