package com.bd.transformer.domain.dim;

import com.bd.transformer.domain.dim.base.BaseDimension;
import com.bd.transformer.domain.dim.base.BrowserDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 15:17 2018/11/29
 * @Modified by:
 */
public class StatsUserDimension extends StatsDimension {

    private StatsCommonDimension commonDimension = new StatsCommonDimension();
    private BrowserDimension browserDimension = new BrowserDimension();

    public StatsUserDimension() {
    }

    public StatsUserDimension(StatsCommonDimension commonDimension, BrowserDimension browserDimension) {
        this.commonDimension = commonDimension;
        this.browserDimension = browserDimension;
    }

    @Override
    public int compareTo(BaseDimension o) {
        if (this == o) {
            return 0;
        }
        StatsUserDimension other = (StatsUserDimension) o;
        int tmp = this.commonDimension.compareTo(other.commonDimension);
        if (0 != tmp) {
            return tmp;
        }
        tmp = this.browserDimension.compareTo(other.browserDimension);
        return tmp;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.commonDimension.write(out);
        this.browserDimension.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.commonDimension.readFields(in);
        this.browserDimension.readFields(in);
    }

    public StatsCommonDimension getCommonDimension() {
        return commonDimension;
    }

    public void setCommonDimension(StatsCommonDimension commonDimension) {
        this.commonDimension = commonDimension;
    }

    public BrowserDimension getBrowserDimension() {
        return browserDimension;
    }

    public void setBrowserDimension(BrowserDimension browserDimension) {
        this.browserDimension = browserDimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsUserDimension that = (StatsUserDimension) o;
        return Objects.equals(commonDimension, that.commonDimension) &&
                Objects.equals(browserDimension, that.browserDimension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commonDimension, browserDimension);
    }

}
