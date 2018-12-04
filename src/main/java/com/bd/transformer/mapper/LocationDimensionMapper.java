package com.bd.transformer.mapper;

import com.bd.transformer.domain.dim.base.LocationDimension;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 11:50 2018/11/29
 * @Modified by:
 */
@Repository
public interface LocationDimensionMapper {

    List<LocationDimension> getList();

}
