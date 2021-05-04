package com.kish2.hermitcrabapp.model.api;


public class Api {
//    public static final String API_BASE = "http://hermitcrab.free.idcfengye.com/api/";
//    public static final String API_BASE = "http://10.39.230.84:9999/api/";
    public static final String API_BASE = "http://hkkiack8.xiaomy.net:32771/api/";

    private static final String USER_API = "userApi/";

    public static final String API_USER_REG = USER_API + "reg";

    public static final String API_USERNAME_UPDATE = USER_API + "update/username";

    public static final String API_PASSWORD_UPDATE = USER_API + "update/password";

    public static final String API_AUTH_BY_USERNAME = USER_API + "authentication/usrnm_psswd";

    public static final String API_AUTH_BY_MOBILE_CODE = USER_API + "authentication/mobile_code";

    public static final String API_VERIFY_CODE = "verifyCode";
}

