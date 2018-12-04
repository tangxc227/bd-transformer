package com.bd.transformer.domain.value.map;

import com.bd.transformer.common.KpiType;
import com.bd.transformer.domain.value.BaseStatsValueWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 15:38 2018/11/29
 * @Modified by:
 */
public class TimeOutputValue extends BaseStatsValueWritable {

    private String id;
    private long timestamp;

    public TimeOutputValue() {
    }

    public TimeOutputValue(String id, long timestamp) {
        set(id, timestamp);
    }

    public void set(String id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    @Override
    public KpiType getKpi() {
        return null;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.id);
        out.writeLong(this.timestamp);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readUTF();
        this.timestamp = in.readLong();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
