package com.bd.transformer.etl.util;

import com.bd.transformer.domain.UserAgentInfo;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 22:28 2018/11/28
 * @Modified by:
 */
public class UserAgentUtil {

    private static UASparser uaSparser = null;

    static {
        try {
            uaSparser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserAgentInfo parseUserAgent(String userAgent) {
        UserAgentInfo userAgentInfo = null;
        if (StringUtils.isNotBlank(userAgent)) {
            try {
                cz.mallat.uasparser.UserAgentInfo info = uaSparser.parse(userAgent);
                if (null != info) {
                    userAgentInfo = new UserAgentInfo();
                    userAgentInfo.setBrowserName(info.getUaFamily());
                    userAgentInfo.setBrowserVersion(info.getBrowserVersionInfo());
                    userAgentInfo.setOsName(info.getOsFamily());
                    userAgentInfo.setOsVersion(info.getOsName());
                }
            } catch (Exception e) {
                // nothing
            }
        }
        return userAgentInfo;
    }

}
