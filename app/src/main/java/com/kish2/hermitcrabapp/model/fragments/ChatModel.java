package com.kish2.hermitcrabapp.model.fragments;

import com.kish2.hermitcrabapp.bean.Inform;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ChatModel {
    public void updateInforms(ArrayList<Inform> informs) {
        /* 测试，添加ListView，查看滑动效果 */
        informs.clear();
        for (int i = 0; i < 30; i++) {
            Inform inform = new Inform();
            inform.setDate(new Date());
            int j = new Random().nextInt(6);
            inform.setPicInt(j);
            inform.setTitle("model:j->" + j + '\t');
            informs.add(inform);
        }
    }
}
