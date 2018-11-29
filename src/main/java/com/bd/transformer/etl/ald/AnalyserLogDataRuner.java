package com.bd.transformer.etl.ald;

import com.bd.transformer.common.EventLogsConstants;
import com.bd.transformer.common.GlobalConstants;
import com.bd.transformer.util.ArgsUtils;
import com.bd.transformer.util.ConfigurationManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 11:53 2018/11/28
 * @Modified by:
 */
public class AnalyserLogDataRuner extends Configured implements Tool {

    private static final Logger LOGGER = Logger.getLogger(AnalyserLogDataRuner.class);

    private void setJobInputPaths(Job job) {
        Configuration configuration = job.getConfiguration();
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(configuration);
            Path inputPath = new Path("/logs/" + configuration.get(GlobalConstants.RUNNING_DATE_PARAMES));
            if (fileSystem.exists(inputPath)) {
                FileInputFormat.addInputPath(job, inputPath);
            } else {
                throw new RuntimeException("输入目录不存在：" + inputPath);
            }
        } catch (Exception e) {
            throw new RuntimeException("设置job的mapreduce输入路径异常", e);
        } finally {
            if (null != fileSystem) {
                try {
                    fileSystem.close();
                } catch (IOException e) {
                    // nothing
                }
            }
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration configuration = this.getConf();
        // 参数设置
        ArgsUtils.processArgs(configuration, args);

        Job job = Job.getInstance(configuration, this.getClass().getSimpleName());
        job.setJarByClass(this.getClass());

        // 设置mapper
        job.setMapperClass(AnalyserLogDataMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Put.class);

        // 设置reduce个数
        job.setNumReduceTasks(0);

        // 设置reducer
        TableMapReduceUtil.initTableReducerJob(EventLogsConstants.HBASE_NAME_EVENT_LOGS,
                null, job, null, null, null, null, false);

        // 设置输入目录
        this.setJobInputPaths(job);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) {
        try {
            Configuration configuration = HBaseConfiguration.create();
            configuration.set(GlobalConstants.FS_DEFAULTFS,
                    ConfigurationManager.getProperty(GlobalConstants.FS_DEFAULTFS));
            configuration.set(GlobalConstants.HBASE_ZOOKEEPER_QUORUM,
                    ConfigurationManager.getProperty(GlobalConstants.HBASE_ZOOKEEPER_QUORUM));
            int exitCode = ToolRunner.run(configuration, new AnalyserLogDataRuner(), args);
            System.exit(exitCode);
        } catch (Exception e) {
            LOGGER.error("执行日志解析job异常", e);
            throw new RuntimeException(e);
        }
    }

}
