package com.kish2.hermitcrabapp.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.HermitCrabVectorIllustrations;
import com.kish2.hermitcrabapp.utils.App;
import com.kongzue.dialog.util.TextInfo;

public class ThemeUtil {

    public static final String THEME_CONFIG_FILE_NAME = "theme_config";
    public static final String KEY_COLOR = "colorId";
    public static final String KEY_BG_IMG = "bgImgSrc";
    public static final String KEY_SIDE_BG_IMG = "sideBgImgSrc";
    public static final String KEY_DARK_THEME = "isDarkTheme";

    public static int THEME_ID = 0;

    /* 顶部status bar 的高度，全局使用 */
    public static int STATUS_BAR_HEIGHT = 0;

    public static final int BOTTOM_TAB_NUM = 5;

    public static int TAB_TRANSPARENT = 255;

    /* 主题字体颜色选择器 */
    public static ColorStateList TEXT_SELECTOR;

    /* TabLayout颜色选择器 */
    public static ColorStateList TAB_RIPPLE_SELECTOR;

    /* 底部tab选择器*/
    public static StateListDrawable[] BOTTOM_TAB_SELECTOR;

    /* checkBox 选择器 */
    public static StateListDrawable CHECK_BOX_SELECTOR;

    /* KZDialog的字体颜色 */
    public static TextInfo FONT_COLOR;

    public static void changeTheme(int appThemeColorIndex) {
        THEME_ID = appThemeColorIndex;
        /* 更新颜色资源id */
        Theme.afterGetResourcesColorId = ContextCompat.getColor(App.getAppContext(), AppTheme[appThemeColorIndex]);
        /* 更新图片资源颜色 */
        HermitCrabVectorIllustrations.setAIResources(Theme.afterGetResourcesColorId);
        for (int i = 0; i < BOTTOM_TAB_NUM; i++) {
            /* 普通模式下的颜色*/
            /* 先回收资源 */
            BOTTOM_TAB_SELECTOR[i] = null;
            BOTTOM_TAB_SELECTOR[i] = new StateListDrawable();
            BOTTOM_TAB_SELECTOR[i].addState(new int[]{-android.R.attr.state_selected}, HermitCrabVectorIllustrations.BOTTOM_TABS_DEFAULT[i]);
            /* 添加选中模式的颜色 */
            BOTTOM_TAB_SELECTOR[i].addState(new int[]{android.R.attr.state_selected}, HermitCrabVectorIllustrations.BOTTOM_TABS_ACTIVE[i]);
            BOTTOM_TAB_SELECTOR[i].addState(new int[]{android.R.attr.state_pressed}, HermitCrabVectorIllustrations.BOTTOM_TABS_ACTIVE[i]);
        }
        /* 字体颜色选择器 */
        int[][] state = new int[][]{
                new int[]{-android.R.attr.state_selected}, new int[]{android.R.attr.state_selected}
        };
        int[] color = new int[]{
                ContextCompat.getColor(App.getAppContext(), R.color.cl_text_default),
                ThemeUtil.Theme.afterGetResourcesColorId
        };
        TEXT_SELECTOR = null;
        TEXT_SELECTOR = new ColorStateList(state, color);


        /* TabLayout选择器 */
        state = new int[][]{
                new int[]{-android.R.attr.state_pressed}, new int[]{android.R.attr.state_pressed}
        };
        color = new int[]{
                ContextCompat.getColor(App.getAppContext(), R.color.transparent),
                ThemeUtil.Theme.afterGetResourcesColorId
        };
        TAB_RIPPLE_SELECTOR = null;
        TAB_RIPPLE_SELECTOR = new ColorStateList(state, color);

        /* checkbox*/
        CHECK_BOX_SELECTOR = null;
        CHECK_BOX_SELECTOR = new StateListDrawable();
        CHECK_BOX_SELECTOR.addState(new int[]{android.R.attr.state_checked}, HermitCrabVectorIllustrations.VI_CHECK);
        CHECK_BOX_SELECTOR.addState(new int[]{-android.R.attr.state_checked}, HermitCrabVectorIllustrations.VI_NOT_CHECK);

        /* dialog字体颜色*/
        FONT_COLOR = null;
        FONT_COLOR = new TextInfo();
        FONT_COLOR.setFontColor(Theme.afterGetResourcesColorId);
    }

    public static void setThemeTabSelectors() {
        /* 初始化AI图标类 */
        HermitCrabVectorIllustrations.getAIResources(App.getAppContext());
        HermitCrabVectorIllustrations.setAIResources(Theme.afterGetResourcesColorId);
        // TODO:判断是否已经有banner背景，如果有则不再需要设置颜色了
        HermitCrabVectorIllustrations.setAIColorWhite();
        TAB_TRANSPARENT = App.getAppResources().getInteger(R.integer.tab_transparent);
        /* tab图标Selector */
        BOTTOM_TAB_SELECTOR = new StateListDrawable[BOTTOM_TAB_NUM];
        for (int i = 0; i < BOTTOM_TAB_NUM; i++) {
            /* 普通模式下的颜色*/
            BOTTOM_TAB_SELECTOR[i] = new StateListDrawable();
            BOTTOM_TAB_SELECTOR[i].addState(new int[]{-android.R.attr.state_selected}, HermitCrabVectorIllustrations.BOTTOM_TABS_DEFAULT[i]);
            /* 添加选中模式的颜色 */
            BOTTOM_TAB_SELECTOR[i].addState(new int[]{android.R.attr.state_selected}, HermitCrabVectorIllustrations.BOTTOM_TABS_ACTIVE[i]);
            BOTTOM_TAB_SELECTOR[i].addState(new int[]{android.R.attr.state_pressed}, HermitCrabVectorIllustrations.BOTTOM_TABS_ACTIVE[i]);
        }
        /* 字体颜色选择器 */
        int[][] state = new int[][]{
                new int[]{-android.R.attr.state_selected}, new int[]{android.R.attr.state_selected}
        };
        int[] color = new int[]{
                ContextCompat.getColor(App.getAppContext(), R.color.cl_text_default),
                ThemeUtil.Theme.afterGetResourcesColorId
        };
        TEXT_SELECTOR = new ColorStateList(state, color);

        /* TabLayout选择器 */
        state = new int[][]{
                new int[]{-android.R.attr.state_pressed}, new int[]{android.R.attr.state_pressed}
        };
        color = new int[]{
                ContextCompat.getColor(App.getAppContext(), R.color.transparent),
                ThemeUtil.Theme.afterGetResourcesColorId
        };
        TAB_RIPPLE_SELECTOR = new ColorStateList(state, color);

        /* checkbox*/
        CHECK_BOX_SELECTOR = new StateListDrawable();
        CHECK_BOX_SELECTOR.addState(new int[]{android.R.attr.state_checked}, HermitCrabVectorIllustrations.VI_CHECK);
        CHECK_BOX_SELECTOR.addState(new int[]{-android.R.attr.state_checked}, HermitCrabVectorIllustrations.VI_NOT_CHECK);

        /* dialog字体颜色*/
        FONT_COLOR = new TextInfo();
        FONT_COLOR.setFontColor(Theme.afterGetResourcesColorId);
    }

    public static SharedPreferences theme_config;
    public static SharedPreferences.Editor theme_config_editor;

    @SuppressLint("CommitPrefEdits")
    public static void loadThemeAndColorsVI() {
        theme_config = App.getAppContext().getSharedPreferences(THEME_CONFIG_FILE_NAME, Context.MODE_PRIVATE);
        theme_config_editor = theme_config.edit();
        THEME_ID = Theme.colorId = theme_config.getInt(KEY_COLOR, 0);
        /* 如果不是用户用图片作为背景的情况下，设置颜色主题 */
        Theme.colorId = AppTheme[Theme.colorId];
        Theme.afterGetResourcesColorId = ContextCompat.getColor(App.getAppContext(), Theme.colorId);
        Theme.bgImgSrc = theme_config.getString(KEY_BG_IMG, null);
        Theme.sideBgImgSrc = theme_config.getString(KEY_SIDE_BG_IMG, null);
        Theme.isDarkTheme = theme_config.getBoolean(KEY_DARK_THEME, false);

        /* 获取顶部status bar高度 */
        int identifier = App.getAppResources().getIdentifier("status_bar_height", "dimen", "android");
        STATUS_BAR_HEIGHT = App.getAppResources().getDimensionPixelOffset(identifier);
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
            R.color.snow
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

    /**
     * 判断色调的明暗
     * 只需要获取
     *
     * @param palette 画板
     * @param jude    标准，默认值为0xa
     * @Dominated 主色调，然后进行判断
     * @判断标准 作用域最多的色彩的rgb通道平均值(除0和alpha通道之外的相加)的平均值有没有达到所给标准
     */
    private static int mJudge = 0xa;

    public static boolean isLightTone(Palette palette, int judge) {
        int res = 0;
        int count = 0;
        if (mJudge < judge)
            mJudge = judge;
        if (palette != null) {
            Palette.Swatch dominantSwatch = palette.getDominantSwatch();
            if (dominantSwatch != null) {
                int rgb = dominantSwatch.getRgb();
                String string = Integer.toHexString(rgb);
                string = string.substring(2);
                int length = string.length();
                for (int i = 0; i < length; i++) {
                    int j = string.charAt(i) - '0';
                    if (j != 0) {
                        count++;
                        res += j;
                    }
                }
            }
        }
        return (res / count) >= mJudge;
    }
}
