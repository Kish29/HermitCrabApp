package com.kish2.hermitcrabapp.presenter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;


public abstract class FBasePresenter<V> extends BasePresenter {
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public abstract void onFragmentPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public abstract void onFragmentCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public abstract void onFragmentResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public abstract void onFragmentDestroy();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public abstract void onFragmentStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public abstract void onFragmentStop();
}
