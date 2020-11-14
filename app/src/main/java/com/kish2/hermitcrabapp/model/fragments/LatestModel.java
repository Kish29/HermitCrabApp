package com.kish2.hermitcrabapp.model.fragments;

import com.kish2.hermitcrabapp.bean.Inform;

import java.util.ArrayList;
import java.util.Date;

public class LatestModel {

    public void updateInforms(ArrayList<Inform> informs) {
        /* 测试，添加ListView，查看滑动效果 */
        informs.clear();
        for (int i = 0; i < 1000; i++) {
            Inform inform = new Inform();
            inform.setDate(new Date());
            inform.setTitle("星期日");
            informs.add(inform);
        }
    }
}
