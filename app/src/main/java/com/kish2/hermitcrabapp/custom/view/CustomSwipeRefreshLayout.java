package com.kish2.hermitcrabapp.custom.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kish2.hermitcrabapp.utils.view.ThemeUtil;

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
    public CustomSwipeRefreshLayout(@NonNull Context context) {
        super(context);
        setColorSchemeColors(ThemeUtil.Theme.afterGetResourcesColorId);
    }

    public CustomSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(ThemeUtil.Theme.afterGetResourcesColorId);
    }
}
