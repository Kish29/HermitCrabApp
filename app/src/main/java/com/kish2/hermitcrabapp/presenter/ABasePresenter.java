package com.kish2.hermitcrabapp.presenter;

import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.kish2.hermitcrabapp.view.BaseActivity;

public abstract class ABasePresenter<T extends BaseActivity> extends BasePresenter {

    protected Intent intent;

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public abstract void onActivityPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public abstract void onActivityCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public abstract void onActivityResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public abstract void onActivityDestroy();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public abstract void onActivityStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public abstract void onActivityStop();
}
