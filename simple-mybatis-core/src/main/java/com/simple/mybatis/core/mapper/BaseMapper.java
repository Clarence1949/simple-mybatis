package com.simple.mybatis.core.mapper;

import com.simple.mybatis.core.support.SQLGenerator;
import com.simple.mybatis.core.wrapper.Wrapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author FanXing
 * @date 2023年02月16日 11:15
 */
public interface BaseMapper<T> {
    @SelectProvider(value = SQLGenerator.class, method = "selectByExample")
    List<T> selectByExample(T t);

    @SelectProvider(value = SQLGenerator.class, method = "select")
    List<T> select(Wrapper<T> wrapper);

    @SelectProvider(value = SQLGenerator.class, method = "countByExample")
    long countByExample(T t);

    @SelectProvider(value = SQLGenerator.class, method = "count")
    long count(Wrapper<T> wrapper);

    @SelectProvider(value = SQLGenerator.class, method = "insert")
    int insert(T t);

    @SelectProvider(value = SQLGenerator.class, method = "updateById")
    int updateById(T t);

    @SelectProvider(value = SQLGenerator.class, method = "update")
    int update(T t, Wrapper<T> wrapper);

    @SelectProvider(value = SQLGenerator.class, method = "deleteByExample")
    int deleteByExample(T t);

    @SelectProvider(value = SQLGenerator.class, method = "delete")
    int delete(Wrapper<T> wrapper);

}
