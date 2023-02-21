package com.simple.mybatis.core.support;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import com.simple.mybatis.core.util.SpringContextUtil;
import com.simple.mybatis.core.wrapper.Wrapper;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

import static com.simple.mybatis.core.support.SQLSegmentHandler.*;

/**
 * @author FanXing
 * @date 2023年02月19日 18:51
 */
@UtilityClass
public class SQLGenerator {
    private final TableInfoBuilder TABLE_INFO_BUILDER = SpringContextUtil.getBean(TableInfoBuilder.class);

    private <T> TableInfo getTableInfo(T t) {
        return TABLE_INFO_BUILDER.getTableInfo(t.getClass());
    }

    public <T> String selectByExample(T t) {
        TableInfo tableInfo = getTableInfo(t);
        return parseSelectSqlSegment(tableInfo) + WHERE + parseEqSqlSegment(t, tableInfo, false);
    }

    public <T> String select(Wrapper<T> wrapper) {
        TableInfo tableInfo = getTableInfo(wrapper.getEntityClass());
        String wrapperSqlSegment = StrUtil.join(AND, wrapper.getQuerySqlSegments());
        return parseSelectSqlSegment(tableInfo) + WHERE + wrapperSqlSegment;
    }

    public <T> String countByExample(T t) {
        TableInfo tableInfo = getTableInfo(t);
        return parseCountSqlSegment(tableInfo) + WHERE + parseEqSqlSegment(t, tableInfo, false);
    }

    public <T> String count(Wrapper<T> wrapper) {
        TableInfo tableInfo = getTableInfo(wrapper.getEntityClass());
        String wrapperSqlSegment = StrUtil.join(AND, wrapper.getQuerySqlSegments());
        return parseCountSqlSegment(tableInfo) + WHERE + wrapperSqlSegment;
    }

    public <T> String insert(T t) {
        TableInfo tableInfo = getTableInfo(t);
        return convertColumnAndValList(t, tableInfo, (columns, vals) -> StrFormatter.format(PATTERN_INSERT, tableInfo.getTableName(), StrUtil.join(",", columns), StrUtil.join(",", vals)), true);
    }

    public <T> String updateById(T t) {
        TableInfo tableInfo = getTableInfo(t);
        return convertColumnMap(t, tableInfo, (columnMap, keyMap) -> {
            List<String> eqExpress = columnMap.entrySet().stream().map(entry -> entry.getKey() + EQUAL + entry.getValue()).collect(Collectors.toList());
            String eqSqlSegment = StrUtil.join(AND, eqExpress);
            String keySqlSegment = keyMap.entrySet().stream().map(entry -> entry.getKey() + EQUAL + entry.getValue()).findFirst().get();
            return StrFormatter.format(PATTERN_UPDATE, tableInfo.getTableName(), eqSqlSegment) + WHERE + keySqlSegment;
        });
    }

    public <T> String update(T t, Wrapper<T> wrapper) {
        TableInfo tableInfo = getTableInfo(wrapper.getEntityClass());
        return convertColumnMap(t, tableInfo, (columnMap, keyMap) -> {
            List<String> eqExpress = columnMap.entrySet().stream().map(entry -> entry.getKey() + EQUAL + entry.getValue()).collect(Collectors.toList());
            String eqSqlSegment = StrUtil.join(AND, eqExpress);
            String querySqlSegment = StrUtil.join(AND, wrapper.getQuerySqlSegments());
            return StrFormatter.format(PATTERN_UPDATE, tableInfo.getTableName(), eqSqlSegment) + WHERE + querySqlSegment;
        });
    }

    public <T> String deleteByExample(T t) {
        TableInfo tableInfo = getTableInfo(t);
        return StrFormatter.format(PATTERN_DELETE, tableInfo.getTableName()) + WHERE + parseEqSqlSegment(t, tableInfo, false);
    }

    public <T> String delete(Wrapper<T> wrapper) {
        TableInfo tableInfo = getTableInfo(wrapper.getEntityClass());
        return StrFormatter.format(PATTERN_DELETE, tableInfo.getTableName()) + WHERE + StrUtil.join(AND, wrapper.getQuerySqlSegments());
    }

}
