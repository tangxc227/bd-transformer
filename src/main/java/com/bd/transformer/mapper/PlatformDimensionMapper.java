package com.bd.transformer.mapper;

import com.bd.transformer.domain.dim.base.PlatformDimension;
import org.springframework.stereotype.Repository;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 09:18 2018/11/30
 * @Modified by:
 */
@Repository
public interface PlatformDimensionMapper {

    void addPlatformDimension(PlatformDimension platformDimension);

    PlatformDimension getPlatformDimensionIdByValue(PlatformDimension platformDimension);

}
