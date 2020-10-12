package com.kish2.hermitcrabapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kish2.hermitcrabapp.R;

public class ThemeUtil {

    private static final String KEY_COLOR = "colorId";
    private static final String KEY_BG_IMG = "bgImgSrc";
    private static final String KEY_SIDE_BG_IMG = "sideBgImgSrc";
    private static final String KEY_DARK_THEME = "isDarkTheme";

    public static void setInstance(Context context) {
        SharedPreferences theme_config = context.getSharedPreferences("theme_config", Context.MODE_PRIVATE);
        Theme.colorId = theme_config.getInt(KEY_COLOR, 6);
        /* 如果不是用户用图片作为背景的情况下，设置颜色主题 */
        if (Theme.colorId != -1)
            Theme.colorId = AppTheme[Theme.colorId];
        Theme.bgImgSrc = theme_config.getString(KEY_BG_IMG, null);
        Theme.sideBgImgSrc = theme_config.getString(KEY_SIDE_BG_IMG, null);
        Theme.isDarkTheme = theme_config.getBoolean(KEY_DARK_THEME, false);
    }

    private static int[] AppTheme = {
            R.color.colorPrimary,
            R.color.md_blue_300,
            R.color.pink,
            R.color.miku,
            R.color.md_purple_300,
            R.color.md_cyan_300,
            R.color.md_green_300,
            R.color.md_red_500,
            R.color.now
    };

    public static class Theme {
        /* 主题颜色id，默认为0，如果为-1表示使用图片作为主题 */
        public static int colorId;
        /* 背景图片路径*/
        public static String bgImgSrc;
        /* 左侧边栏图片路径 */
        public static String sideBgImgSrc;
        /* 是否设置了深色主题 */
        public static boolean isDarkTheme;
    }
}
