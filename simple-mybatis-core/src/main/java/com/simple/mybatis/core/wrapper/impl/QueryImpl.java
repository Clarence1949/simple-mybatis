package com.simple.mybatis.core.wrapper.impl;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import com.simple.mybatis.core.wrapper.ILambdaQuery;
import com.simple.mybatis.core.wrapper.IQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author FanXing
 * @date 2023年02月19日 23:03
 */
public class QueryImpl<T> implements IQuery<T> {
    private final List<String> querySqlSegments = new ArrayList<>();

    private IQuery<T> addSqlSegment(Boolean condition, Supplier<String> sqlSegment) {
        if (condition) querySqlSegments.add(sqlSegment.get());
        return this;
    }

    @Override
    public List<String> getQuerySqlSegments() {
        return querySqlSegments;
    }

    @Override
    public ILambdaQuery<T> lambdaQuery() {
        return (ILambdaQuery<T>) this;
    }

    @Override
    public IQuery<T> eq(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} = {}", property, value));
    }

    @Override
    public IQuery<T> ne(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} <> {}", property, value));
    }

    @Override
    public IQuery<T> gt(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} > {}", property, value));
    }

    @Override
    public IQuery<T> ge(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} >= {}", property, value));
    }

    @Override
    public IQuery<T> lt(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} < {}", property, value));
    }

    @Override
    public IQuery<T> le(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} <= {}", property, value));
    }

    @Override
    public IQuery<T> between(Boolean condition, String property, Object leftVal, Object rightVal) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} BETWEEN {} AND {}", property, leftVal, rightVal));
    }

    @Override
    public IQuery<T> like(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} LIKE '%{}%'", property, value));
    }

    @Override
    public IQuery<T> notLike(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} NOT LIKE '%{}%'", property, value));
    }

    @Override
    public IQuery<T> likeLeft(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} LIKE '%{}'", property, value));
    }

    @Override
    public IQuery<T> likeRight(Boolean condition, String property, Object value) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} LIKE '{}%'", property, value));
    }

    @Override
    public IQuery<T> isNull(Boolean condition, String property) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} IS NULL", property));
    }

    @Override
    public IQuery<T> isNotNull(Boolean condition, String property) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} IS NOT NULL", property));
    }

    @Override
    public IQuery<T> in(Boolean condition, String property, Collection<Object> values) {
        return addSqlSegment(condition, () -> StrFormatter.format("{} IN ({})", property, StrUtil.join(",", values)));
    }
}
