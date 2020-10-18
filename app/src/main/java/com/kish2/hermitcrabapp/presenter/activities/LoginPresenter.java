package com.kish2.hermitcrabapp.presenter.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.kish2.hermitcrabapp.view.activities.LoginActivity;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.presenter.ILoginPresenter;

public class LoginPresenter implements ILoginPresenter {

    private LoginActivity mLoginView;
    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAILURE = 1;
    private static int LOGIN_STATUS;

    public LoginPresenter(LoginActivity loginActivity) {
        this.mLoginView = loginActivity;
    }

    @Override
    public void detachView() {
        /* 让CG回收内存 */
        this.mLoginView = null;
    }

    @Override
    public void login() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = LOGIN_SUCCESS;
                LOGIN_STATUS = LOGIN_SUCCESS;
                mLoginView.mHandler.sendMessage(message);
            }
        }.start();
    }

    @Override
    public void loginSuccess() {
        new Handler().postDelayed(() -> mLoginView.startActivity(new Intent(mLoginView, MainActivity.class)), 500);
    }

    @Override
    public void register() {
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
}
