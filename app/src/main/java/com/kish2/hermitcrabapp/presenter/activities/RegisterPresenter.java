package com.kish2.hermitcrabapp.presenter.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.kish2.hermitcrabapp.presenter.ILoginPresenter;
import com.kish2.hermitcrabapp.view.activities.LoginActivity;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.activities.RegisterActivity;

public class RegisterPresenter implements ILoginPresenter {

    private RegisterActivity mRegisterView;
    public static final int REGISTER_SUCCESS = 100;
    public static final int REGISTER_FAILURE = 99;
    private static int REGISTER_STATUS;

    public RegisterPresenter(RegisterActivity activity) {
        this.mRegisterView = activity;
    }

    @Override
    public void login() {

    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void register() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = REGISTER_SUCCESS;
                REGISTER_STATUS = REGISTER_SUCCESS;
                if (mRegisterView != null)
                    mRegisterView.mHandler.sendMessage(message);
            }
        }.start();
    }

    @Override
    public void registerSuccess() {
        new Handler().postDelayed(() -> {
            if (mRegisterView != null) {
                mRegisterView.startActivity(new Intent(mRegisterView, MainActivity.class));
                mRegisterView.finish();
            }
        }, 500);
    }

    @Override
    public void rememberUser(boolean isRemember) {

    }

    @Override
    public void forgetPassword() {

    }

    @Override
    public void loginByWeChat() {

    }

    @Override
    public void loginByQQ() {

    }

    @Override
    public void detachView() {
        this.mRegisterView = null;
    }
}
