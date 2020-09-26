package com.kish2.hermitcrabapp.view.impl;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.view.LoginView;

@SuppressLint("Registered")
public class LoginViewImpl extends AppCompatActivity implements LoginView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    @Override
    public String getIdentify() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
