package com.hjh.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 洪锦辉
 * 2022/2/15
 */

public class MD5Util {

    private static final String SALT = "9d5b364d";

    public static String md5(String target) {
        return DigestUtils.md5Hex(target);
    }

    public static String inputPwdToFromPwd(String inputPwd) {
        String tmp = "" + SALT.charAt(0) + SALT.charAt(2) + inputPwd + SALT.charAt(5) + SALT.charAt(4);
        return md5(tmp);
    }

    public static String fromPwdToDBPwd(String fromPwd, String salt) {
        String tmp = "" + salt.charAt(0) + salt.charAt(2) + fromPwd + salt.charAt(5) + salt.charAt(4);
        return md5(tmp);
    }

    public static String inputPwdToDBPwd(String inputPwd, String salt) {
        return fromPwdToDBPwd(inputPwdToFromPwd(inputPwd), salt);
    }

    public static void main(String[] args) {
        System.out.println(inputPwdToFromPwd("123456"));
        System.out.println(inputPwdToDBPwd("123456", "9d5b364d"));
    }
}
