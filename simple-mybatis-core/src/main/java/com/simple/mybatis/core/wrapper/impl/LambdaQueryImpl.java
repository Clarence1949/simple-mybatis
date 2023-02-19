package com.simple.mybatis.core.wrapper.impl;

import com.simple.mybatis.core.util.LambdaUtil;
import com.simple.mybatis.core.util.SFunction;
import com.simple.mybatis.core.wrapper.ILambdaQuery;
import com.simple.mybatis.core.wrapper.IQuery;

import java.util.Collection;

/**
 * @author FanXing
 * @date 2023年02月19日 13:59
 */
public class LambdaQueryImpl<T> extends QueryImpl<T> implements ILambdaQuery<T> {

    @Override
    public <R> IQuery<T> eq(Boolean condition, SFunction<T, R> property, R value) {
        return eq(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> ne(Boolean condition, SFunction<T, R> property, R value) {
        return ne(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> gt(Boolean condition, SFunction<T, R> property, R value) {
        return gt(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> ge(Boolean condition, SFunction<T, R> property, R value) {
        return ge(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> lt(Boolean condition, SFunction<T, R> property, R value) {
        return lt(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> le(Boolean condition, SFunction<T, R> property, R value) {
        return le(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> between(Boolean condition, SFunction<T, R> property, R leftVal, R rightVal) {
        return between(condition, LambdaUtil.propertyToString(property), leftVal, rightVal);
    }

    @Override
    public <R> IQuery<T> like(Boolean condition, SFunction<T, R> property, R value) {
        return like(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> notLike(Boolean condition, SFunction<T, R> property, R value) {
        return notLike(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> likeLeft(Boolean condition, SFunction<T, R> property, R value) {
        return likeLeft(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> likeRight(Boolean condition, SFunction<T, R> property, R value) {
        return likeRight(condition, LambdaUtil.propertyToString(property), value);
    }

    @Override
    public <R> IQuery<T> isNull(Boolean condition, SFunction<T, R> property) {
        return isNull(condition, LambdaUtil.propertyToString(property));
    }

    @Override
    public <R> IQuery<T> isNotNull(Boolean condition, SFunction<T, R> property) {
        return isNotNull(condition, LambdaUtil.propertyToString(property));
    }

    @Override
    public <R> IQuery<T> in(Boolean condition, SFunction<T, R> property, Collection<R> values) {
        return in(condition, LambdaUtil.propertyToString(property), (Collection<Object>) values);
    }
}
