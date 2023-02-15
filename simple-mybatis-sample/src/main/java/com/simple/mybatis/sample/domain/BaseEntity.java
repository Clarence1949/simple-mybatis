package com.simple.mybatis.sample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simple.mybatis.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体抽象类，实现通用字段维护
 *
 * @author FanXing
 * @date 2023年01月17日 10:58
 */
@Data
public abstract class BaseEntity implements Serializable {

    @ApiModelProperty("创建人")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @ApiModelProperty("创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdTime;

    @ApiModelProperty("更新人")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastUpdatedBy;

    @ApiModelProperty("更新时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdatedTime;

    @ApiModelProperty("是否删除")
    @JsonIgnore
    @TableLogic
    private Boolean deleted;

}
