package com.example.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5Utils
 *
 * @author maco
 * @data 2019/10/25
 */
public class MD5Utils {
    public static String md5(String key) {
        return DigestUtils.md5Hex(key);
    }

    public static final String salt = "1a2b3c4d";

    /**
     * 密码加盐、md5
     *
     * @param inputPass
     * @return
     */
    public static String inputPassFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次md5　salt　可随机
     *
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }
}
