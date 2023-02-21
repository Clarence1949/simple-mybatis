package com.simple.mybatis.core.support;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.simple.mybatis.annotation.TableField;
import com.simple.mybatis.annotation.TableId;
import com.simple.mybatis.annotation.TableLogic;
import com.simple.mybatis.annotation.TableName;
import com.simple.mybatis.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author FanXing
 * @date 2023年02月19日 16:33
 */
@Slf4j
@Component
public class TableInfoBuilder {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Map<Class<?>, TableInfo> TABLE_INFO_CACHE = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(Mapper.class);
        for (Object bean : map.values()) {
            if (bean instanceof BaseMapper<?>) {
                TableInfo tableInfo = new TableInfo();
                List<Field> unNormalFields = new ArrayList<>();

                BaseMapper<?> mapper = (BaseMapper<?>) bean;
                Class<?> entityClazz = (Class<?>) TypeUtil.getTypeArgument(mapper.getClass().getGenericInterfaces()[0], 0);
                tableInfo.setEntityClazz(entityClazz);

                String tableName = StrUtil.toUnderlineCase(entityClazz.getSimpleName());
                if (AnnotationUtil.hasAnnotation(entityClazz, TableName.class)) {
                    tableName = AnnotationUtil.getAnnotation(entityClazz, TableName.class).value();
                }
                tableInfo.setTableName(tableName);

                Field[] tableIds = ReflectUtil.getFields(entityClazz, field -> field.isAnnotationPresent(TableId.class));
                if (null != tableIds && tableIds.length == 1) {
                    Field key = tableIds[0];
                    String keyColumn = StrUtil.toUnderlineCase(key.getName());
                    String tableIdVal = key.getAnnotation(TableId.class).value();
                    if (StrUtil.isNotBlank(tableIdVal)) keyColumn = tableIdVal;
                    TableFieldInfo fieldInfo = new TableFieldInfo(key, keyColumn);
                    fieldInfo.setKey(true);
                    tableInfo.setKeyField(fieldInfo);
                    unNormalFields.add(key);
                }

                Field[] logicFields = ReflectUtil.getFields(entityClazz, field -> field.isAnnotationPresent(TableLogic.class));
                if (null != logicFields && logicFields.length == 1) {
                    Field logicField = logicFields[0];
                    String logicColumn = StrUtil.toUnderlineCase(logicField.getName());
                    String logicVal = logicField.getAnnotation(TableLogic.class).value();
                    if (StrUtil.isNotBlank(logicVal)) logicColumn = logicVal;
                    TableFieldInfo fieldInfo = new TableFieldInfo(logicField, logicColumn);
                    fieldInfo.setLogic(true);
                    tableInfo.setLogicField(fieldInfo);
                    unNormalFields.add(logicField);
                }

                Field[] normalFields = ReflectUtil.getFields(entityClazz, field -> !unNormalFields.contains(field) && checkField(field));
                if (null != normalFields && normalFields.length > 0) {
                    List<TableFieldInfo> normalColumns = new ArrayList<>();
                    for (Field field : normalFields) {
                        String column = StrUtil.toUnderlineCase(field.getName());
                        if (field.isAnnotationPresent(TableField.class)) {
                            column = field.getAnnotation(TableField.class).value();
                        }
                        normalColumns.add(new TableFieldInfo(field, column));
                    }
                    tableInfo.setNormalFields(normalColumns);
                }

                if (Objects.isNull(tableInfo.getKeyField())) log.warn("entity {} don't have key column", entityClazz);

                List<TableFieldInfo> fullColumns = new ArrayList<>();
                if (Objects.nonNull(tableInfo.getKeyField())) {
                    fullColumns.add(tableInfo.getKeyField());
                }
                fullColumns.addAll(tableInfo.getNormalFields());
                if (Objects.nonNull(tableInfo.getLogicField())) {
                    fullColumns.add(tableInfo.getLogicField());
                }
                tableInfo.setFullFields(fullColumns);
                Map<String, TableFieldInfo> fieldInfoMap = fullColumns.stream().collect(Collectors.toMap(tableFieldInfo -> tableFieldInfo.getField().getName(), Function.identity()));
                tableInfo.setFieldInfoMap(fieldInfoMap);

                TABLE_INFO_CACHE.put(entityClazz, tableInfo);
            }
        }
    }

    public TableInfo getTableInfo(Class<?> entityClazz) {
        return TABLE_INFO_CACHE.get(entityClazz);
    }

    private boolean checkField(Field field) {
        int modifiers = field.getModifiers();
        return !Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers);
    }
}
