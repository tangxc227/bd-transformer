package com.bd.transformer.test.mapper;

import com.bd.transformer.common.DateEnum;
import com.bd.transformer.domain.dim.base.DateDimension;
import com.bd.transformer.mapper.DateDimensionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 09:59 2018/11/30
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-beans.xml" })
public class DateDimensionMapperTest {

    @Autowired
    private DateDimensionMapper dateDimensionMapper;

    @Test
    public void test1() throws ParseException {
        DateDimension dateDimension = DateDimension.buildDate(new SimpleDateFormat("yyyy-MM-dd").parse("2015-12-12").getTime(), DateEnum.DAY);
        System.out.println(dateDimensionMapper.getDateDimensionIdByValue(dateDimension));
    }

    @Test
    public void test2() {
        DateDimension dateDimension = DateDimension.buildDate(new Date().getTime(), DateEnum.DAY);
        dateDimensionMapper.addDateDimension(dateDimension);
        System.out.println(dateDimension.getId());
    }

}
