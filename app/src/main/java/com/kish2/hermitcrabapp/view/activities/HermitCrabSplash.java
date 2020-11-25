package com.kish2.hermitcrabapp.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.HermitCrabBitMaps;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HermitCrabSplash extends BaseActivity {

    @BindView(R.id.iv_splash_picture)
    ImageView mSplashPicture;

    private static final long DELAY = 500;

    @Override
    protected void themeChanged() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        ButterKnife.bind(this);
        /* 获取主题设置 */
        getAndSetLayoutView();
        registerViewComponentsAffairs();
        /* 子线程获取布局参数 */
        new Thread() {
            @Override
            public void run() {
                /* 资源也应当在子线程中设置 */
                ThemeUtil.loadThemeAndColorsVI();
                ThemeUtil.setThemeTabSelectors();
                HermitCrabBitMaps.loadBitMaps();
                loadData();
                mHandler.sendEmptyMessage(MessageForHandler.LOCAL_DATA_LOADED);
            }
        }.start();
    }

    @Override
    public void getLayoutComponentsAttr() {
    }

    @Override
    public void getAndSetLayoutView() {
        Glide.with(this).load(R.drawable.background).into(mSplashPicture);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void registerViewComponentsAffairs() {

    }

    @Override
    public void attachPresenter() {

    }

    @SuppressLint("HandlerLeak")
    @Override
    public void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.LOCAL_DATA_LOADED:
                        postDelayed(() -> {
                            startActivity(new Intent(HermitCrabSplash.this, MainActivity.class));
                            finish();
                        }, DELAY);
                        break;
                    case MessageForHandler.ADAPTER_INIT:
                    case MessageForHandler.SYSTEM_FAILURE:
                    default:
                        break;
                }
            }
        };
    }
}
