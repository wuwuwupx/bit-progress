package com.bitprogress.model.group.pojo.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 * @since 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GroupVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "定时任务分组ID")
    private Long groupId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

}
