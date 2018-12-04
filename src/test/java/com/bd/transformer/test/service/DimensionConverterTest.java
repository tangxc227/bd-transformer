package com.bd.transformer.test.service;

import com.bd.transformer.common.DateEnum;
import com.bd.transformer.domain.dim.base.BaseDimension;
import com.bd.transformer.domain.dim.base.DateDimension;
import com.bd.transformer.service.IDimensionConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 15:18 2018/12/4
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml" })
public class DimensionConverterTest {

    @Autowired
    private IDimensionConverter dimensionConverter;

    @Test
    public void test() throws IOException {
        BaseDimension dimension = DateDimension.buildDate(System.currentTimeMillis(), DateEnum.DAY);
        int id = 0;
        for (int i = 0; i < 5; i ++) {
            id = dimensionConverter.getDimensionIdByValue(dimension);
            System.out.println(id);
        }
    }

}
