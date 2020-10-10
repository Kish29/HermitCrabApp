package com.kish2.hermitcrabapp.presenter;

public interface LoginPresenter extends IBasePresenter {

    /* 登录按钮事件 */
    public void login();

    /* 注册按钮事件 */
    public void register();

    public void rememberUser(boolean isRemember);

    public void forgetPassword();

    public void loginByWeChat();

    public void loginByQQ();
}
