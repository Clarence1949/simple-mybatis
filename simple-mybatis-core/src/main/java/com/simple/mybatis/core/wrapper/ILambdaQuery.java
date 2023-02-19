package com.simple.mybatis.core.wrapper;

import com.simple.mybatis.core.util.SFunction;

import java.util.Collection;

/**
 * @author FanXing
 * @date 2023年02月19日 13:37
 */
public interface ILambdaQuery<T> extends IQuery<T> {

    /**
     * 等于
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> eq(Boolean condition, SFunction<T, R> property, R value);

    /**
     * 不等于
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> ne(Boolean condition, SFunction<T, R> property, R value);

    /**
     * 大于
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> gt(Boolean condition, SFunction<T, R> property, R value);

    /**
     * 大于等于
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> ge(Boolean condition, SFunction<T, R> property, R value);

    /**
     * 小于
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> lt(Boolean condition, SFunction<T, R> property, R value);

    /**
     * 小于等于
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> le(Boolean condition, SFunction<T, R> property, R value);

    /**
     * 介于两值之间
     *
     * @param condition
     * @param property
     * @param leftVal
     * @param rightVal
     * @param <R>
     * @return
     */
    <R> IQuery<T> between(Boolean condition, SFunction<T, R> property, R leftVal, R rightVal);

    /**
     * LIKE '%VAL%'
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> like(Boolean condition, SFunction<T, R> property, R value);

    /**
     * NOT LIKE '%VAL%'
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> notLike(Boolean condition, SFunction<T, R> property, R value);

    /**
     * LIKE '%VAL'
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> likeLeft(Boolean condition, SFunction<T, R> property, R value);

    /**
     * LIKE 'VAL%'
     *
     * @param condition
     * @param property
     * @param value
     * @param <R>
     * @return
     */
    <R> IQuery<T> likeRight(Boolean condition, SFunction<T, R> property, R value);

    /**
     * IS NULL
     *
     * @param condition
     * @param property
     * @param <R>
     * @return
     */
    <R> IQuery<T> isNull(Boolean condition, SFunction<T, R> property);

    /**
     * IS NOT NULL
     *
     * @param condition
     * @param property
     * @param <R>
     * @return
     */
    <R> IQuery<T> isNotNull(Boolean condition, SFunction<T, R> property);

    /**
     * IN (...)
     *
     * @param condition
     * @param property
     * @param values
     * @param <R>
     * @return
     */
    <R> IQuery<T> in(Boolean condition, SFunction<T, R> property, Collection<R> values);

}
