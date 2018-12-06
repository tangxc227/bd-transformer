package com.bd.transformer.mapreduce;

import com.bd.transformer.common.GlobalConstants;
import com.bd.transformer.common.KpiType;
import com.bd.transformer.domain.dim.base.BaseDimension;
import com.bd.transformer.domain.value.BaseStatsValueWritable;
import com.bd.transformer.service.IDimensionConverter;
import com.bd.transformer.util.JdbcManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 14:35 2018/12/5
 * @Modified by:
 */
public class TransformerOutputFormat extends OutputFormat<BaseDimension, BaseStatsValueWritable> {

    private static final Logger LOGGER = Logger.getLogger(TransformerOutputFormat.class);

    @Override
    public RecordWriter<BaseDimension, BaseStatsValueWritable> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();
        Connection conn = null;
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-beans.xml");
        IDimensionConverter converter = (IDimensionConverter) ac.getBean("dimensionConverter");
        try {
            conn = JdbcManager.getConnection(conf, GlobalConstants.WAREHOUSE_OF_REPORT);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("获取数据库连接失败", e);
            throw new IOException("获取数据库连接失败", e);
        }
        return new TransformerRecoderWriter(conn, conf, converter);
    }

    @Override
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {
        // nothing
    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new FileOutputCommitter(FileOutputFormat.getOutputPath(context), context);
    }

    public class TransformerRecoderWriter extends RecordWriter<BaseDimension, BaseStatsValueWritable> {

        private Connection conn = null;
        private Configuration conf = null;
        private IDimensionConverter converter = null;
        private Map<KpiType, PreparedStatement> map = new HashMap<>();
        private Map<KpiType, Integer> batch = new HashMap<>();

        public TransformerRecoderWriter(Connection conn, Configuration conf, IDimensionConverter converter) {
            this.conn = conn;
            this.conf = conf;
            this.converter = converter;
        }

        @Override
        public void write(BaseDimension key, BaseStatsValueWritable value) throws IOException, InterruptedException {
            if (null == key || null == value) {
                return;
            }
            try {
                // 获取kpi
                KpiType kpi = value.getKpi();
                PreparedStatement pstmt = null;
                int count = 1;
                if (!map.containsKey(kpi)) {
                    pstmt = this.conn.prepareStatement(this.conf.get(kpi.name));
                    map.put(kpi, pstmt);
                } else {
                    pstmt = map.get(kpi);
                    count = batch.get(kpi);
                    count ++;
                }
                batch.put(kpi, count);
                String collectorName = this.conf.get(GlobalConstants.OUTPUT_COLLECTOR_KEY_PREFIX + kpi.name);
                Class<?> clazz = Class.forName(collectorName);
                IOutputCollector outputCollector = (IOutputCollector) clazz.newInstance();
                outputCollector.collect(conf, key, value, converter, pstmt);

                if (count % Integer.parseInt(this.conf.get(GlobalConstants.JDBC_BATCH_SIZE, GlobalConstants.DEFAULT_JDBC_BATCH_SIZE)) == 0) {
                    pstmt.executeBatch();
                    this.conn.commit();
                    batch.remove(kpi);
                }
            } catch (Exception e) {
                LOGGER.error("在writer中写数据出现异常", e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            try {
                for (Map.Entry<KpiType, PreparedStatement> entry : this.map.entrySet()) {
                    entry.getValue().executeBatch();
                }
            } catch (Exception e) {
                LOGGER.error("执行executeUpdate方法异常", e);
                throw new RuntimeException(e);
            } finally {
                try {
                    if (null != this.conn) {
                        this.conn.commit();
                    }
                } catch (Exception e) {
                    // nothing
                } finally {
                    if (null != this.conn) {
                        try {
                            this.conn.close();
                        } catch (SQLException e) {
                            // nothing
                        }
                    }
                }
            }
        }

    }

}
