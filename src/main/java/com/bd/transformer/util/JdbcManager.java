package com.bd.transformer.util;

import com.bd.transformer.common.GlobalConstants;
import org.apache.hadoop.conf.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 16:14 2018/12/5
 * @Modified by:
 */
public class JdbcManager {

    public static Connection getConnection(Configuration configuration, String flag) throws SQLException {
        String jdbcDriver = configuration.get(String.format(GlobalConstants.JDBC_DRIVER, flag));
        String jdbcUrl = configuration.get(String.format(GlobalConstants.JDBC_URL, flag));
        String jdbcUsername = configuration.get(String.format(GlobalConstants.JDBC_USERNAME, flag));
        String jdbcPassword = configuration.get(String.format(GlobalConstants.JDBC_PASSWORD, flag));

        try {
            Class.forName(jdbcDriver);
        } catch (Exception e) {
            // nothing
        }

        return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
    }

}
