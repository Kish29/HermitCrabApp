package com.kish2.hermitcrabapp.presenter;

public interface IUserPresenter extends IBasePresenter {

    void changeAvatar();

    void updateUsername(String username);

    void updatePassword(String password);

    void updateEmail();

    void updateMobile();

    void updateBindInfo();

    void bindStudentInfo();

    /* 登录按钮事件 */
    void loginByUsername(String username, String password);

    void loginByMobile(String mobile, String checkCode);

    /* 注册按钮事件 */
    void register(String mobile, String checkCode);

    void rememberUser(boolean isRemember);

    void forgetPassword();

    void loginByWeChat();

    void loginByQQ();
}
