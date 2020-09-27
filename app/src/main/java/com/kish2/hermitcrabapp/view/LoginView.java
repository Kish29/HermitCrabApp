package com.kish2.hermitcrabapp.view;

public interface LoginView extends BaseView {

    /* presenter调用方法 */
    public String getIdentify();

    public String getPassword();

    /* 需要调用presenter的方法 */
    public void login();

    public void register();

    public void forgetPassword();

    public void rememberUser();

    public void loginByWeChat();

    public void loginByQQ();

    /* 自己本地方法 */
    public void pullOtherLoginView();

    public boolean isValidIdentify(String identify);

    public boolean isValidPassword(String password);
}
