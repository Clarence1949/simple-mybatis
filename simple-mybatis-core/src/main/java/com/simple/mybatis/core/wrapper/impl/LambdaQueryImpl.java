package com.simple.mybatis.core.wrapper.impl;

import com.simple.mybatis.core.util.LambdaUtil;
import com.simple.mybatis.core.util.SFunction;
import com.simple.mybatis.core.wrapper.ILambdaQuery;

import java.util.Collection;

/**
 * @author FanXing
 * @date 2023年02月19日 13:59
 */
public class LambdaQueryImpl<T> extends QueryImpl<T> implements ILambdaQuery<T> {
    public LambdaQueryImpl() {
        this(null);
    }

    public LambdaQueryImpl(T entity) {
        super(entity);
    }

    @Override
    public <R> ILambdaQuery<T> eq(Boolean condition, SFunction<T, R> property, R value) {
        return eq(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> ne(Boolean condition, SFunction<T, R> property, R value) {
        return ne(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> gt(Boolean condition, SFunction<T, R> property, R value) {
        return gt(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> ge(Boolean condition, SFunction<T, R> property, R value) {
        return ge(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> lt(Boolean condition, SFunction<T, R> property, R value) {
        return lt(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> le(Boolean condition, SFunction<T, R> property, R value) {
        return le(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> between(Boolean condition, SFunction<T, R> property, R leftVal, R rightVal) {
        return between(condition, LambdaUtil.propertyToString(property), leftVal, rightVal).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> like(Boolean condition, SFunction<T, R> property, R value) {
        return like(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> notLike(Boolean condition, SFunction<T, R> property, R value) {
        return notLike(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> likeLeft(Boolean condition, SFunction<T, R> property, R value) {
        return likeLeft(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> likeRight(Boolean condition, SFunction<T, R> property, R value) {
        return likeRight(condition, LambdaUtil.propertyToString(property), value).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> isNull(Boolean condition, SFunction<T, R> property) {
        return isNull(condition, LambdaUtil.propertyToString(property)).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> isNotNull(Boolean condition, SFunction<T, R> property) {
        return isNotNull(condition, LambdaUtil.propertyToString(property)).lambdaQuery();
    }

    @Override
    public <R> ILambdaQuery<T> in(Boolean condition, SFunction<T, R> property, Collection<R> values) {
        return in(condition, LambdaUtil.propertyToString(property), (Collection<Object>) values).lambdaQuery();
    }
}
