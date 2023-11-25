package com.onlyone.crm.commons.utils;

import java.util.UUID;

/**
 * @Author 临渊
 * @Date 2023-11-25 17:12
 */
public class UUIDUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
