package com.simple.mybatis.core.service;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.simple.mybatis.core.mapper.BaseMapper;
import com.simple.mybatis.core.page.Page;
import com.simple.mybatis.core.wrapper.Wrapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author FanXing
 * @date 2023年02月16日 11:26
 */
public interface IService<T> {
    int DEFAULT_BATCH_SIZE = 1000;

    BaseMapper<T> getMapper();

    default List<T> pageByExample(T t, Page<T> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return getMapper().selectByExample(t);
    }

    default List<T> queryByExample(T t) {
        return getMapper().selectByExample(t);
    }

    default List<T> page(Wrapper<T> wrapper, Page<T> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return getMapper().select(wrapper);
    }

    default List<T> query(Wrapper<T> wrapper) {
        return getMapper().select(wrapper);
    }

    default long countByExample(T t) {
        return getMapper().countByExample(t);
    }

    default long count(Wrapper<T> wrapper) {
        return getMapper().count(wrapper);
    }

    default T getOne(Wrapper<T> wrapper) {
        List<T> list = getMapper().select(wrapper);
        return CollUtil.isNotEmpty(list) ? list.get(0) : null;
    }

    T getById(Serializable id);

    default boolean save(T t) {
        return getMapper().insert(t) > 0;
    }

    default boolean modifyById(T t) {
        return getMapper().updateById(t) > 0;
    }

    default boolean modify(T t, Wrapper<T> wrapper) {
        return getMapper().update(t, wrapper) > 0;
    }

    boolean removeByIds(Collection<Serializable> ids);

    default boolean removeByExample(T t) {
        return getMapper().deleteByExample(t) > 0;
    }

    default boolean remove(Wrapper<T> wrapper) {
        return getMapper().delete(wrapper) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    boolean saveBatch(Collection<T> entityList, int batchSize);
}
