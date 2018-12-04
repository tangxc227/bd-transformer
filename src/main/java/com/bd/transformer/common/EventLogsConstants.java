package com.bd.transformer.common;

/**
 * @Author: tangxc
 * @Description:    定义日志收集客户端收集得到的用户数据参数的name名称<br />
 *      以及event_logs这张hbase表的结构信息
 * @Date: Created in 11:18 2018/11/28
 * @Modified by:
 */
public interface EventLogsConstants {

    /**
     * hbase 表名
     */
    String HBASE_NAME_EVENT_LOGS = "event_logs";
    /**
     * event_logs表的列簇名称
     */
    String EVENT_LOGS_FAMILY_NAME = "info";
    /**
     * 分隔符
     */
    String LOG_SEPARTIOR = "\\^A";
    /**
     * 用户IP地址
     */
    String IP = "ip";
    /**
     * 服务器时间
     */
    String SERVER_TIME = "s_time";

    String EVENT_NAME = "en";
    /**
     * 数据收集端的版本信息
     */
    String VERSION = "ver";
    /**
     * 用户唯一标识符
     */
    String UUID = "u_ud";

    String MEMBER_ID = "u_mid";

    String PLATFORM_NAME = "pl";

    String SESSION_ID = "u_sd";

    String CLIENT_TIME = "c_time";

    String LANGUAGE = "l";

    String USER_AGENT = "b_iev";

    String RESOLUTION = "b_rst";

    String CURRENT_URL = "p_url";

    String REFERRER_URL = "p_ref";

    String TITLE = "tt";

    String ORDER_ID = "oid";

    String ORDER_NAME = "on";

    String ORDER_CURRENCY_AMOUNT = "cua";

    String ORDER_CURRENCY_TYPE = "cut";

    String ORDER_PAYMENT_TYPE = "pt";

    String EVENT_CATEGORY = "ca";

    String EVENT_ACTION = "ac";

    String EVENT_KV_START = "kv_";

    String EVENT_DURATION = "du";

    String BROWSER_NAME = "browser";

    String BROWSER_VERSION = "browser_v";

    String OS_NAME = "os";

    String OS_VERSION = "os_v";

    String COUNTRY = "country";

    String PROVINCE = "province";

    String CITY = "city";
}
