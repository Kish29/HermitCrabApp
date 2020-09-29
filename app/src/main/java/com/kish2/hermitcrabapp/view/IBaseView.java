package com.kish2.hermitcrabapp.view;

import android.content.Context;

/* IBaseFragment 必须给子类给予约束 */
public interface IBaseView {

    /* 初始化视图 */
    public void initView();

    /* 初始化presenter */
    public void attachPresenter();

    /* 必须调用，detach连接的presenter，调用presenter的detachView方法*/
    public void detachPresenter();

}
