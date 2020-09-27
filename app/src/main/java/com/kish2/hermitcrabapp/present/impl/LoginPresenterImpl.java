package com.kish2.hermitcrabapp.present.impl;

import com.kish2.hermitcrabapp.present.LoginPresenter;
import com.kish2.hermitcrabapp.view.LoginView;
import com.kish2.hermitcrabapp.view.impl.LoginViewImpl;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginViewImpl loginView;

    public LoginPresenterImpl(LoginViewImpl loginView) {
        this.loginView = loginView;
    }


    @Override
    public void detachView() {
        /* 让CG回收内存 */
        this.loginView = null;
    }
}
