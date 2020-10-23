package com.kish2.hermitcrabapp.view;

import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    protected int DEFAULT_COLOR_ID = -1;

    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;

    /* 提供给Presenter使用 */
    public Handler mHandler;
}
