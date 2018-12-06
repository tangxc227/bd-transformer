package com.bd.transformer.common;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 11:18 2018/11/28
 * @Modified by:
 */
public interface GlobalConstants {

    String FS_DEFAULTFS = "fs.defaultFS";

    String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";

    String RUNNING_DATE_PARAMES = "RUNNING_DATE";

    String DEFAULT_VALUE = "unknown";

    String VALUE_OF_ALL = "all";

    Long DAY_OF_MILLISECONDS = 86400000L;

    String OUTPUT_COLLECTOR_KEY_PREFIX = "collector_";

    String JDBC_DRIVER = "mysql.%s.driver";

    String JDBC_URL = "mysql.%s.url";

    String JDBC_USERNAME = "mysql.%s.username";

    String JDBC_PASSWORD = "mysql.%s.password";

    String WAREHOUSE_OF_REPORT = "report";

    String JDBC_BATCH_SIZE = "mysql.batch.number";

    String DEFAULT_JDBC_BATCH_SIZE = "500";

}
