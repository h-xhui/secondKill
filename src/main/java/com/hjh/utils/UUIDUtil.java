package com.hjh.utils;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author 洪锦辉
 * 2022/2/16
 */

public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
