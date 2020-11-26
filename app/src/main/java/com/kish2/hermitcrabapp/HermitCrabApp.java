package com.kish2.hermitcrabapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.kish2.hermitcrabapp.bean.User;
import com.kish2.hermitcrabapp.utils.dev.ApplicationConfigUtil;
import com.kish2.hermitcrabapp.utils.dev.FileStorageManager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    public static ThreadPoolExecutor APP_THREAD_POOL;

    public static int CPU_NUM;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        USER = new User();
        CPU_NUM = Runtime.getRuntime().availableProcessors();
        /* 获取CPU核心数，通常设置线程最大值为 cpu * 2*/
        APP_THREAD_POOL =
                new ThreadPoolExecutor(
                        CPU_NUM,
                        CPU_NUM * 2,
                        60L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(CPU_NUM),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.CallerRunsPolicy()
                );
        APP_THREAD_POOL.execute(() -> {
            /* 加载配置信息 */
            ApplicationConfigUtil.getConfigAndSetEditor(getAppContext());
            /* 加载本地数据 */
            FileStorageManager.initApplicationDirs(getAppContext());
            ApplicationConfigUtil.loadLocalAppData();
        });

    }

    public static Context getAppContext() {
        return app;
    }

    public static Resources getAppResources() {
        return app.getResources();
    }
}
