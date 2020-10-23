package com.kish2.hermitcrabapp.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.StateListDrawable;

import androidx.core.content.ContextCompat;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.VectorIllustrations;

public class ThemeUtil {
    public static final int BOTTOM_TAB_NUM = 5;

    /* 主题字体颜色选择器 */
    public static ColorStateList TEXT_SELECTOR;

    /* TabLayout颜色选择器 */
    public static ColorStateList TAB_RIPPLE_SELECTOR;

    /* 底部tab选择器*/
    public static StateListDrawable[] BOTTOM_TAB_SELECTOR;

    /* checkBox 选择器 */
    public static StateListDrawable CHECK_BOX_SELECTOR;

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    public static void setThemeTabSelectors(Context context) {
        /* 初始化AI图标类 */
        VectorIllustrations.getAIResources(context);
        VectorIllustrations.setAIResources(Theme.afterGetResourcesColorId);
        /* tab图标Selector */
        BOTTOM_TAB_SELECTOR = new StateListDrawable[BOTTOM_TAB_NUM];
        for (int i = 0; i < BOTTOM_TAB_NUM; i++) {
            /* 普通模式下的颜色*/
            BOTTOM_TAB_SELECTOR[i] = new StateListDrawable();
            BOTTOM_TAB_SELECTOR[i].addState(new int[]{-android.R.attr.state_selected}, VectorIllustrations.BOTTOM_TABS_DEFAULT[i]);
            /* 添加选中模式的颜色 */
            BOTTOM_TAB_SELECTOR[i].addState(new int[]{android.R.attr.state_selected}, VectorIllustrations.BOTTOM_TABS_ACTIVE[i]);
            BOTTOM_TAB_SELECTOR[i].addState(new int[]{android.R.attr.state_pressed}, VectorIllustrations.BOTTOM_TABS_ACTIVE[i]);
        }
        /* 字体颜色选择器 */
        int[][] state = new int[][]{
                new int[]{-android.R.attr.state_selected}, new int[]{android.R.attr.state_selected}
        };
        int[] color = new int[]{
                ContextCompat.getColor(context, R.color.cl_text_default),
                ThemeUtil.Theme.afterGetResourcesColorId
        };
        TEXT_SELECTOR = new ColorStateList(state, color);

        /* TabLayout选择器 */
        state = new int[][]{
                new int[]{-android.R.attr.state_pressed}, new int[]{android.R.attr.state_pressed}
        };
        color = new int[]{
                ContextCompat.getColor(context, R.color.transparent),
                ThemeUtil.Theme.afterGetResourcesColorId
        };
        TAB_RIPPLE_SELECTOR = new ColorStateList(state, color);

        /* checkbox*/
        CHECK_BOX_SELECTOR = new StateListDrawable();
        CHECK_BOX_SELECTOR.addState(new int[]{android.R.attr.state_checked}, VectorIllustrations.VI_CHECK);
        CHECK_BOX_SELECTOR.addState(new int[]{-android.R.attr.state_checked}, VectorIllustrations.VI_NOT_CHECK);
    }

    public static final String THEME_CONFIG_FILE_NAME = "theme_config";
    public static final String KEY_COLOR = "colorId";
    public static final String KEY_BG_IMG = "bgImgSrc";
    public static final String KEY_SIDE_BG_IMG = "sideBgImgSrc";
    public static final String KEY_DARK_THEME = "isDarkTheme";

    public static int THEME_ID = 0;

    /* 顶部status bar 的高度，全局使用 */
    public static int STATUS_BAR_HEIGHT = 0;

    public static void setInstance(Context context) {
        SharedPreferences theme_config = context.getSharedPreferences(THEME_CONFIG_FILE_NAME, Context.MODE_PRIVATE);
        THEME_ID = Theme.colorId = theme_config.getInt(KEY_COLOR, 6);
        /* 如果不是用户用图片作为背景的情况下，设置颜色主题 */
        Theme.colorId = AppTheme[Theme.colorId];
        Theme.afterGetResourcesColorId = ContextCompat.getColor(context, Theme.colorId);
        Theme.bgImgSrc = theme_config.getString(KEY_BG_IMG, null);
        Theme.sideBgImgSrc = theme_config.getString(KEY_SIDE_BG_IMG, null);
        Theme.isDarkTheme = theme_config.getBoolean(KEY_DARK_THEME, false);

        /* 获取顶部status bar高度 */
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelOffset(identifier);
    }

    public static int[] AppTheme = {
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
