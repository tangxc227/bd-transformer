package com.bd.transformer.service;

import com.bd.transformer.domain.dim.base.BaseDimension;

import java.io.IOException;

/**
 * @Author: tangxc
 * @Description: 根据dimension的value值获取id<br   />
 * 如果数据库中有，那么直接返回。如果没有，那么进行插入后返回新的id值
 * @Date: Created in 17:27 2018/11/29
 * @Modified by:
 */
public interface IDimensionConverter {

    int getDimensionIdByValue(BaseDimension dimension) throws IOException;

}
