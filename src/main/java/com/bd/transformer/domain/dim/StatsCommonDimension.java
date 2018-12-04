package com.bd.transformer.domain.dim;

import com.bd.transformer.domain.dim.base.BaseDimension;
import com.bd.transformer.domain.dim.base.DateDimension;
import com.bd.transformer.domain.dim.base.KpiDimension;
import com.bd.transformer.domain.dim.base.PlatformDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 15:14 2018/11/29
 * @Modified by:
 */
public class StatsCommonDimension extends StatsDimension {

    private DateDimension dateDimension = new DateDimension();
    private PlatformDimension platformDimension = new PlatformDimension();
    private KpiDimension kpiDimension = new KpiDimension();

    public StatsCommonDimension() {

    }

    public StatsCommonDimension(DateDimension dateDimension, PlatformDimension platformDimension, KpiDimension kpiDimension) {
        this.dateDimension = dateDimension;
        this.platformDimension = platformDimension;
        this.kpiDimension = kpiDimension;
    }

    @Override
    public int compareTo(BaseDimension o) {
        if (this == o) {
            return 0;
        }
        StatsCommonDimension other = (StatsCommonDimension) o;
        int tmp = this.dateDimension.compareTo(other.dateDimension);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.platformDimension.compareTo(other.platformDimension);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.kpiDimension.compareTo(other.kpiDimension);
        return tmp;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.dateDimension.write(out);
        this.platformDimension.write(out);
        this.kpiDimension.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.dateDimension.readFields(in);
        this.platformDimension.readFields(in);
        this.kpiDimension.readFields(in);
    }

    public DateDimension getDateDimension() {
        return dateDimension;
    }

    public void setDateDimension(DateDimension dateDimension) {
        this.dateDimension = dateDimension;
    }

    public PlatformDimension getPlatformDimension() {
        return platformDimension;
    }

    public void setPlatformDimension(PlatformDimension platformDimension) {
        this.platformDimension = platformDimension;
    }

    public KpiDimension getKpiDimension() {
        return kpiDimension;
    }

    public void setKpiDimension(KpiDimension kpiDimension) {
        this.kpiDimension = kpiDimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsCommonDimension that = (StatsCommonDimension) o;
        return Objects.equals(dateDimension, that.dateDimension) &&
                Objects.equals(platformDimension, that.platformDimension) &&
                Objects.equals(kpiDimension, that.kpiDimension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateDimension, platformDimension, kpiDimension);
    }

}
