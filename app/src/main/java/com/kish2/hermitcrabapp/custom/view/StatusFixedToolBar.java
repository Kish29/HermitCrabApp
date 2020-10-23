package com.kish2.hermitcrabapp.custom.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.kish2.hermitcrabapp.bean.VectorIllustrations;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;

public class StatusFixedToolBar extends MaterialToolbar {

    /* 该函数不能再getViewTreeObserver中调用，因为此时布局已经完成，不能再进行更改 */
    private void fixStatusBar() {
        ((MarginLayoutParams) getLayoutParams()).topMargin = ThemeUtil.STATUS_BAR_HEIGHT;
    }

    public void bindAndSetThisToolbar(AppCompatActivity activity, boolean setColor, String title) {
        fixStatusBar();
        if (setColor) {
            setNavigationIcon(VectorIllustrations.VI_BACK_WHITE);
            setBackgroundColor(ThemeUtil.Theme.afterGetResourcesColorId);
        } else {
            setNavigationIcon(VectorIllustrations.VI_BACK);
        }

        if (title != null) {
            setTitle(title);
            setTitleTextColor(setColor ? VectorIllustrations.colorWhite : ThemeUtil.Theme.afterGetResourcesColorId);
        }
        /*setPopupTheme(R.style.toolbar_menu_popup);*/
    }

    public StatusFixedToolBar(@NonNull Context context) {
        super(context);
    }

    public StatusFixedToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusFixedToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
