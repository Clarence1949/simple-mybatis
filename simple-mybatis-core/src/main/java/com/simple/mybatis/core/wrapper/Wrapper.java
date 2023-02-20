package com.simple.mybatis.core.wrapper;

import java.util.List;

/**
 * @author FanXing
 * @date 2023年02月20日 9:51
 */
public interface Wrapper<T> {
    /**
     * 获取查询SQL片段集合
     *
     * @return
     */
    List<String> getQuerySqlSegments();
}
