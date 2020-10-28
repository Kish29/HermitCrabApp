package com.kish2.hermitcrabapp.bean;

import android.content.Context;
import android.graphics.drawable.VectorDrawable;

import androidx.core.content.ContextCompat;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;

public class HermitCrabVectorIllustrations {

    public static VectorDrawable[] BOTTOM_TABS_ACTIVE;
    public static VectorDrawable[] BOTTOM_TABS_DEFAULT;
    public static VectorDrawable VI_BACK;
    public static VectorDrawable VI_BACK_WHITE;
    public static VectorDrawable VI_USER;
    public static VectorDrawable VI_PASSWORD;
    public static VectorDrawable VI_CLEAR;
    public static VectorDrawable VI_CHECK;
    public static VectorDrawable VI_NOT_CHECK;
    public static VectorDrawable VI_MOBILE;
    public static VectorDrawable VI_VERIFY;
    public static VectorDrawable VI_MENU;
    public static VectorDrawable VI_THEME;
    public static VectorDrawable VI_SETTING;
    public static VectorDrawable VI_ARROW_DOWN;
    public static VectorDrawable VI_CIRCLE;
    public static VectorDrawable VI_MALE;
    public static VectorDrawable VI_FEMALE;

    public static int colorWhite;

    public static void getAIResources(Context context) {
        /* 获取白色 */
        colorWhite = ContextCompat.getColor(context, R.color.white);

        BOTTOM_TABS_ACTIVE = new VectorDrawable[ThemeUtil.BOTTOM_TAB_NUM];
        /* 获取 */
        BOTTOM_TABS_ACTIVE[0] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_home_active);
        BOTTOM_TABS_ACTIVE[1] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_community_active);
        BOTTOM_TABS_ACTIVE[2] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_service_active);
        BOTTOM_TABS_ACTIVE[3] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_message_active);
        BOTTOM_TABS_ACTIVE[4] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_personal_active);
        /* default */
        BOTTOM_TABS_DEFAULT = new VectorDrawable[ThemeUtil.BOTTOM_TAB_NUM];
        BOTTOM_TABS_DEFAULT[0] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_home_default);
        BOTTOM_TABS_DEFAULT[1] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_community_default);
        BOTTOM_TABS_DEFAULT[2] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_service_default);
        BOTTOM_TABS_DEFAULT[3] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_message_default);
        BOTTOM_TABS_DEFAULT[4] = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_personal_default);

        VI_BACK = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_back);
        VI_BACK_WHITE = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_back_white);
        VI_USER = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_user);
        VI_PASSWORD = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_password);
        VI_CLEAR = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_input_clear);
        VI_CHECK = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_check);
        VI_NOT_CHECK = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_not_check);
        VI_VERIFY = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_verify);
        VI_MOBILE = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_mobile);
        VI_MENU = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_menu);
        VI_THEME = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_theme);
        VI_SETTING = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_personal_setting);
        VI_ARROW_DOWN = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_arrow_down);
        VI_CIRCLE = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_circle);
        VI_MALE = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_male);
        VI_FEMALE = (VectorDrawable) ContextCompat.getDrawable(context, R.drawable.ai_female);
    }

    /* 设置颜色 */
    public static void setAIResources(int colorId) {
        for (int i = 0; i < ThemeUtil.BOTTOM_TAB_NUM; i++) {
            BOTTOM_TABS_ACTIVE[i].setTint(colorId);
        }

        VI_BACK.setTint(colorId);
        VI_USER.setTint(colorId);
        VI_PASSWORD.setTint(colorId);
        VI_CLEAR.setTint(colorId);
        VI_CHECK.setTint(colorId);
        VI_NOT_CHECK.setTint(colorId);
        VI_MOBILE.setTint(colorId);
        VI_VERIFY.setTint(colorId);
        VI_ARROW_DOWN.setTint(colorId);
        VI_CIRCLE.setTint(colorId);

        /* 白色部分 */
        VI_MENU.setTint(colorWhite);
        VI_THEME.setTint(colorWhite);
        VI_SETTING.setTint(colorWhite);
    }
}

