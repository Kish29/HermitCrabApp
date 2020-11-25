package com.kish2.hermitcrabapp.presenter;

/* 同理，BasePresenter必须给予实现约束*/
public interface IBasePresenter {

    /* 初始化数据，设置数据的点击或者其它时间 */
    void initDataAdapter();

    void registerItemEvent();
}
