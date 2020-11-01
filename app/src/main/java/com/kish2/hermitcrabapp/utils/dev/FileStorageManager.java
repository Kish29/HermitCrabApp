package com.kish2.hermitcrabapp.utils.dev;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

public class FileStorageManager {

    public enum DIR_TYPE {
        USER_AVATAR,
        USER_BANNER_BACKGROUND,
        FILE_DOWNLOAD,
        APP_SIDE_MENU_BACKGROUND
    }

    private static String USER_AVATAR_PATH;
    private static String USER_BANNER_BKG_PATH;
    private static String FILE_DOWNLOAD_PATH;
    private static String APP_SIDE_MENU_BKG_PATH;


    public static final String user_avatar_file_name = "userAvatar.png";
    public static String APP_PROVIDER_AUTHORITY = "com.kish2.hermitcrabapp.FileProvider";

    /* 在app中调用*/
    public static void initApplicationDirs(Context context) {
        /* 初始化目录名称 */
        String APP_PIC_DIR = ContextCompat.getExternalFilesDirs(context, Environment.DIRECTORY_PICTURES)[0].toString();
        USER_AVATAR_PATH = APP_PIC_DIR + "/user/avatar";
        USER_BANNER_BKG_PATH = APP_PIC_DIR + "/user/bannerBkg";
        APP_SIDE_MENU_BKG_PATH = APP_PIC_DIR + "/app/sideMenuBkg";
        FILE_DOWNLOAD_PATH = ContextCompat.getExternalFilesDirs(context, Environment.DIRECTORY_DOWNLOADS)[0].toString();
        /* 创建目录 */
        File file = new File(USER_AVATAR_PATH);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
        file = new File(USER_BANNER_BKG_PATH);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
        file = new File(APP_SIDE_MENU_BKG_PATH);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
    }

    public static File createFileIfNull(@NonNull String fileName, @NonNull DIR_TYPE dir_type) throws IOException {
        File file;
        switch (dir_type) {
            case USER_AVATAR:
                file = new File(USER_AVATAR_PATH, fileName);
                if (!file.exists()) {
                    boolean mkdir = file.createNewFile();
                }
                break;
            case USER_BANNER_BACKGROUND:
                file = new File(USER_BANNER_BKG_PATH, fileName);
                if (!file.exists()) {
                    boolean mkdir = file.createNewFile();
                }
                break;
            case FILE_DOWNLOAD:
                file = new File(FILE_DOWNLOAD_PATH, fileName);
                if (!file.exists()) {
                    boolean mkdir = file.createNewFile();
                }
                break;
            case APP_SIDE_MENU_BACKGROUND:
                file = new File(APP_SIDE_MENU_BKG_PATH, fileName);
                if (!file.exists()) {
                    boolean mkdirs = file.createNewFile();
                }
                break;
            default:
                file = null;
                break;
        }
        return file;
    }
}
