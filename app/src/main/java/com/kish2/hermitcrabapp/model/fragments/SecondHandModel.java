package com.kish2.hermitcrabapp.model.fragments;

import com.kish2.hermitcrabapp.bean.Inform;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SecondHandModel {
    public void updateInforms(ArrayList<Inform> informs) {
        /* 测试，添加ListView，查看滑动效果 */
        informs.clear();
        for (int i = 0; i < 100; i++) {
            Inform inform = new Inform();
            inform.setDate(new Date());
            int j = new Random().nextInt(11);
            if (j <= 5)
                inform.setImgSrc("yes");
            else inform.setImgSrc("no");
            inform.setTitle("model:j->" + j + '\t');
            informs.add(inform);
        }
    }
}
