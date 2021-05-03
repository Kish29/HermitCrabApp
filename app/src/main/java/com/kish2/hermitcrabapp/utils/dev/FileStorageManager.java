package com.kish2.hermitcrabapp.utils.dev;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileStorageManager {

    public enum DIR_TYPE {
        USER_AVATAR,
        USER_BANNER_BACKGROUND,
        FILE_DOWNLOAD,
        APP_SIDE_MENU_BACKGROUND,
        CACHE
    }

    private static String USER_AVATAR_PATH;
    private static String USER_BANNER_BKG_PATH;
    private static String FILE_DOWNLOAD_PATH;
    private static String APP_SIDE_MENU_BKG_PATH;
    private static String CACHE_DIR;

    public static String APP_PROVIDER_AUTHORITY = "com.kish2.hermitcrabapp.FileProvider";

    /* 在app中调用*/
    public static void initApplicationDirs(Context context) {
        /* 初始化目录名称 */
        String APP_PIC_DIR = ContextCompat.getExternalFilesDirs(context, Environment.DIRECTORY_PICTURES)[0].toString();
        CACHE_DIR = ContextCompat.getExternalCacheDirs(HermitCrabApp.getAppContext())[0].toString();
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

    public static String storeBitmapAsPng(Bitmap bitmap, String file_name, @NonNull DIR_TYPE dir_type, int compressQuality) throws IOException {
        File file = createFileIfNull(file_name, dir_type);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return file.getPath();
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
            case CACHE:
                file = new File(CACHE_DIR, fileName);
                if (!file.exists()) {
                    boolean newFile = file.createNewFile();
                }
                break;
            default:
                file = null;
                break;
        }
        return file;
    }

    /**
     * @param src 源文件路径
     * @param des 目的文件路径
     */
    public static void copyFile(String src, String des) {
        copyFile(new File(src), new File(des));
    }

    /**
     * @param src 源文件
     * @param des 目的文件
     */
    public static void copyFile(File src, File des) {
        try {
            FileChannel cSrc = new FileInputStream(src).getChannel();
            FileChannel cDes = new FileOutputStream(des).getChannel();
            cDes.transferFrom(cSrc, 0, cSrc.size());
            cSrc.close();
            cDes.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param src 源文件
     * @param des 目的文件
     */
    public static void copyFile(String src, File des) {
        copyFile(new File(src), des);
    }

    /**
     * @param src 源文件
     * @param des 目的文件
     */
    public static void copyFile(File src, String des) {
        copyFile(src, new File(des));
    }

}
