package com.kish2.hermitcrabapp.view;

/* 视图层控制，显示/获取内容 */
public interface UserView extends IBaseView {

    public String getUsername();

    public String getPassword();

    public String getVerifyCode();
}
