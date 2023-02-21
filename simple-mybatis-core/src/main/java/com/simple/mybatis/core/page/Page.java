package com.simple.mybatis.core.page;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author FanXing
 * @date 2023年02月16日 22:12
 */
@Data
public class Page<T> implements Serializable {
    private List<T> records = Collections.emptyList();
    private int pageNum = 1;
    private int pageSize = 10;
    private int total = 0;

    public Page() {
    }

    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
