package com.simple.mybatis.core.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author FanXing
 * @date 2023年02月16日 11:27
 */
public interface IRelService<T> extends IService<T> {
    /**
     * 关联保存
     *
     * @param t
     * @return
     */
    boolean relSave(T t);

    /**
     * 关联查询
     *
     * @param id
     * @return
     */
    T relGetById(Serializable id);

    /**
     * 关联更新
     *
     * @param t
     * @return
     */
    Boolean relUpdateById(T t);

    /**
     * 关联删除
     *
     * @param ids
     * @return
     */
    Boolean relRemoveByIds(List<Serializable> ids);
}
