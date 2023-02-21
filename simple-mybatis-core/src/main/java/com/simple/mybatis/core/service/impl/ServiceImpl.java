package com.simple.mybatis.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.db.DbRuntimeException;
import com.simple.mybatis.core.mapper.BaseMapper;
import com.simple.mybatis.core.service.IService;
import com.simple.mybatis.core.support.TableFieldInfo;
import com.simple.mybatis.core.support.TableInfo;
import com.simple.mybatis.core.support.TableInfoBuilder;
import com.simple.mybatis.core.wrapper.Wrappers;
import lombok.SneakyThrows;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author FanXing
 * @date 2023年02月16日 11:26
 */
public class ServiceImpl<M extends BaseMapper<T>, T> implements IService<T> {
    @Autowired
    protected M mapper;
    @Autowired
    protected TableInfoBuilder tableInfoBuilder;
    @Autowired
    protected SqlSessionFactory sqlSessionFactory;
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public BaseMapper<T> getMapper() {
        return mapper;
    }

    public Class currentModelClass() {
        return (Class) TypeUtil.getTypeArgument(getClass(), 1);
    }

    @SneakyThrows
    @Override
    public T getById(Serializable id) {
        return getOne(Wrappers.<T>query().eq(true, getKeyField(), id));
    }

    private String getKeyField() {
        Class<T> entityClazz = currentModelClass();
        TableInfo tableInfo = tableInfoBuilder.getTableInfo(entityClazz);
        TableFieldInfo fieldInfo = tableInfo.getKeyField();
        Objects.requireNonNull(fieldInfo, StrFormatter.format("实体 {} 主键不存在", entityClazz.getCanonicalName()));
        return fieldInfo.getField().getName();
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        if (CollUtil.isEmpty(ids)) throw new IllegalArgumentException("参数不能为空");
        return remove(Wrappers.<T>query().in(true, getKeyField(), Arrays.asList(ids.toArray())));
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            sqlSession.getMapper(mapper.getClass()).insert(entity);
        });
    }

    private boolean executeBatch(Collection<T> entityList, int batchSize, BiConsumer<SqlSession, T> consumer) {
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            multiProcess((List<T>) entityList, batchSize, list -> {
                for (T entity : list)
                    consumer.accept(sqlSession, entity);
//            非事务情况下，批量操作强制提交，避免大数据量下缓存占用
                sqlSession.commit(!transaction);
                return true;
            });
        }
        return true;
    }

    @SneakyThrows
    private Boolean multiProcess(List<T> data, int batchSize, Function<List<T>, Boolean> func) {
        List<Boolean> bools = new ArrayList<>();
        List<List<T>> partitions = ListUtil.partition(data, batchSize);
        final CountDownLatch latch = new CountDownLatch(partitions.size());
        partitions.forEach(partition -> executor.execute(() -> {
            try {
                bools.add(func.apply(partition));
            } finally {
                latch.countDown();
            }
        }));
        latch.await();
        if (bools.stream().filter(bool -> bool).count() != partitions.size())
            throw new DbRuntimeException("多线程分片保存数据失败");
        return Boolean.TRUE;
    }
}
