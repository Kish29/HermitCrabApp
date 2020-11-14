package com.kish2.hermitcrabapp.model;


public interface IUserModel extends IBaseModel {

    void userRegister(String mobile, String code);

    void usernameUpdate(long uid, String username);

    void passwordUpdate(long uid, String password);

    void authByUsername(String username, String password);

    void authByMobile(String mobile, String code);

    void getDozenOfUsers(int num);
}
