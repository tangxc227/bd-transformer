package com.bd.transformer.service.impl;

import com.bd.transformer.domain.dim.base.BaseDimension;
import com.bd.transformer.domain.dim.base.BrowserDimension;
import com.bd.transformer.domain.dim.base.DateDimension;
import com.bd.transformer.domain.dim.base.PlatformDimension;
import com.bd.transformer.mapper.BrowserDimensionMapper;
import com.bd.transformer.mapper.DateDimensionMapper;
import com.bd.transformer.mapper.PlatformDimensionMapper;
import com.bd.transformer.service.IDimensionConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 17:28 2018/11/29
 * @Modified by:
 */
@Service(value = "dimensionConverter")
public class DimensionConverterImpl implements IDimensionConverter {

    private static final Logger LOGGER = Logger.getLogger(DimensionConverterImpl.class);

    @Autowired
    private DateDimensionMapper dateDimensionMapper;
    @Autowired
    private PlatformDimensionMapper platformDimensionMapper;
    @Autowired
    private BrowserDimensionMapper browserDimensionMapper;

    private Map<String, Integer> cache = new LinkedHashMap<String, Integer>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
            return cache.size() > 5000;
        }
    };

    @Override
    public int getDimensionIdByValue(BaseDimension dimension) throws IOException {
        String cacheKey = this.buildCacheKey(dimension);
        if (this.cache.containsKey(cacheKey)) {
            LOGGER.info("缓存中存在维度信息，直接获取");
            return this.cache.get(cacheKey);
        }
        int id = 0;
        BaseDimension resultDimension = null;
        if (dimension instanceof DateDimension) {
            DateDimension dateDimension = (DateDimension) dimension;
            resultDimension = this.dateDimensionMapper.getDateDimensionIdByValue(dateDimension);
            if (null == resultDimension) {
                this.dateDimensionMapper.addDateDimension(dateDimension);
                resultDimension = dateDimension;
            }
            id = ((DateDimension) resultDimension).getId();
        } else if (dimension instanceof PlatformDimension) {
            PlatformDimension platformDimension = (PlatformDimension) dimension;
            resultDimension = this.platformDimensionMapper.getPlatformDimensionIdByValue(platformDimension);
            if (null == resultDimension) {
                this.platformDimensionMapper.addPlatformDimension(platformDimension);
                resultDimension = platformDimension;
            }
            id = ((PlatformDimension) resultDimension).getId();
        } else if (dimension instanceof BrowserDimension) {
            BrowserDimension browserDimension = (BrowserDimension) dimension;
            resultDimension = this.browserDimensionMapper.getBrowserDimensionIdByValue(browserDimension);
            if (null == resultDimension) {
                this.browserDimensionMapper.addBrowserDimension(browserDimension);
                resultDimension = browserDimension;
            }
            id = ((BrowserDimension) resultDimension).getId();
        }
        if (id > 0) {
            this.cache.put(cacheKey, id);
            return id;
        }
        throw new RuntimeException("从数据库获取id失败");
    }

    /**
     * 创建cache key
     *
     * @param dimension
     * @return
     */
    private String buildCacheKey(BaseDimension dimension) {
        StringBuilder sb = new StringBuilder();
        if (dimension instanceof DateDimension) {
            sb.append("date_dimension");
            DateDimension dateDimension = (DateDimension) dimension;
            sb.append(dateDimension.getYear())
                    .append(dateDimension.getSeason())
                    .append(dateDimension.getMonth())
                    .append(dateDimension.getWeek())
                    .append(dateDimension.getDay())
                    .append(dateDimension.getType());
        } else if (dimension instanceof PlatformDimension) {
            sb.append("platform_dimension");
            PlatformDimension platformDimension = (PlatformDimension) dimension;
            sb.append(platformDimension.getPlatformName());
        } else if (dimension instanceof BrowserDimension) {
            sb.append("browser_dimension");
            BrowserDimension browserDimension = (BrowserDimension) dimension;
            sb.append(browserDimension.getBrowserName())
                    .append(browserDimension.getBrowserVersion());
        }
        if (sb.length() == 0) {
            throw new RuntimeException("无法创建指定dimension的cachekey：" + dimension.getClass());
        }
        return sb.toString();
    }

}
