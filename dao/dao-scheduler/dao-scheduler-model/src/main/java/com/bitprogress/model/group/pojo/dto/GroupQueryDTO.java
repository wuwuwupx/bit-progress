package com.bitprogress.model.group.pojo.dto;

import java.time.LocalDateTime;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuwuwupx
 * @since 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "定时任务分组ID")
    private Long groupId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "创建时间", dataType = "java.lang.Long")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", dataType = "java.lang.Long")
    private LocalDateTime updateTime;


}
