package com.bd.transformer.mapper;

import com.bd.transformer.domain.dim.base.BrowserDimension;
import org.springframework.stereotype.Repository;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 09:50 2018/11/30
 * @Modified by:
 */
@Repository
public interface BrowserDimensionMapper {

    /**
     * 根据条件查询id
     *
     * @param browserDimension
     * @return
     */
    BrowserDimension getBrowserDimensionIdByValue(BrowserDimension browserDimension);

    /**
     * 新增浏览器维度
     *
     * @param browserDimension
     */
    void addBrowserDimension(BrowserDimension browserDimension);

}
