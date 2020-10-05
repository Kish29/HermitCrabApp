package com.kish2.hermitcrabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.IBaseView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HermitCrabSplash extends BaseActivity implements IBaseView {

    @BindView(R.id.splash_picture)
    View splashPicture;

    private static final long DELAY = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        ButterKnife.bind(this);

        /* intent必须为final */
        final Intent intent = new Intent(this, MainActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, DELAY);
    }

    @Override
    public void initView() {

    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
