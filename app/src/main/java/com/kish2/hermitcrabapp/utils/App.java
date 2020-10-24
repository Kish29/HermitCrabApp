package com.kish2.hermitcrabapp.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class App extends Application {

    private static App app;

    public static boolean IS_USER_LOG_IN = false;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Context getAppContext() {
        return app;
    }

    public static Resources getAppResources() {
        return app.getResources();
    }
}
