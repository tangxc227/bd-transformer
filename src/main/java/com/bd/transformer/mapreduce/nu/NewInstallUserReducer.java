package com.bd.transformer.mapreduce.nu;

import com.bd.transformer.common.KpiType;
import com.bd.transformer.domain.dim.StatsUserDimension;
import com.bd.transformer.domain.value.map.TimeOutputValue;
import com.bd.transformer.domain.value.reduce.MapWritableValue;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 14:38 2018/12/5
 * @Modified by:
 */
public class NewInstallUserReducer extends Reducer<StatsUserDimension, TimeOutputValue, StatsUserDimension, MapWritableValue> {

    private MapWritableValue outputValue = new MapWritableValue();

    private Set<String> unique = new HashSet<>();

    @Override
    protected void reduce(StatsUserDimension key, Iterable<TimeOutputValue> values, Context context) throws IOException, InterruptedException {

        // 设置kpi
        outputValue.setKpiType(KpiType.valueOfName(key.getCommonDimension().getKpiDimension().getKpiName()));

        for (TimeOutputValue value : values) {
            this.unique.add(value.getId());
        }

        MapWritable map = new MapWritable();
        map.put(new IntWritable(-1), new IntWritable(this.unique.size()));
        outputValue.setValue(map);

        context.write(key, outputValue);
    }

}
