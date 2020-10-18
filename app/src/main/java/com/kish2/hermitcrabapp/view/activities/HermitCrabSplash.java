package com.kish2.hermitcrabapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.utils.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.http2.Http2Reader;

public class HermitCrabSplash extends BaseActivity {

    @BindView(R.id.iv_splash_picture)
    ImageView mSplashPicture;

    private static final long DELAY = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        ButterKnife.bind(this);
        /* 获取主题设置 */
        ThemeUtil.setInstance(this);
        getAndSetLayoutView();
        /* 子线程获取布局参数 */
        new Thread() {
            @Override
            public void run() {
                /* 资源也应当在子线程中设置 */
                registerViewComponentsAffairs();
                loadData();
            }
        }.start();
        /* intent必须为final */
        new Handler().postDelayed(() -> {
            startActivity(new Intent(HermitCrabSplash.this, MainActivity.class));
            finish();
        }, DELAY);
    }

    @Override
    public void getLayoutComponentsAttr() {
    }

    @Override
    public void getAndSetLayoutView() {
        mSplashPicture.setImageResource(R.drawable.background);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void registerViewComponentsAffairs() {

    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
