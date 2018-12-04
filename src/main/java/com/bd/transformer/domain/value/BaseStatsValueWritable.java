package com.bd.transformer.domain.value;

import com.bd.transformer.common.KpiType;
import org.apache.hadoop.io.Writable;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 15:36 2018/11/29
 * @Modified by:
 */
public abstract class BaseStatsValueWritable implements Writable {

    public abstract KpiType getKpi();

}
