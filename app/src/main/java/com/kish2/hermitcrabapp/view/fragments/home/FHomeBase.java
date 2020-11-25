package com.kish2.hermitcrabapp.view.fragments.home;

import android.annotation.SuppressLint;

import com.kish2.hermitcrabapp.view.BaseFragment;

public abstract class FHomeBase extends BaseFragment {

    /* HomeFragment的实例，用于操作顶部和底部 */
    @SuppressLint("StaticFieldLeak")
    protected static HomeFragment mHome;

    @Override
    public void initHandler() {

    }
}
