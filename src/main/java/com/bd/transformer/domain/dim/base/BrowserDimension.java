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
 * @Description: 浏览器维度信息表
 * @Date: Created in 11:14 2018/11/29
 * @Modified by:
 */
public class BrowserDimension extends BaseDimension {

    private int id;
    private String browserName;
    private String browserVersion;
    private Date createdTime;

    public BrowserDimension() {
    }

    public BrowserDimension(int id, String browserName, String browserVersion) {
        this.id = id;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
    }

    public BrowserDimension(String browserName, String browserVersion) {
        this.browserName = browserName;
        this.browserVersion = browserVersion;
    }

    public BrowserDimension(String browserName, String browserVersion, Date createdTime) {
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.createdTime = createdTime;
    }

    public BrowserDimension(int id, String browserName, String browserVersion, Date createdTime) {
        this.id = id;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.createdTime = createdTime;
    }

    public static List<BrowserDimension> buildList(String browserName, String browserVersion) {
        List<BrowserDimension> list = new ArrayList<>();
        if (StringUtils.isBlank(browserName)) {
            browserName = GlobalConstants.DEFAULT_VALUE;
            browserVersion = GlobalConstants.DEFAULT_VALUE;
        }
        if (StringUtils.isBlank(browserVersion)) {
            browserVersion = GlobalConstants.DEFAULT_VALUE;
        }
        list.add(new BrowserDimension(browserName, browserVersion, new Date()));
        list.add(new BrowserDimension(browserName, GlobalConstants.VALUE_OF_ALL, new Date()));
        return list;
    }

    @Override
    public int compareTo(BaseDimension o) {
        if (this == o) {
            return 0;
        }
        BrowserDimension other = (BrowserDimension) o;
        int tmp = Integer.compare(this.id, other.id);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.browserName.compareTo(other.browserName);
        if (0 != tmp) {
            return tmp;
        }
        tmp = this.browserVersion.compareTo(other.browserVersion);
        return tmp;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.browserName);
        out.writeUTF(this.browserVersion);
        out.writeLong(this.createdTime.getTime());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.browserName = in.readUTF();
        this.browserVersion = in.readUTF();
        this.createdTime.setTime(in.readLong());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
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
        BrowserDimension that = (BrowserDimension) o;
        return id == that.id &&
                Objects.equals(browserName, that.browserName) &&
                Objects.equals(browserVersion, that.browserVersion) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, browserName, browserVersion, createdTime);
    }

}
