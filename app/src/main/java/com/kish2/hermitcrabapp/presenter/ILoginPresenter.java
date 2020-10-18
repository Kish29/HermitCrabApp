package com.kish2.hermitcrabapp.presenter;

public interface ILoginPresenter extends IBasePresenter {

    /* 登录按钮事件 */
    public void login();

    public void loginSuccess();

    /* 注册按钮事件 */
    public void register();

    public void rememberUser(boolean isRemember);

    public void forgetPassword();

    public void loginByWeChat();

    public void loginByQQ();
}
