package com.kish2.hermitcrabapp.view;

import android.content.Context;

/* BaseFragment 必须给子类给予约束 */
public interface BaseView {

    /* 初始化视图 */
    public void initView();

    /* 设置顶部状态栏 */
    public void setStatusBar();

    /* 初始化presenter */
    public void attachPresenter();

    /* 必须调用，detach连接的presenter，调用presenter的detachView方法*/
    public void detachPresenter();

    /* 通用方法 */
    // 返回
    public void navigationBack();

    // 显示toast
    public void showToast(String msg);

    // 返回Context对象
    public Context getContext();
}
