package com.bd.transformer.util;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 21:58 2018/11/28
 * @Modified by:
 */
public class TimeUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 获取昨日的日期格式字符串数据
     *
     * @return
     */
    public static String getYesterday() {
        return getYesterday(DATE_FORMAT);
    }

    /**
     * 获取对应格式的时间字符串
     *
     * @param pattern
     * @return
     */
    public static String getYesterday(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return sdf.format(calendar.getTime());
    }

    /**
     * 将指定格式的时间字符串转换为时间戳
     *
     * @param input
     * @param pattern
     * @return
     */
    public static long parseString2Long(String input, String pattern) {
        Date date =null;
        try {
            date = new SimpleDateFormat(pattern).parse(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date.getTime();
    }

    /**
     * 将yyyy-MM-dd格式的时间字符串转换为时间戳
     *
     * @param input
     * @return
     */
    public static long parseString2Long(String input) {
        return parseString2Long(input, DATE_FORMAT);
    }

    /**
     * 判断输入日期是否合法
     *
     * @param input
     * @return
     */
    public static boolean isValidateRunningDate(String input) {
        if (StringUtils.isNotBlank(input) &&
                input.matches("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$")) {
            return true;
        }

        return false;
    }

    /**
     * 将nginx服务器时间转化为时间戳，如果解析失败，返回-1
     *
     * @param input
     * @return
     */
    public static long parseNginxServerTime2Long(String input) {
        Date date = parseNginxServerTime2Date(input);
        return date == null ? -1 : date.getTime();
    }

    /**
     * 将nginx服务器时间转换为date对象。如果解析失败，返回null
     *
     * @param input
     * @return
     */
    public static Date parseNginxServerTime2Date(String input) {
        if (StringUtils.isNotBlank(input)) {
            try {
                long timestamp = Double.valueOf(Double.valueOf(input) * 1000).longValue();
                return new Date(timestamp);
            } catch (Exception e) {
                // nothing
            }
        }
        return null;
    }

}
