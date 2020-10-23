package com.kish2.hermitcrabapp.custom.listener;

import android.view.View;

public abstract class OnClickCustomListener extends BaseOnClickListener {

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (isCorrect()) {
            onCorrectClick(v);
        } else {
            onIncorrectClick(v);
        }
    }

    /**
     * 判断是否逻辑通过
     *
     * @return
     */
    public abstract boolean isCorrect();

    /**
     * 判断正确之后执行的逻辑请求
     *
     * @param v 点击视图
     */
    public abstract void onCorrectClick(View v);

    /**
     * 判断失败之后执行的逻辑请求
     *
     * @param v 点击视图
     */
    public abstract void onIncorrectClick(View v);
}
