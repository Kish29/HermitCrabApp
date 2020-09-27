package com.kish2.hermitcrabapp.view;

/* BaseView 必须给子类给予约束 */
public interface BaseView {

    /* 初始化presenter */
    public void attachPresenter();

    /* 必须调用，detach连接的presenter，调用presenter的detachView方法*/
    public void detachPresenter();

    /* 通用方法 */
    // 返回
    public void navigationBack();

    public void showToast(String msg);
}
