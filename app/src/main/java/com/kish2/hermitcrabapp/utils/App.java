package com.kish2.hermitcrabapp.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.kish2.hermitcrabapp.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

public class App extends Application {

    private static App app;

    public static boolean IS_USER_LOG_IN = false;

    /* 运行期间保存所有的用户信息*/
    public static User USER;

    public static boolean LOAD_USER_SUCCESS = false;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        USER = new User();
    }

    public static Context getAppContext() {
        return app;
    }

    public static Resources getAppResources() {
        return app.getResources();
    }
}
