package com.kish2.hermitcrabapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.Iterator;


public class BaseActivity extends AppCompatActivity {

    protected int DEFAULT_COLOR_ID = -1;

    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;

    public void setSinkStatusBar(boolean remainPadding, boolean isDarkTheme) {
        setSinkStatusBar(remainPadding, isDarkTheme, DEFAULT_COLOR_ID);
    }

    public void setSinkStatusBar(boolean remainPadding, boolean isDarkTheme, int colorId) {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, remainPadding);
        //设置状态栏透明
        if (!remainPadding)
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
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.transparent));
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

    // 返回Context对象
    public Context baseActivityGetContext() {
        return this;
    }

    /* 检查并申请权限 */
    public void checkAndRequestPermissions(ArrayList<String> permissionList) {
        ArrayList<String> list = new ArrayList<>(permissionList);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String permission = it.next();
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                it.remove();
            }
        }
        /* 所有权限均通过 */
        if (list.size() == 0) {
            return;
        }
        /* 先转化为String数组*/
        String[] permissions = list.toArray(new String[0]);
        ActivityCompat.requestPermissions(this, permissions, 3000);
    }
}
