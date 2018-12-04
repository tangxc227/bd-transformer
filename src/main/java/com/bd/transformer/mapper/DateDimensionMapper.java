package com.bd.transformer.mapper;

import com.bd.transformer.domain.dim.base.DateDimension;
import org.springframework.stereotype.Repository;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 09:08 2018/11/30
 * @Modified by:
 */
@Repository
public interface DateDimensionMapper {

    /**
     * 根据条件查询id
     *
     * @param dateDimension
     * @return
     */
    DateDimension getDateDimensionIdByValue(DateDimension dateDimension);

    /**
     * 新增DateDimension维度
     *
     * @param dateDimension
     */
    void addDateDimension(DateDimension dateDimension);

}
