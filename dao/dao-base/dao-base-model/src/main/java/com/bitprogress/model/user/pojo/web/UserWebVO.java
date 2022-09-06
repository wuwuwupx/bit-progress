package com.bitprogress.model.user.pojo.web;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 * created on 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserWebVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户所属应用ID")
    private Long appId;

    @ApiModelProperty(value = "用户所属应用类型")
    private Integer appType;

    @ApiModelProperty(value = "用户所属应用标识")
    private String appSign;

    @ApiModelProperty(value = "是否禁用，0：false，不禁言；1：true，禁用")
    private Boolean flag;

}
