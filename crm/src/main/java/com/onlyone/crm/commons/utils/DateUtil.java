package com.onlyone.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author 临渊
 * @Date 2023-11-25 9:10
 */
public class DateUtil {

    public static String formatDateTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
