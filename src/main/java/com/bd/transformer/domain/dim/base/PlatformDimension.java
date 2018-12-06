package com.bd.transformer.domain.dim.base;

import com.bd.transformer.common.GlobalConstants;
import org.apache.commons.lang.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 11:37 2018/11/29
 * @Modified by:
 */
public class PlatformDimension extends BaseDimension {

    private int id;
    private String platformName;
    private Date createdTime = new Date();

    public PlatformDimension() {

    }

    public PlatformDimension(String platformName, Date createdTime) {
        this.platformName = platformName;
        this.createdTime = createdTime;
    }

    public PlatformDimension(int id, String platformName, Date createdTime) {
        this.id = id;
        this.platformName = platformName;
        this.createdTime = createdTime;
    }

    public static List<PlatformDimension> buildList(String platformName) {
        List<PlatformDimension> list = new ArrayList<>();
        if (StringUtils.isBlank(platformName)) {
            platformName = GlobalConstants.DEFAULT_VALUE;
        }
        list.add(new PlatformDimension(platformName, new Date()));
        list.add(new PlatformDimension(GlobalConstants.VALUE_OF_ALL, new Date()));
        return list;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.platformName);
        out.writeLong(this.createdTime.getTime());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.platformName = in.readUTF();
        this.createdTime.setTime(in.readLong());
    }

    @Override
    public int compareTo(BaseDimension o) {
        if (this == o) {
            return 0;
        }
        PlatformDimension other = (PlatformDimension) o;
        int tmp = Integer.compare(this.id, other.id);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.platformName.compareTo(other.platformName);
        return tmp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
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
        PlatformDimension that = (PlatformDimension) o;
        return id == that.id &&
                Objects.equals(platformName, that.platformName) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, platformName, createdTime);
    }

    @Override
    public String toString() {
        return "PlatformDimension{" +
                "id=" + id +
                ", platformName='" + platformName + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

}
