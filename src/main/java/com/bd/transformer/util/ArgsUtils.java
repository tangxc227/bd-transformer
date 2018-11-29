package com.bd.transformer.util;

import com.bd.transformer.common.GlobalConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 10:15 2018/11/29
 * @Modified by:
 */
public class ArgsUtils {

    public static void processArgs(Configuration configuration, String[] args) {
        String date = null;
        for (int i = 0; i < args.length; i++) {
            if ("-d".equals(args[i])) {
                if (i + 1 < args.length) {
                    date = args[++i];
                    break;
                }
            }
        }
        if (StringUtils.isBlank(date) || !TimeUtils.isValidateRunningDate(date)) {
            date = TimeUtils.getYesterday();
        }
        configuration.set(GlobalConstants.RUNNING_DATE_PARAMES, date);
    }

}
