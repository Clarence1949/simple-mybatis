package com.simple.mybatis.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.db.DbRuntimeException;
import com.simple.mybatis.core.mapper.BaseMapper;
import com.simple.mybatis.core.service.IRelService;
import com.simple.mybatis.core.service.IService;
import com.simple.mybatis.core.support.TableFieldInfo;
import com.simple.mybatis.core.util.LambdaUtil;
import com.simple.mybatis.core.util.SFunction;
import com.simple.mybatis.core.wrapper.Wrappers;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * @author FanXing
 * @date 2023年02月16日 11:27
 */
public abstract class RelServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IRelService<T> {
    @Autowired
    private ApplicationContext applicationContext;
    private final List<RelPropertySetter<?>> relPropertySetters = new ArrayList<>();
    private final List<RelPropertyGetter<?>> relPropertyGetters = new ArrayList<>();
    private String idField;

    protected final <R> void addRelProperty(SFunction<T, List<R>> mainRelListProperty, SFunction<R, Serializable> relMainIdProperty, Class<? extends IService<R>> relServiceClass) {
        relPropertySetters.add(new RelPropertySetter<>(mainRelListProperty, relMainIdProperty, relServiceClass));
    }

    protected abstract void initRelProperties();

    @PostConstruct
    private void init() {
        Class<T> entityClazz = currentModelClass();
        TableFieldInfo fieldInfo = tableInfoBuilder.getTableInfo(entityClazz).getKeyField();
        Objects.requireNonNull(fieldInfo, StrFormatter.format("实体 {} 主键不存在", entityClazz.getCanonicalName()));

        idField = fieldInfo.getField().getName();
        initRelProperties();
        relPropertySetters.forEach(setter -> {
            RelPropertyGetter<?> getter = new RelPropertyGetter(LambdaUtil.propertyToString(setter.getMainRelListProperty()), setter.getRelMainIdProperty(), LambdaUtil.propertyToString(setter.getRelMainIdProperty()), applicationContext.getBean(setter.getRelServiceClass()));
            relPropertyGetters.add(getter);
        });
    }

    @Override
    public boolean relSave(T t) {
        return defaultSave(t, t1 -> saveMultiRels(t1));
    }

    protected final Boolean defaultSave(T t, Consumer<T> consumer) {
        Boolean bool = save(t);
        if (bool) {
            Objects.requireNonNull(consumer).accept(t);
        } else {
            throw new DbRuntimeException("保存数据失败");
        }
        return bool;
    }

    private void saveMultiRels(T t) {
        Object actualId = BeanUtil.getFieldValue(t, idField);
        relPropertyGetters.forEach(relPropertyGetter -> {
            saveRels(t, actualId, relPropertyGetter);
        });
    }

    private <R> void saveRels(T t, Object actualId, RelPropertyGetter<R> relPropertyGetter) {
        List<R> rels = (List<R>) BeanUtil.getFieldValue(t, relPropertyGetter.getMainRelListField());
        if (null != actualId && CollUtil.isNotEmpty(rels)) {
            List<R> relData = rels.stream().map(rel -> {
                BeanUtil.setFieldValue(rel, relPropertyGetter.getRelMainIdField(), actualId);
                return rel;
            }).collect(Collectors.toList());
            relPropertyGetter.getRelService().saveBatch(relData);
        }
    }

    @Override
    public T relGetById(Serializable id) {
        return defaultGetById(id, t -> {
            relPropertyGetters.forEach(relProperties -> {
                BeanUtil.setFieldValue(t, relProperties.getMainRelListField(), getRels(id, relProperties));
            });
            return t;
        });
    }

    protected final T defaultGetById(Serializable id, UnaryOperator<T> operator) {
        Objects.requireNonNull(operator);
        T t = getById(id);
        if (Objects.nonNull(t)) {
            operator.apply(t);
        }
        return t;
    }

    private <R> List<R> getRels(Serializable id, RelPropertyGetter<R> relProperties) {
        return relProperties.getRelService().query(Wrappers.<R>lambdaQuery().eq(Objects.nonNull(id), relProperties.getRelMainIdProperty(), id));
    }

    @Override
    public Boolean relUpdateById(T t) {
        Serializable actualId = (Serializable) BeanUtil.getFieldValue(t, idField);
        return defaultUpdateById(t, t1 -> {
            relPropertyGetters.forEach(relPropertyGetter -> {
                List<?> rels = (List) BeanUtil.getFieldValue(t, relPropertyGetter.getMainRelListField());
                if (rels != null) {
                    deleteRels(Collections.singletonList(actualId), relPropertyGetter);
                    saveRels(t1, actualId, relPropertyGetter);
                }
            });
        });
    }

    protected final Boolean defaultUpdateById(T t, Consumer<T> consumer) {
        Boolean bool = modifyById(t);
        if (bool) {
            Objects.requireNonNull(consumer).accept(t);
        } else {
            throw new DbRuntimeException("更新数据失败");
        }
        return bool;
    }

    @Override
    public Boolean relRemoveByIds(List<Serializable> ids) {
        return defaultRemoveByIds(ids, ids1 -> relPropertyGetters.forEach(relPropertyGetter -> {
            deleteRels(ids1, relPropertyGetter);
        }));
    }

    protected final Boolean defaultRemoveByIds(List<Serializable> ids, Consumer<List<Serializable>> consumer) {
        Boolean bool = removeByIds(ids);
        if (bool) {
            Objects.requireNonNull(consumer).accept(ids);
        } else {
            throw new DbRuntimeException("删除数据失败");
        }
        return bool;
    }

    private <R> void deleteRels(List<Serializable> actualIds, RelPropertyGetter<R> relPropertyGetter) {
        relPropertyGetter.getRelService().remove(Wrappers.<R>lambdaQuery().in(CollUtil.isNotEmpty(actualIds), relPropertyGetter.getRelMainIdProperty(), actualIds));
    }

    @Data
    @AllArgsConstructor
    private class RelPropertySetter<R> {
        SFunction<T, List<R>> mainRelListProperty;
        SFunction<R, Serializable> relMainIdProperty;
        Class<? extends IService<R>> relServiceClass;
    }

    @Data
    @AllArgsConstructor
    private class RelPropertyGetter<R> {
        String mainRelListField;
        SFunction<R, Serializable> relMainIdProperty;
        String relMainIdField;
        IService<R> relService;
    }
}
