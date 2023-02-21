package com.simple.mybatis.sample.domain;

import com.simple.mybatis.annotation.Simpler;
import com.simple.mybatis.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
@Simpler
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String name;

    private String state;

    private String country;

}
