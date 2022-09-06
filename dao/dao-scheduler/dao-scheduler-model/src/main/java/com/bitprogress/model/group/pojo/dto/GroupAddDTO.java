package com.bitprogress.model.group.pojo.dto;

import javax.validation.constraints.NotBlank;

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
public class GroupAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "分组名称")
    @NotBlank(message = "分组名称不能为空")
    private String groupName;


}
