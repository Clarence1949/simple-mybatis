package com.simple.mybatis.sample.mapper;

import com.simple.mybatis.core.mapper.BaseMapper;
import com.simple.mybatis.sample.domain.TestEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author FanXing
 * @date 2023年02月21日 9:29
 */
@Mapper
public interface TestEntityMapper extends BaseMapper<TestEntity> {
}
