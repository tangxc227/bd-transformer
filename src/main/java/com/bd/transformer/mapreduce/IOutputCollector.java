package com.bd.transformer.mapreduce;

import com.bd.transformer.domain.dim.base.BaseDimension;
import com.bd.transformer.domain.value.BaseStatsValueWritable;
import com.bd.transformer.service.IDimensionConverter;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: tangxc
 * @Description: 自定义的配合自定义output进行具体sql输出的类
 * @Date: Created in 14:34 2018/12/5
 * @Modified by:
 */
public interface IOutputCollector {

    void collect(Configuration conf, BaseDimension key, BaseStatsValueWritable value, IDimensionConverter converter, PreparedStatement pstmt) throws IOException, SQLException;

}
