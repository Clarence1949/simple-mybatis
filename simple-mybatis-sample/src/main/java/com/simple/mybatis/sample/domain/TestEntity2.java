package com.simple.mybatis.sample.domain;

import com.simple.mybatis.annotation.Simpler;
import com.simple.mybatis.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 测试用例;
 *
 * @author : Clarence
 * @date : 2023-1-15
 */
@ApiModel(description = "测试用例")
@Data
@Simpler
public class TestEntity2 implements Serializable {

    @ApiModelProperty("")
    @TableId
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("关联主表ID")
    private Long mainId;

}