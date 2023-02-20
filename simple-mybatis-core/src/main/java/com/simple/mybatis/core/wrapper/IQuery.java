package com.simple.mybatis.core.wrapper;

import java.util.Collection;

/**
 * @author FanXing
 * @date 2023年02月19日 22:57
 */
public interface IQuery<T> extends Wrapper<T> {
    ILambdaQuery<T> lambdaQuery();

    /**
     * 等于
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> eq(Boolean condition, String property, Object value);

    /**
     * 不等于
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> ne(Boolean condition, String property, Object value);

    /**
     * 大于
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> gt(Boolean condition, String property, Object value);

    /**
     * 大于等于
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> ge(Boolean condition, String property, Object value);

    /**
     * 小于
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> lt(Boolean condition, String property, Object value);

    /**
     * 小于等于
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> le(Boolean condition, String property, Object value);

    /**
     * 介于两值之间
     *
     * @param condition
     * @param property
     * @param leftVal
     * @param rightVal
     * @return
     */
    IQuery<T> between(Boolean condition, String property, Object leftVal, Object rightVal);

    /**
     * LIKE '%VAL%'
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> like(Boolean condition, String property, Object value);

    /**
     * NOT LIKE '%VAL%'
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> notLike(Boolean condition, String property, Object value);

    /**
     * LIKE '%VAL'
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> likeLeft(Boolean condition, String property, Object value);

    /**
     * LIKE 'VAL%'
     *
     * @param condition
     * @param property
     * @param value
     * @return
     */
    IQuery<T> likeRight(Boolean condition, String property, Object value);

    /**
     * IS NULL
     *
     * @param condition
     * @param property
     * @return
     */
    IQuery<T> isNull(Boolean condition, String property);

    /**
     * IS NOT NULL
     *
     * @param condition
     * @param property
     * @return
     */
    IQuery<T> isNotNull(Boolean condition, String property);

    /**
     * IN (...)
     *
     * @param condition
     * @param property
     * @param values
     * @return
     */
    IQuery<T> in(Boolean condition, String property, Collection<Object> values);
}
