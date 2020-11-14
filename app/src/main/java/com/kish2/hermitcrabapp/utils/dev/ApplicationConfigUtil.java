package com.kish2.hermitcrabapp.utils.dev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.kish2.hermitcrabapp.HermitCrabApp;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;

/* 该类通过App的子线程，将应用配置存储在手机本地，需要用到的时候进行读取 */
public class ApplicationConfigUtil {

    private static SharedPreferences mAppConfig;

    private static final String APP_CONFIG_NAME = "app_config";
    public static final String KEY_HAD_LOGIN = "had_login";
    public static boolean HAD_LOGIN = false;
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USER_TOKEN = "user_token";
    public static final String KEY_HAS_AVATAR = "has_avatar";
    public static boolean HAS_AVATAR = false;
    public static final String KEY_HAS_BANNER_BKG = "has_banner_bkg";
    public static final String KEY_ORIGIN_BANNER_BKG_URI = "origin_banner_bkg_uri";
    public static int sample_radius = 25;
    public static int sample_value = 1;
    public static Uri ORIGIN_BANNER_BKG_URI = null;
    public static boolean HAS_BANNER_BKG = false;
    /* 使用系统默认颜色作为背景 */
    public static boolean BANNER_DEFAULT = true;
    public static final String KEY_BANNER_DEFAULT = "user_sys_default";
    public static final String KEY_HAS_SIDE_MENU_BKG = "has_side_bkg";
    public static boolean HAS_SIDE_MENU_BKG = false;


    public static Bitmap USER_AVATAR;
    public static Bitmap USER_BANNER_BKG;
    public static Bitmap APP_SIDE_MENU_BKG;

    public static void getConfigAndSetEditor(Context context) {
        mAppConfig = context.getSharedPreferences(APP_CONFIG_NAME, Context.MODE_PRIVATE);
        HAD_LOGIN = mAppConfig.getBoolean(KEY_HAD_LOGIN, false);
        HAS_AVATAR = mAppConfig.getBoolean(KEY_HAS_AVATAR, false);
        HAS_BANNER_BKG = mAppConfig.getBoolean(KEY_HAS_BANNER_BKG, false);
        HAS_SIDE_MENU_BKG = mAppConfig.getBoolean(KEY_HAS_SIDE_MENU_BKG, false);
        BANNER_DEFAULT = mAppConfig.getBoolean(KEY_BANNER_DEFAULT, true);
        String string = mAppConfig.getString(KEY_ORIGIN_BANNER_BKG_URI, null);
        if (string != null)
            ORIGIN_BANNER_BKG_URI = Uri.parse(string);
        sample_radius = mAppConfig.getInt("sample_radius", 25);
        sample_value = mAppConfig.getInt("sample_value", 1);
    }

    public static void loadLocalAppData() {
        if (HAD_LOGIN) {
            HermitCrabApp.USER.setUsername(mAppConfig.getString(KEY_USERNAME, null));
            HermitCrabApp.USER.setToken(mAppConfig.getString(KEY_USER_TOKEN, null));
        }
        if (HAS_AVATAR) {
            USER_AVATAR = BitmapFactory.decodeFile(FileStorageManager.getUserAvatarPath());
        }
        if (HAS_BANNER_BKG)
            USER_BANNER_BKG = BitmapFactory.decodeFile(FileStorageManager.getUserBannerBkgPath());
        if (HAS_SIDE_MENU_BKG) {
            APP_SIDE_MENU_BKG = BitmapFactory.decodeFile(FileStorageManager.getAppSideMenuBkgPath());
        }
    }

    @SuppressLint("CommitPrefEdits")
    public static void storeAppConfig() {
        /* 应用部分*/
        SharedPreferences.Editor mAppConfigEditor = mAppConfig.edit();
        mAppConfigEditor.putBoolean(KEY_HAD_LOGIN, HAD_LOGIN);
        mAppConfigEditor.putBoolean(KEY_HAS_AVATAR, HAS_AVATAR);
        mAppConfigEditor.putBoolean(KEY_HAS_BANNER_BKG, HAS_BANNER_BKG);
        mAppConfigEditor.putBoolean(KEY_HAS_SIDE_MENU_BKG, HAS_SIDE_MENU_BKG);
        mAppConfigEditor.putBoolean(KEY_BANNER_DEFAULT, BANNER_DEFAULT);
        if (ORIGIN_BANNER_BKG_URI != null)
            mAppConfigEditor.putString(KEY_ORIGIN_BANNER_BKG_URI, ORIGIN_BANNER_BKG_URI.toString());
        mAppConfigEditor.putInt("sample_radius", sample_radius);
        mAppConfigEditor.putInt("sample_value", sample_value);

        /* 主题部分 */
        ThemeUtil.theme_config_editor.putInt(ThemeUtil.KEY_COLOR, ThemeUtil.THEME_ID);

        mAppConfigEditor.apply();
        ThemeUtil.theme_config_editor.apply();
    }

}
