package com.bd.transformer.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 10:43 2018/11/29
 * @Modified by:
 */
public class ConfigurationManager {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream in = ConfigurationManager.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(in);
        } catch (Exception e) {
            throw new RuntimeException("加载配置文件失败", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
