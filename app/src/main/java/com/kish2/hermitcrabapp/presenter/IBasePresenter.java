package com.kish2.hermitcrabapp.presenter;

/* 同理，BasePresenter必须给予实现约束*/
public interface IBasePresenter {

    public void detachView();

    void onServerError(Object object);

    void onServerSuccess(Object object);
}
