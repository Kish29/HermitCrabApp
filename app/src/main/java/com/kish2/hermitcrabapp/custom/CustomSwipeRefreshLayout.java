package com.kish2.hermitcrabapp.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kish2.hermitcrabapp.intfc.ThemeManagerApiKt;

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
    public CustomSwipeRefreshLayout(@NonNull Context context) {
        super(context);
        setColorSchemeColors(ThemeManagerApiKt.getThemeManager().getThemeColor());
    }

    public CustomSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(ThemeManagerApiKt.getThemeManager().getThemeColor());
    }
}
