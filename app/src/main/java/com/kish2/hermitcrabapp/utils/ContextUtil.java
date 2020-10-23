package com.kish2.hermitcrabapp.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.kish2.hermitcrabapp.utils.view.ThemeUtil;

import java.io.PushbackInputStream;

public class ContextUtil extends Application {

    private static ContextUtil contextUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        contextUtil = this;
    }

    public static Context getAppContext() {
        return contextUtil;
    }

    public static Resources getAppResources() {
        return contextUtil.getResources();
    }
}
