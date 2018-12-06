package com.bd.transformer.mapreduce.nu;

import com.bd.transformer.common.GlobalConstants;
import com.bd.transformer.domain.dim.StatsUserDimension;
import com.bd.transformer.domain.dim.base.BaseDimension;
import com.bd.transformer.domain.value.BaseStatsValueWritable;
import com.bd.transformer.domain.value.reduce.MapWritableValue;
import com.bd.transformer.mapreduce.IOutputCollector;
import com.bd.transformer.service.IDimensionConverter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 14:46 2018/12/5
 * @Modified by:
 */
public class StatsDeviceBrowserNewInstallUserCollector implements IOutputCollector {
    @Override
    public void collect(Configuration conf, BaseDimension key, BaseStatsValueWritable value, IDimensionConverter converter, PreparedStatement pstmt) throws IOException, SQLException {
        StatsUserDimension statsUserDimension = (StatsUserDimension) key;
        MapWritableValue mapWritableValue = (MapWritableValue) value;
        IntWritable newInstallUsers = (IntWritable) mapWritableValue.getValue().get(new IntWritable(-1));
        int i = 0;
        pstmt.setInt(++i, converter.getDimensionIdByValue(statsUserDimension.getCommonDimension().getPlatformDimension()));
        pstmt.setInt(++i, converter.getDimensionIdByValue(statsUserDimension.getCommonDimension().getDateDimension()));
        pstmt.setInt(++i, converter.getDimensionIdByValue(statsUserDimension.getBrowserDimension()));
        pstmt.setInt(++i, newInstallUsers.get());
        pstmt.setString(++i, conf.get(GlobalConstants.RUNNING_DATE_PARAMES));
        pstmt.setInt(++i, newInstallUsers.get());
        pstmt.addBatch();
    }
}
