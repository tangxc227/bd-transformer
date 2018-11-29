package com.bd.transformer.etl.util;

import com.bd.transformer.common.EventLogsConstants;
import com.bd.transformer.domain.UserAgentInfo;
import com.bd.transformer.util.IPSeekerExt;
import com.bd.transformer.util.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: tangxc
 * @Description: 处理日志数据的具体工作类
 * @Date: Created in 21:50 2018/11/28
 * @Modified by:
 */
public class LoggerUtil {

    private static final Logger LOGGER = Logger.getLogger(LoggerUtil.class);

    private static IPSeekerExt ipSeekerExt = new IPSeekerExt();

    /**
     * @param logText
     * @return
     */
    public static Map<String, String> handleLog(String logText) {
        Map<String, String> clientInfo = new HashMap<>();
        if (StringUtils.isNotBlank(logText)) {
            String[] tokens = logText.trim().split(EventLogsConstants.LOG_SEPARTIOR);
            if (4 == tokens.length) {
                // 日志格式：ip^A服务器时间^Ahost^A请求参数
                // 设置IP地址
                clientInfo.put(EventLogsConstants.IP, tokens[0].trim());
                // 设置服务器时间
                clientInfo.put(EventLogsConstants.SERVER_TIME, String.valueOf(TimeUtils.parseNginxServerTime2Long(tokens[1].trim())));
                int index = tokens[3].indexOf("?");
                if (index > -1) {
                    String requestBody = tokens[3].substring(index + 1);
                    // 处理请求参数
                    handleRequestBody(requestBody, clientInfo);
                    // 处理userAgent
                    handleUserAgent(clientInfo);
                    // 处理ip地址
                    handleIp(clientInfo);
                } else {
                    clientInfo.clear();
                }
            }
        }
        return clientInfo;
    }

    /**
     * 处理ip地址
     *
     * @param clientInfo
     */
    private static void handleIp(Map<String, String> clientInfo) {
        if (clientInfo.containsKey(EventLogsConstants.IP)) {
            String ip = clientInfo.get(EventLogsConstants.IP);
            IPSeekerExt.RegionInfo regionInfo = ipSeekerExt.analyticIp(ip);
            if (null != regionInfo) {
                clientInfo.put(EventLogsConstants.COUNTRY, regionInfo.getCountry());
                clientInfo.put(EventLogsConstants.PROVINCE, regionInfo.getProvince());
                clientInfo.put(EventLogsConstants.CITY, regionInfo.getCity());
            }
        }
    }

    /**
     * 处理浏览器的userAgent信息
     *
     * @param clientInfo
     */
    private static void handleUserAgent(Map<String, String> clientInfo) {
        if (clientInfo.containsKey(EventLogsConstants.USER_AGENT)) {
            UserAgentInfo info = UserAgentUtil.parseUserAgent(clientInfo.get(EventLogsConstants.USER_AGENT));
            if (null != info) {
                clientInfo.put(EventLogsConstants.BROWSER_NAME, info.getBrowserName());
                clientInfo.put(EventLogsConstants.BROWSER_VERSION, info.getBrowserVersion());
                clientInfo.put(EventLogsConstants.OS_NAME, info.getOsName());
                clientInfo.put(EventLogsConstants.OS_VERSION, info.getOsVersion());
            }
        }
    }

    /**
     * 处理请求参数
     *
     * @param requestBody
     * @param clientInfo
     */
    private static void handleRequestBody(String requestBody, Map<String, String> clientInfo) {
        if (StringUtils.isNotBlank(requestBody)) {
            String[] requestParams = StringUtils.split(requestBody, "&");
            for (String param : requestParams) {
                int index = param.indexOf("=");
                if (index < 0) {
                    LOGGER.warn("无法进行参数解析：" + param + "， 请求参数为：" + requestBody);
                    continue;
                }
                String key = null, value = null;
                try {
                    key = param.substring(0, index);
                    value = URLDecoder.decode(param.substring(index + 1), "UTF-8");
                } catch (Exception e) {
                    LOGGER.warn("解码操作出现异常", e);
                    continue;
                }
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                    clientInfo.put(key, value);
                }
            }
        }
    }

}
