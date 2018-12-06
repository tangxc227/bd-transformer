package com.bd.transformer.mapreduce.nu;

import com.bd.transformer.common.EventEnum;
import com.bd.transformer.common.EventLogsConstants;
import com.bd.transformer.common.GlobalConstants;
import com.bd.transformer.domain.dim.StatsUserDimension;
import com.bd.transformer.domain.value.map.TimeOutputValue;
import com.bd.transformer.domain.value.reduce.MapWritableValue;
import com.bd.transformer.mapreduce.TransformerOutputFormat;
import com.bd.transformer.util.ArgsUtils;
import com.bd.transformer.util.ConfigurationManager;
import com.bd.transformer.util.TimeUtils;
import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 14:38 2018/12/5
 * @Modified by:
 */
public class NewInstallUserRunner extends Configured implements Tool {

    private static final Logger LOGGER = Logger.getLogger(NewInstallUserRunner.class);

    public static void main(String[] args) {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set(GlobalConstants.FS_DEFAULTFS, ConfigurationManager.getProperty(GlobalConstants.FS_DEFAULTFS));
        configuration.set(GlobalConstants.HBASE_ZOOKEEPER_QUORUM, ConfigurationManager.getProperty(GlobalConstants.HBASE_ZOOKEEPER_QUORUM));
        configuration.addResource("transformer-env.xml");
        configuration.addResource("query-mapping.xml");
        configuration.addResource("output-collector.xml");
        try {
            ToolRunner.run(configuration, new NewInstallUserRunner(), args);
        } catch (Exception e) {
            LOGGER.error("运行计算新用户的job出现异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration configuration = this.getConf();
        // 设置参数
        ArgsUtils.processArgs(configuration, args);

        // 创建job
        Job job = Job.getInstance(configuration, this.getClass().getSimpleName());
        job.setJarByClass(this.getClass());

        // mapper
        TableMapReduceUtil.initTableMapperJob(initScans(job), NewInstallUserMapper.class, StatsUserDimension.class, TimeOutputValue.class, job, false);

        // reducer
        job.setReducerClass(NewInstallUserReducer.class);
        job.setOutputKeyClass(StatsUserDimension.class);
        job.setOutputValueClass(MapWritableValue.class);
        job.setOutputFormatClass(TransformerOutputFormat.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    private List<Scan> initScans(Job job) {
        Configuration configuration = job.getConfiguration();
        String date = configuration.get(GlobalConstants.RUNNING_DATE_PARAMES);
        long startDate = TimeUtils.parseString2Long(date);
        long endDate = startDate + GlobalConstants.DAY_OF_MILLISECONDS;

        Scan scan = new Scan();
        // 定义hbase扫描的开始rowkey和结束rowkey
        scan.setStartRow(Bytes.toBytes("" + startDate));
        scan.setStopRow(Bytes.toBytes("" + endDate));

        FilterList filterList = new FilterList();
        // 过滤事件：只取launch事件
        filterList.addFilter(new SingleColumnValueFilter(
                Bytes.toBytes(EventLogsConstants.EVENT_LOGS_FAMILY_NAME),
                Bytes.toBytes(EventLogsConstants.EVENT_NAME),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(EventEnum.LAUNCH.alias)
        ));
        // 过滤字段
        String[] columns = new String[] {
                EventLogsConstants.EVENT_NAME,
                EventLogsConstants.SERVER_TIME,
                EventLogsConstants.UUID,
                EventLogsConstants.PLATFORM_NAME,
                EventLogsConstants.BROWSER_NAME,
                EventLogsConstants.BROWSER_VERSION
        };
        filterList.addFilter(getColumnFilter(columns));

        scan.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME, Bytes.toBytes(EventLogsConstants.HBASE_NAME_EVENT_LOGS));
        scan.setFilter(filterList);

        return Lists.newArrayList(scan);
    }

    private Filter getColumnFilter(String[] columns) {
        int length = columns.length;
        byte[][] filter = new byte[length][];
        for (int i = 0; i < length; i ++) {
            filter[i] = Bytes.toBytes(columns[i]);
        }
        return new MultipleColumnPrefixFilter(filter);
    }

}
