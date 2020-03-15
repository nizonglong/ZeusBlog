package com.nzl.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @classname: DateTimeUtil
 * @description:
 * @author: nizonglong
 * @date: 2019/9/18 21:29
 * @version: 1.0
 */
public class DateTimeUtil {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Date strToDate(String dataTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dataTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    public static Date strToDateTime(String dataTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATETIME_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStrDateTime(Date date) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DATETIME_FORMAT);
    }

    public static String dateToStrDate(Date date) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DATE_FORMAT);
    }
}
