package com.simple.mybatis.sample.controller;


import com.simple.mybatis.core.page.Page;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author FanXing
 * @date 2022年11月17日 9:13
 */
public interface IController<T> {

    ResponseEntity<Page<T>> page(T t, Page<T> page);

    ResponseEntity<T> get(Serializable id);

    ResponseEntity<Boolean> add(T t);

    ResponseEntity<Boolean> update(T t);

    ResponseEntity<Boolean> delete(List<Serializable> ids);

}
