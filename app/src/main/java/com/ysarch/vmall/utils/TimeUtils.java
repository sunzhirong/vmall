package com.ysarch.vmall.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期实用类
 *
 * @author Darcy https://yedaxia.github.io/
 * @version 2017/4/6.
 */

public class TimeUtils {

    /**
     * 把日期显示成DateTime格式，yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
    }

    /**
     * 把日期显示成Date格式，yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
    }


    /**
     * 把日期显示成Date格式，yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatCustomDate(Date date, String formatDate) {
        if (formatDate == null || formatDate.length() == 0) {
            formatDate = "yyyy-MM-dd-hhmmss";
        }
        return new SimpleDateFormat(formatDate, Locale.CHINA).format(date);
    }


    public static String getTimerString(long milliseconds) {
        return getTimerString(milliseconds, true);
    }

    public static String getTimerString(long milliseconds, boolean showSeconds) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;


        long hour = (milliseconds) / hh;
        long minute = (milliseconds - hour * hh) / mi;
        long second = (milliseconds - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();

        if (hour > 0) {
            sb.append(hour + ":");
        }
        if ((hour > 0 && minute == 0) || minute > 0) {
            sb.append(minute);
        }
        if (showSeconds && ((minute > 0 && second == 0) || second >= 0)) {
            sb.append(":");
            sb.append(second);
        }

        return sb.toString();
    }


    /**
     * 功能描述：返回日
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 将 yyyy-MM-dd 格式的日期转为Date数据
     *
     * @param str
     * @return
     */
    public static Date stringToDate(String str) {
        return stringToDate(str, "yyyy-MM-dd");
    }




    /**
     * 将字符串日期转为
     *
     * @param str
     * @param dataFormat
     * @return
     */
    public static Date stringToDate(String str, String dataFormat) {
        DateFormat format = new SimpleDateFormat(dataFormat);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = java.sql.Date.valueOf(str);
        }

        return date;
    }

}
