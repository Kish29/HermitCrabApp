package com.kish2.hermitcrabapp.presenter;

public interface ILoginPresenter {

    /* 登录按钮事件 */
    public void loginByUsername(String username, String password);

    void loginByMobile(String mobile, String checkCode);

    /* 注册按钮事件 */
    public void register(String mobile, String checkCode);

    public void rememberUser(boolean isRemember);

    public void forgetPassword();

    public void loginByWeChat();

    public void loginByQQ();
}
