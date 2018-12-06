package com.bd.transformer.domain.dim.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 15:18 2018/11/29
 * @Modified by:
 */
public class KpiDimension extends BaseDimension {

    private int id;
    private String kpiName;
    private Date createdTime = new Date();

    public KpiDimension() {
    }

    public KpiDimension(String kpiName, Date createdTime) {
        this.kpiName = kpiName;
        this.createdTime = createdTime;
    }

    public KpiDimension(int id, String kpiName, Date createdTime) {
        this.id = id;
        this.kpiName = kpiName;
        this.createdTime = createdTime;
    }

    @Override
    public int compareTo(BaseDimension o) {
        if (this == o) {
            return 0;
        }
        KpiDimension other = (KpiDimension) o;
        int tmp = Integer.compare(this.id, other.id);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.kpiName.compareTo(other.kpiName);
        return tmp;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.kpiName);
        out.writeLong(this.createdTime.getTime());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.kpiName = in.readUTF();
        this.createdTime.setTime(in.readLong());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KpiDimension that = (KpiDimension) o;
        return id == that.id &&
                Objects.equals(kpiName, that.kpiName) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kpiName, createdTime);
    }

    @Override
    public String toString() {
        return "KpiDimension{" +
                "id=" + id +
                ", kpiName='" + kpiName + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

}
