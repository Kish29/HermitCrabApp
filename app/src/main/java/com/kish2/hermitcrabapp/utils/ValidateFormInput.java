package com.kish2.hermitcrabapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFormInput {

    private final static String MOBILE_STANDARD = "^1[34578][0-9]{9}";

    private final static String EMAIL_STANDARD = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static boolean isValidMobile(String mobile) {
        Pattern pattern = Pattern.compile(MOBILE_STANDARD);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_STANDARD);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
