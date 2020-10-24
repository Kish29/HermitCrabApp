package com.kish2.hermitcrabapp.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
            /* 设置了title之后，childAt(1)就是当前标题，所以我们需要居中显示 */
            setTileShowAtCenter();
        }
        /*setPopupTheme(R.style.toolbar_menu_popup);*/
    }

    private void setTileShowAtCenter() {
        TextView title = (TextView) getChildAt(1);
        if (title != null) {
            Toolbar.LayoutParams params = new Toolbar.LayoutParams(title.getLayoutParams());
            params.gravity = Gravity.CENTER;
            title.setLayoutParams(params);
            title.setGravity(Gravity.CENTER);
            title.setTypeface(Typeface.SANS_SERIF);
        }
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
