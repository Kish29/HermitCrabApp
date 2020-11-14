package com.kish2.hermitcrabapp.utils.dev;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
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
    public static final String banner_bkg_file_name = "bannerBkg.png";
    public static final String app_side_menu_bkg_file_name = "sideMenuBkg.png";
    public static final String origin_banner_bkg_file_name = "orgBannerBkg.png";
    public static String APP_PROVIDER_AUTHORITY = "com.kish2.hermitcrabapp.FileProvider";

    public static String getUserAvatarPath() {
        return USER_AVATAR_PATH + "/" + user_avatar_file_name;
    }

    public static String getUserBannerBkgPath() {
        return USER_BANNER_BKG_PATH + "/" + banner_bkg_file_name;
    }

    public static String getAppSideMenuBkgPath() {
        return APP_SIDE_MENU_BKG_PATH + "/" + app_side_menu_bkg_file_name;
    }

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

    public static Uri storeBitmapAsPng(Bitmap bitmap, String file_name, @NonNull DIR_TYPE dir_type) throws IOException {
        File file = createFileIfNull(file_name, dir_type);
        Uri uri = Uri.fromFile(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return uri;
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
