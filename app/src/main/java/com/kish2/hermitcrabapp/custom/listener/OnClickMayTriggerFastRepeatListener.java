package com.kish2.hermitcrabapp.custom.listener;

import android.view.View;

public abstract class OnClickMayTriggerFastRepeatListener extends BaseOnClickListener {

    public static void setLastClickTime(long lastClickTime) {
        LAST_CLICK_TIME = lastClickTime;
    }

    private static final long CLICK_INTERVAL = 1000;
    private static long LAST_CLICK_TIME = -1;

    private boolean isFastRepeatClick() {
        if (LAST_CLICK_TIME == -1) {    // 如果是第一次点击，直接返回false
            LAST_CLICK_TIME = System.currentTimeMillis();
            return false;
        }
        long time = System.currentTimeMillis();
        long interval = time - LAST_CLICK_TIME;
        if (0 < interval && interval < CLICK_INTERVAL)
            return true;
        LAST_CLICK_TIME = time;
        return false;
    }

    @Override
    public void onClick(View v) {
        if (isFastRepeatClick())
            return;
        onMayTriggerFastRepeatClick(v);
    }

    /* 快速点击事件的回调方法 */
    public abstract void onMayTriggerFastRepeatClick(View v);
}
