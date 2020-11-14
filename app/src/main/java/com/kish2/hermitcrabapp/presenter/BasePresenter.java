package com.kish2.hermitcrabapp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kish2.hermitcrabapp.HermitCrabApp;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.BaseFragment;

public abstract class BasePresenter<A extends BaseActivity, F extends BaseFragment> implements IBasePresenter, LifecycleObserver {

    protected CustomTipDialog mWaitDialog;

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

    @Override
    public void detachView() {
        activity = null;
        fragment = null;
    }

    public void getDataFromModel() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public abstract void onViewPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public abstract void onViewCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public abstract void onViewResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public abstract void onViewDestroy();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public abstract void onViewStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public abstract void onViewStop();

}
