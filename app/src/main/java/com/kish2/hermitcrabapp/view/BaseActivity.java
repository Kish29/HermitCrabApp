package com.kish2.hermitcrabapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kish2.hermitcrabapp.utils.StatusBarUtil;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    protected int DEFAULT_COLOR_ID = -1;

    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;

    public void setSinkStatusBar(boolean isRemainPadding, boolean isDarkTheme) {
        setSinkStatusBar(isRemainPadding, isDarkTheme, DEFAULT_COLOR_ID);
    }

    public void setSinkStatusBar(boolean isRemainPadding, boolean isDarkTheme, int colorId) {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, isRemainPadding);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);

        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (isDarkTheme) {
            if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
                //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
                //这样半透明+白=灰, 状态栏的文字能看得清
                StatusBarUtil.setStatusBarColor(this, 0x55000000);
            }
        }
        if (colorId != DEFAULT_COLOR_ID)
            StatusBarUtil.setStatusBarColor(this, getResources().getColor(colorId));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /* 通用方法 */
    // 返回
    public void navigationBack() {
        finish();
    }

    protected enum TOAST_POSITION {
        TOAST_CENTER,
        TOAST_TOP,
        TOAST_BOTTOM,
        TOAST_LEFT,
        TOAST_RIGHT
    }

    protected enum TOAST_DURATION {
        TOAST_SHORT,
        TOAST_LONG
    }

    // 显示toast
    @SuppressLint("ShowToast")
    public void showToast(String msg, TOAST_DURATION duration, TOAST_POSITION position) {
        Toast toast;
        switch (duration) {
            case TOAST_LONG:
                toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
                break;
            default:
                toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
                break;
        }
        switch (position) {
            case TOAST_CENTER:
                toast.setGravity(Gravity.CENTER, 0, 0);
                break;
            case TOAST_TOP:
                toast.setGravity(Gravity.TOP, 0, 0);
                break;
            case TOAST_LEFT:
                toast.setGravity(Gravity.START, 0, 0);
                break;
            case TOAST_RIGHT:
                toast.setGravity(Gravity.END, 0, 0);
                break;
            default:
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                break;
        }
        toast.show();
    }

    // 返回Context对象
    public Context baseActivityGetContext() {
        return this;
    }
}
