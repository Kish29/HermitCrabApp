package com.kish2.hermitcrabapp.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.utils.ThemeUtil;

import java.util.Objects;

public class StatusFixedToolBar extends MaterialToolbar {

    /* 该函数不能再getViewTreeObserver中调用，因为此时布局已经完成，不能再进行更改 */
    private void fixStatusBar() {
        ((MarginLayoutParams) getLayoutParams()).topMargin = ThemeUtil.STATUS_BAR_HEIGHT;
    }

    public void bindAndSetThisToolbar(AppCompatActivity activity, boolean isDarkTheme, String title) {
        fixStatusBar();
        activity.setSupportActionBar(this);
        ActionBar actionBar = activity.getSupportActionBar();
        Objects.requireNonNull(actionBar).setHomeAsUpIndicator(isDarkTheme ? R.drawable.ai_action_back_white : R.drawable.ai_action_back_black);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(title != null);
        setPopupTheme(R.style.toolbar_menu_popup);
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
