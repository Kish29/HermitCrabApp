package com.kish2.hermitcrabapp.view;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kish2.hermitcrabapp.custom.view.CustomWaitDialog;
import com.kish2.hermitcrabapp.utils.view.KZDialogUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;


public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    protected int DEFAULT_COLOR_ID = -1;

    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;

    /* 提供给Presenter使用 */
    public Handler mHandler;

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
