package com.kish2.hermitcrabapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/* 该类通过MainActivity的子线程，将数据存储在手机本地，需要用到的时候进行读取 */
public class DeviceInfo {

    private SharedPreferences deviceInfo;

    public static final String SCREEN_REFRESH_RATE = "SCREEN_REFRESH_RATE";

    public DeviceInfo(Context context, Activity activity) {
        this.deviceInfo = context.getSharedPreferences("device_info", Context.MODE_PRIVATE);
        initData(activity);
    }

    private void initData(Activity activity) {

        float refreshRate = activity.getWindowManager().getDefaultDisplay().getRefreshRate();

        activity.getWindowManager().getDefaultDisplay().getRefreshRate();

        SharedPreferences.Editor editor = deviceInfo.edit();
        editor.putFloat(SCREEN_REFRESH_RATE, refreshRate).apply();
    }

}
