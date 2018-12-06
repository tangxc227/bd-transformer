package com.bd.transformer.mapreduce.nu;

import com.bd.transformer.common.DateEnum;
import com.bd.transformer.common.EventLogsConstants;
import com.bd.transformer.common.KpiType;
import com.bd.transformer.domain.dim.StatsCommonDimension;
import com.bd.transformer.domain.dim.StatsUserDimension;
import com.bd.transformer.domain.dim.base.BrowserDimension;
import com.bd.transformer.domain.dim.base.DateDimension;
import com.bd.transformer.domain.dim.base.KpiDimension;
import com.bd.transformer.domain.dim.base.PlatformDimension;
import com.bd.transformer.domain.value.map.TimeOutputValue;
import com.bd.transformer.util.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 14:38 2018/12/5
 * @Modified by:
 */
public class NewInstallUserMapper extends TableMapper<StatsUserDimension, TimeOutputValue> {

    private static final Logger LOGGER = Logger.getLogger(NewInstallUserMapper.class);

    private byte[] family = Bytes.toBytes(EventLogsConstants.EVENT_LOGS_FAMILY_NAME);
    /**
     * map output key
     */
    private StatsUserDimension userDimension = new StatsUserDimension();
    /**
     * map output value
     */
    private TimeOutputValue outputValue = new TimeOutputValue();

    private KpiDimension newInstallUserKpi = new KpiDimension(KpiType.NEW_INSTALL_USER.name, new Date());
    private KpiDimension newInstallUserOfBrowserKpi = new KpiDimension(KpiType.BROWSER_NEW_INSTALL_USER.name, new Date());

    private BrowserDimension defaultBrowserDimension = new BrowserDimension("", "");

    /**
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {

        String serverTime = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogsConstants.SERVER_TIME)));
        String uuid = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogsConstants.UUID)));
        String platformName = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogsConstants.PLATFORM_NAME)));
        String browserName = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogsConstants.BROWSER_NAME)));
        String browserVersion = Bytes.toString(value.getValue(family, Bytes.toBytes(EventLogsConstants.BROWSER_VERSION)));

        if (StringUtils.isBlank(serverTime)) {
            LOGGER.warn("服务器时间不能为空");
            return;
        }

        if (StringUtils.isBlank(uuid)) {
            LOGGER.warn("UUID不能为空");
            return;
        }

        if (StringUtils.isBlank(platformName)) {
            LOGGER.warn("平台名称不能为空");
            return;
        }

        long timestamp = Long.parseLong(serverTime);

        // 构建日期维度
        DateDimension dateDimension = DateDimension.buildDate(timestamp, DateEnum.DAY);
        // 构建平台维度
        List<PlatformDimension> platformDimensionList = PlatformDimension.buildList(platformName);
        // 构建浏览器维度
        List<BrowserDimension> browserDimensionList = BrowserDimension.buildList(browserName, browserVersion);

        // 输出
        this.outputValue.set(uuid, timestamp);

        StatsCommonDimension commonDimension = this.userDimension.getCommonDimension();
        // 设置日期信息
        commonDimension.setDateDimension(dateDimension);
        for (PlatformDimension platformDimension : platformDimensionList) {
            // 设置kpi信息
            commonDimension.setKpiDimension(newInstallUserKpi);
            // 设置平台信息
            commonDimension.setPlatformDimension(platformDimension);
            // 清空浏览器信息
            this.userDimension.setBrowserDimension(defaultBrowserDimension);
            // 输出平台维度信息
            context.write(userDimension, outputValue);

            commonDimension.setKpiDimension(newInstallUserOfBrowserKpi);
            for (BrowserDimension browserDimension : browserDimensionList) {
                this.userDimension.setBrowserDimension(browserDimension);
                context.write(userDimension, outputValue);
            }
        }

    }

}
