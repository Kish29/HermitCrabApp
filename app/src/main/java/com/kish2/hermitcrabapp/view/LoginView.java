package com.kish2.hermitcrabapp.view;

public interface LoginView extends IBaseView {

    /* presenter调用方法 */
    public String getIdentify();

    public String getPassword();

    /* 自己本地方法 */
    public void pullOtherLoginView();
}
