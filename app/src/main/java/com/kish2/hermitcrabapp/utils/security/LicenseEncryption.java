package com.kish2.hermitcrabapp.utils.security;

import cn.hutool.crypto.SecureUtil;

public class LicenseEncryption {

    public static String passwordEncryption(String origin) {
        return SecureUtil.sha256(SecureUtil.md5(origin));
    }
}
