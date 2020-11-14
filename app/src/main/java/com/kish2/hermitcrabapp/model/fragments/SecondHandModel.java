package com.kish2.hermitcrabapp.model.fragments;

import com.kish2.hermitcrabapp.bean.Inform;

import java.util.ArrayList;
import java.util.Date;

public class SecondHandModel {
    public void updateInforms(ArrayList<Inform> informs) {
        /* 测试，添加ListView，查看滑动效果 */
        informs.clear();
        for (int i = 0; i < 100; i++) {
            Inform inform = new Inform();
            inform.setDate(new Date());
            inform.setTitle("");
            informs.add(inform);
        }
    }
}
