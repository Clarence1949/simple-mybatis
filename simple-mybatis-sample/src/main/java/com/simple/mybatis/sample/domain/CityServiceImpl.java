package com.simple.mybatis.sample.domain;

import com.simple.mybatis.core.service.impl.ServiceImpl;
import com.simple.mybatis.sample.mapper.CityMapper;
import org.springframework.stereotype.Service;

/**
 * @author FanXing
 * @date 2023年02月20日 17:08
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {
}
