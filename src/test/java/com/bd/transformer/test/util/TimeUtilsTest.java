package com.bd.transformer.test.util;

import org.junit.Test;

import java.util.Calendar;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 14:18 2018/11/29
 * @Modified by:
 */
public class TimeUtilsTest {

    @Test
    public void testCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println(calendar.getTime());
    }
}
