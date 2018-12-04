package com.bd.transformer.domain.value.reduce;

import com.bd.transformer.common.KpiType;
import com.bd.transformer.domain.value.BaseStatsValueWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.WritableUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 16:09 2018/11/29
 * @Modified by:
 */
public class MapWritableValue extends BaseStatsValueWritable {

    private MapWritable value = new MapWritable();
    private KpiType kpiType;

    public MapWritableValue() {
    }

    public MapWritableValue(MapWritable value, KpiType kpiType) {
        set(value, kpiType);
    }

    public void set(MapWritable value, KpiType kpiType) {
        this.value = value;
        this.kpiType = kpiType;
    }

    @Override
    public KpiType getKpi() {
        return this.kpiType;
    }

    public void setKpiType(KpiType kpiType) {
        this.kpiType = kpiType;
    }

    public MapWritable getValue() {
        return value;
    }

    public void setValue(MapWritable value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.value.write(out);
        WritableUtils.writeEnum(out, this.kpiType);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.value.readFields(in);
        WritableUtils.readEnum(in, KpiType.class);
    }

}
