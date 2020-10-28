package com.kish2.hermitcrabapp.presenter;

public interface UserPresenter {

    void changeAvatar();

    void updateUsername(String username);

    void updatePassword(String password);

    void updateEmail();

    void updateMobile();

    void updateBindInfo();

    void bindStudentInfo();
}
