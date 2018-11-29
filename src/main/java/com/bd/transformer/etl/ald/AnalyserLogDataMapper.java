package com.bd.transformer.etl.ald;

import com.bd.transformer.common.EventEnum;
import com.bd.transformer.common.EventLogsConstants;
import com.bd.transformer.etl.util.LoggerUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.zip.CRC32;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 11:50 2018/11/28
 * @Modified by:
 */
public class AnalyserLogDataMapper extends Mapper<Object, Text, NullWritable, Put> {

    private static final Logger LOGGER = Logger.getLogger(AnalyserLogDataMapper.class);

    private int inputRecords, filterRecords, outputRecords;
    private byte[] family = Bytes.toBytes(EventLogsConstants.EVENT_LOGS_FAMILY_NAME);
    private CRC32 crc32 = new CRC32();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        this.inputRecords++;
        LOGGER.debug("Analyse data of :" + value.toString());
        try {
            // 解析日志
            Map<String, String> clientInfo = LoggerUtil.handleLog(value.toString());
            if (clientInfo.isEmpty()) {
                this.filterRecords++;
                return;
            }
            // 获取事件名称
            String eventAliasName = clientInfo.get(EventLogsConstants.EVENT_NAME);
            EventEnum eventEnum = EventEnum.valuesOfAlias(eventAliasName);
            switch (eventEnum) {
                case LAUNCH:
                case PAGEVIEW:
                case CHARGEREQUEST:
                case CHARGESUCCESS:
                case CHARGEREFUND:
                case EVENT:
                    // 处理数据
                    this.handleData(clientInfo, eventEnum, context);
                    break;
                default:
                    this.filterRecords++;
                    LOGGER.warn("该事件没法进行解析，事件名称：" + eventAliasName);
            }
        } catch (Exception e) {
            this.filterRecords++;
            LOGGER.error("处理数据发生异常，数据：" + value, e);
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
        LOGGER.info("输入数据：" + this.inputRecords + "；输出数据：" + this.outputRecords + "；过滤数据：" + this.filterRecords);
    }

    /**
     * 具体处理数据的方法
     *
     * @param clientInfo
     * @param eventEnum
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    private void handleData(Map<String, String> clientInfo, EventEnum eventEnum, Context context) throws IOException, InterruptedException {
        String serverTime = clientInfo.get(EventLogsConstants.SERVER_TIME);
        String uuid = clientInfo.get(EventLogsConstants.UUID);
        String memberId = clientInfo.get(EventLogsConstants.MEMBER_ID);
        // 服务器时间不能为空
        if (StringUtils.isNotBlank(serverTime)) {
            // 去掉浏览器信息
            clientInfo.remove(EventLogsConstants.USER_AGENT);
            // 生成rowkey
            String rowkey = this.generateRowkey(uuid, memberId, eventEnum.alias, serverTime);
            // 创建put对象
            Put put = new Put(Bytes.toBytes(rowkey));
            for (Map.Entry<String, String> entry : clientInfo.entrySet()) {
                if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
                    put.add(family, Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
                }
            }
            context.write(NullWritable.get(), put);
            this.outputRecords++;
        } else {
            this.filterRecords++;
            LOGGER.warn("服务器时间为空：" + clientInfo);
        }
    }

    /**
     * 根据uuid memberid servertime创建rowkey
     *
     * @param uuid
     * @param memberId
     * @param eventAliasName
     * @param serverTime
     * @return
     */
    private String generateRowkey(String uuid, String memberId, String eventAliasName, String serverTime) {
        StringBuilder builder = new StringBuilder();
        builder.append(serverTime).append("_");
        this.crc32.reset();
        if (StringUtils.isNotBlank(uuid)) {
            this.crc32.update(uuid.getBytes());
        }
        if (StringUtils.isNotBlank(memberId)) {
            this.crc32.update(memberId.getBytes());
        }
        this.crc32.update(eventAliasName.getBytes());
        builder.append(this.crc32.getValue() % 100000000L);
        return builder.toString();
    }

}
