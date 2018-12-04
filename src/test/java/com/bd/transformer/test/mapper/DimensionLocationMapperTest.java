package com.bd.transformer.test.mapper;

import com.bd.transformer.mapper.LocationDimensionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 11:58 2018/11/29
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml" })
public class DimensionLocationMapperTest {

    @Autowired
    private LocationDimensionMapper locationdimensionMapper;

    @Test
    public void getList() {
        System.out.println(locationdimensionMapper.getList());
    }

}
