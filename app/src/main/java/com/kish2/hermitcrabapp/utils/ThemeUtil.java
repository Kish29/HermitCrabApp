package com.kish2.hermitcrabapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.kish2.hermitcrabapp.R;

import java.util.Objects;

public class ThemeUtil {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    public static ColorStateList setThemeTabSelectors(Context context) {
        Objects.requireNonNull(context.getDrawable(R.drawable.ic_home_active)).setTint(ThemeUtil.Theme.afterGetResourcesColorId);
        Objects.requireNonNull(context.getDrawable(R.drawable.ic_community_active)).setTint(ThemeUtil.Theme.afterGetResourcesColorId);
        Objects.requireNonNull(context.getDrawable(R.drawable.ic_service_active)).setTint(ThemeUtil.Theme.afterGetResourcesColorId);
        Objects.requireNonNull(context.getDrawable(R.drawable.ic_message_active)).setTint(ThemeUtil.Theme.afterGetResourcesColorId);
        Objects.requireNonNull(context.getDrawable(R.drawable.ic_personal_active)).setTint(ThemeUtil.Theme.afterGetResourcesColorId);

        int[][] state = new int[][]{
                new int[]{-android.R.attr.state_selected}, new int[]{android.R.attr.state_selected}
        };
        int[] color = new int[]{
                context.getResources().getColor(R.color.cl_text_default),
                ThemeUtil.Theme.afterGetResourcesColorId
        };

        return new ColorStateList(state, color);
    }

    private static final String KEY_COLOR = "colorId";
    private static final String KEY_BG_IMG = "bgImgSrc";
    private static final String KEY_SIDE_BG_IMG = "sideBgImgSrc";
    private static final String KEY_DARK_THEME = "isDarkTheme";

    /* 顶部status bar 的高度，全局使用 */
    public static int STATUS_BAR_HEIGHT = 0;

    public static void setInstance(Context context) {
        SharedPreferences theme_config = context.getSharedPreferences("theme_config", Context.MODE_PRIVATE);
        Theme.colorId = theme_config.getInt(KEY_COLOR, 6);
        /* 如果不是用户用图片作为背景的情况下，设置颜色主题 */
        if (Theme.colorId != -1) {
            Theme.colorId = AppTheme[Theme.colorId];
            Theme.afterGetResourcesColorId = context.getResources().getColor(Theme.colorId);
        }
        Theme.bgImgSrc = theme_config.getString(KEY_BG_IMG, null);
        Theme.sideBgImgSrc = theme_config.getString(KEY_SIDE_BG_IMG, null);
        Theme.isDarkTheme = theme_config.getBoolean(KEY_DARK_THEME, false);
        /* 获取顶部status bar高度 */
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelOffset(identifier);
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
        public static int afterGetResourcesColorId;
        /* 背景图片路径*/
        public static String bgImgSrc;
        /* 左侧边栏图片路径 */
        public static String sideBgImgSrc;
        /* 是否设置了深色主题 */
        public static boolean isDarkTheme;
    }
}
