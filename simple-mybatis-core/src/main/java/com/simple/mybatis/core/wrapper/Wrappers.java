package com.simple.mybatis.core.wrapper;

import com.simple.mybatis.core.wrapper.impl.LambdaQueryImpl;
import com.simple.mybatis.core.wrapper.impl.QueryImpl;
import lombok.experimental.UtilityClass;

/**
 * @author FanXing
 * @date 2023年02月19日 21:52
 */
@UtilityClass
public class Wrappers {
    /**
     * lambda查询包装类
     *
     * @param <T>
     * @return
     */
    public <T> ILambdaQuery<T> lambdaQuery() {
        return new LambdaQueryImpl<>();
    }

    public <T> ILambdaQuery<T> lambdaQuery(T entity) {
        return new LambdaQueryImpl<>(entity);
    }

    /**
     * 查询包装类
     *
     * @param <T>
     * @return
     */
    public <T> IQuery<T> query() {
        return new QueryImpl<>();
    }

    public <T> IQuery<T> query(T entity) {
        return new QueryImpl<>(entity);
    }
}
