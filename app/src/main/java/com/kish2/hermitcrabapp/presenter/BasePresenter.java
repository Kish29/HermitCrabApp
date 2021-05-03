package com.kish2.hermitcrabapp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.BaseFragment;

public abstract class BasePresenter<A extends BaseActivity, F extends BaseFragment> implements IBasePresenter, LifecycleObserver {

    protected CustomTipDialog mWaitDialog;

    protected Handler handler;
    protected Intent intent;

    protected A activity;
    protected F fragment;

    public void bindView(A activity) {
        this.activity = null;
        this.activity = activity;
    }

    public void bindView(F fragment) {
        this.fragment = null;
        this.fragment = fragment;
    }

    public void bindView(A activity, F fragment) {
        this.activity = null;
        this.fragment = null;
        this.activity = activity;
        this.fragment = fragment;
    }

    public Activity getActivity() {
        if (activity != null)
            return activity;
        else return fragment.requireActivity();
    }

    public boolean viewExist() {
        return activity != null || fragment != null;
    }

    public Context getContext() {
        if (this.activity != null)
            return this.activity;
        else if (this.fragment != null)
            return this.fragment.requireContext();
        else return HermitCrabApp.getAppContext();
    }

    public Resources getResources() {
        if (this.activity != null)
            return this.activity.getResources();
        else if (this.fragment != null)
            return this.fragment.getResources();
        else return HermitCrabApp.getAppResources();
    }

    public void getDataFromModel() {

    }

    public abstract void initHandler();


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onViewPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onViewCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onViewResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onViewDestroy() {
        activity = null;
        fragment = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onViewStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onViewStop() {
    }

}
