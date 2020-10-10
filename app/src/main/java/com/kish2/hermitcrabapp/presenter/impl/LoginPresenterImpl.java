package com.kish2.hermitcrabapp.presenter.impl;

import android.content.Intent;
import android.util.Log;

import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.presenter.LoginPresenter;
import com.kish2.hermitcrabapp.view.activities.LoginViewImpl;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginViewImpl loginView;

    public LoginPresenterImpl(LoginViewImpl loginView) {
        this.loginView = loginView;
    }

    private static final String TAG = "login presenter";

    @Override
    public void detachView() {
        /* 让CG回收内存 */
        this.loginView = null;
        Log.d(TAG, "detachView and set view=null");
    }

    @Override
    public void login() {
        Log.d(TAG, "login method");
        Log.d(TAG, loginView.getIdentify() + "\tpwd->" + loginView.getPassword());
        loginView.startActivity(new Intent(loginView.baseActivityGetContext(), MainActivity.class));
    }

    @Override
    public void register() {
        Log.d(TAG, "register method");
    }

    @Override
    public void rememberUser(boolean isRemember) {
        Log.d(TAG, "rememberUser method");
        Log.d(TAG, String.valueOf(isRemember));
    }

    @Override
    public void forgetPassword() {
        Log.d(TAG, "forgetPassword method");
    }

    @Override
    public void loginByWeChat() {
        Log.d(TAG, "loginByWeChat method");
    }

    @Override
    public void loginByQQ() {
        Log.d(TAG, "loginByQQ method");
    }
}
