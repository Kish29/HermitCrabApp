package com.kish2.hermitcrabapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.kish2.hermitcrabapp.bean.User;
import com.kish2.hermitcrabapp.utils.dev.ApplicationConfigUtil;
import com.kish2.hermitcrabapp.utils.dev.FileStorageManager;

/**
 * @App.java
 * @加载应用配置->用户保存的配置等
 * @加载本地数据->SharedPreference/Sqlite3
 * @请求网络更新数据，更新用户状态
 */
public class HermitCrabApp extends Application {

    private static HermitCrabApp app;

    public static boolean IS_USER_LOG_IN = false;

    /* 运行期间保存所有的用户信息*/
    public static User USER;

    public static boolean LOAD_USER_SUCCESS = false;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        USER = new User();
        new Thread() {
            @Override
            public void run() {
                /* 加载配置信息 */
                ApplicationConfigUtil.getConfigAndSetEditor(getAppContext());
                /* 加载本地数据 */
                FileStorageManager.initApplicationDirs(getAppContext());
                ApplicationConfigUtil.loadLocalAppData();
            }
        }.start();
    }

    public static Context getAppContext() {
        return app;
    }

    public static Resources getAppResources() {
        return app.getResources();
    }
}
