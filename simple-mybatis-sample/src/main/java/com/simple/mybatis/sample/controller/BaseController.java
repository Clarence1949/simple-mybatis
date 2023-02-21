package com.simple.mybatis.sample.controller;

import com.simple.mybatis.core.page.Page;
import com.simple.mybatis.core.service.IService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 接口控制器 基础类
 *
 * @author FanXing
 * @date 2022年11月17日 9:19
 */
public abstract class BaseController<T, S extends IService<T>> implements IController<T> {

    @Autowired
    protected S service;

    @Override
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public ResponseEntity<Page<T>> page(T t, Page<T> page) {
        return ResponseEntity.ok(defaultPage(t, page));
    }

    @Override
    @ApiOperation("详情")
    @GetMapping("/{id}")
    public ResponseEntity<T> get(@PathVariable("id") Serializable id) {
        return ResponseEntity.ok(defaultGet(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("新增")
    @PostMapping
    public ResponseEntity<Boolean> add(@RequestBody T t) {
        return ResponseEntity.ok(defaultAdd(t));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("更新")
    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody T t) {
        return ResponseEntity.ok(defaultUpdate(t));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("批量删除")
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestParam List<Serializable> ids) {
        return ResponseEntity.ok(defaultDelete(ids));
    }

    protected Page<T> defaultPage(T t, Page<T> page) {
//        return service.pageByExample(t, page);
        return null;
    }

    protected T defaultGet(Serializable id) {
        return service.getById(id);
    }

    protected Boolean defaultAdd(T t) {
        return service.save(t);
    }

    protected Boolean defaultUpdate(T t) {
        return service.modifyById(t);
    }

    protected Boolean defaultDelete(List<Serializable> ids) {
        return service.removeByIds(ids);
    }

}
