package com.kish2.hermitcrabapp.utils;

import com.kish2.hermitcrabapp.bean.User;

/* 此Bean用来保存用户在内存中的信息，以提高运行时速度 */
public class UserInfoUtil {

    public static User USER;

    public static boolean LOAD_USER_SUCCESS = false;

    public static void loadUserInfo() {
        USER = new User();
        USER.setUsername("kish2");
        USER.setGender(User.GENDER.MALE);
        USER.setEmail("875691208@qq.com");
        USER.setDepartment("计算机与通信工程学院");
        USER.setMobile("13658398509");
        USER.setGrade("2018级");
        USER.setStudentId("41824233");
        LOAD_USER_SUCCESS = true;
        App.IS_USER_LOG_IN = true;
    }
}
