package com.bd.transformer.test.mapper;

import com.bd.transformer.domain.dim.base.BrowserDimension;
import com.bd.transformer.mapper.BrowserDimensionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 10:41 2018/11/30
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml" })
public class BrowserDimensionMapperTest {

    @Autowired
    private BrowserDimensionMapper browserDimensionMapper;

    @Test
    public void test1() {
        BrowserDimension browserDimension = new BrowserDimension();
        browserDimension.setBrowserName("chrome");
        browserDimension.setBrowserVersion("1.2.0");
        System.out.println(browserDimensionMapper.getBrowserDimensionIdByValue(browserDimension));
    }

}
