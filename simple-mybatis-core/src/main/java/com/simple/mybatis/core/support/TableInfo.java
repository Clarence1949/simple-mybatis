package com.simple.mybatis.core.support;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 表信息
 *
 * @author FanXing
 * @date 2023年02月19日 16:32
 */
@Data
public class TableInfo {
    /**
     * 实体名称
     */
    private Class<?> entityClazz;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 主键
     */
    private TableFieldInfo keyField;
    /**
     * 逻辑删除
     */
    private TableFieldInfo logicField;
    /**
     * 正常field集合
     */
    private List<TableFieldInfo> normalFields;
    /**
     * 完整字段
     */
    private List<TableFieldInfo> fullFields;
    /**
     * 属性映射字段信息
     */
    private Map<String, TableFieldInfo> fieldInfoMap;
}
