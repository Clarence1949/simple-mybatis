package com.simple.mybatis.core.support;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author FanXing
 * @date 2023年02月21日 16:05
 */
public interface SQLSegmentHandler {
    String PATTERN_SELECT = "SELECT {} FROM {} ";
    String PATTERN_COUNT = "SELECT COUNT(*) FROM {} ";
    String WHERE = " WHERE ";
    String AND = " AND ";
    String EQUAL = " = ";
    String PATTERN_INSERT = "INSERT INTO {} ({}) VALUES ({})";
    String PATTERN_UPDATE = "UPDATE {} SET ({}) ";
    String PATTERN_DELETE = "DELETE FROM {} ";

    static <T> String convertColumnAndValList(T t, TableInfo tableInfo, BiFunction<List<String>, List<String>, String> biFunction, boolean ignoreKey) {
        return convertColumnMap(t, tableInfo, (columnMap, keyMap) -> {
            List<String> columns = new ArrayList<>();
            List<String> vals = new ArrayList<>();
            if (!ignoreKey) columnMap.putAll(keyMap);
            columnMap.entrySet().forEach(entry -> {
                columns.add(entry.getKey());
                vals.add((String) entry.getValue());
            });
            return biFunction.apply(columns, vals);
        });
    }

    static String parseSelectSqlSegment(TableInfo tableInfo) {
        List<String> fullColumns = tableInfo.getFullFields().stream().map(TableFieldInfo::getColumn).collect(Collectors.toList());
        String selectSqlSegment = StrFormatter.format(PATTERN_SELECT, fullColumns, tableInfo.getTableName());
        return selectSqlSegment;
    }

    static String parseCountSqlSegment(TableInfo tableInfo) {
        return StrFormatter.format(PATTERN_COUNT, tableInfo.getTableName());
    }

    static <T> String parseEqSqlSegment(T t, TableInfo tableInfo, boolean ignoreKey) {
        return convertColumnMap(t, tableInfo, (columnMap, keyMap) -> {
            if (!ignoreKey) columnMap.putAll(keyMap);
            List<String> eqExpress = columnMap.entrySet().stream().map(entry -> entry.getKey() + EQUAL + entry.getValue()).collect(Collectors.toList());
            String eqSqlSegment = StrUtil.join(AND, eqExpress);
            return eqSqlSegment;
        });
    }

    static <T> String convertColumnMap(T t, TableInfo tableInfo, BiFunction<Map<String, Object>, Map<String, Object>, String> biFunction) {
        Map<String, Object> columnMap = new LinkedHashMap<>();
        Map<String, Object> keyMap = new HashMap<>();
        Map<String, TableFieldInfo> fieldInfoMap = tableInfo.getFieldInfoMap();
        JSONObject jsonObject = JSONUtil.parseObj(t, true);
        for (Map.Entry<String, Object> entry : jsonObject) {
            TableFieldInfo fieldInfo = fieldInfoMap.get(entry.getKey());
            if (fieldInfo.isKey()) keyMap.put(fieldInfo.getColumn(), entry.getValue());
            else columnMap.put(fieldInfo.getColumn(), entry.getValue());
        }
        return biFunction.apply(columnMap, keyMap);
    }
}
