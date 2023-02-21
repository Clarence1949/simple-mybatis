package com.simple.mybatis.core.support;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author FanXing
 * @date 2023年02月19日 19:43
 */
@Data
public class TableFieldInfo {
    /**
     * 属性
     */
    private Field field;
    /**
     * 表字段
     */
    private String column;
    /**
     * 是否主键
     */
    private boolean isKey;
    /**
     * 是否逻辑删除字段
     */
    private boolean isLogic;

    public TableFieldInfo(Field field, String column) {
        this.field = field;
        this.column = column;
    }
}
