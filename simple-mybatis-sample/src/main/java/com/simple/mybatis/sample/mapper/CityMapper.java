package com.simple.mybatis.sample.mapper;

import com.simple.mybatis.core.mapper.BaseMapper;
import com.simple.mybatis.sample.domain.City;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author FanXing
 * @date 2023年02月16日 10:43
 */
@Mapper
public interface CityMapper extends BaseMapper<City> {
    @Override
    int insert(City city);
}
