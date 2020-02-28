package com.example.demo.utils;

import java.util.UUID;

/**
 * UUIDUtil
 * 全局唯一标识符
 *
 * @author maco
 * @data 2019/10/28
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
