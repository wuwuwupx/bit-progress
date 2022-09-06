package com.bitprogress.model.manager.pojo.cms;

import com.bitprogress.model.manager.envm.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wuwuwupx
 * created on 2021-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManagerCmsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员ID")
    private Long managerId;

    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "角色。ROOT：超级管理员，ADMIN：普通管理员")
    private RoleEnum role;

    @ApiModelProperty(value = "是否已被禁用。false：未禁用(默认)，true：已禁用")
    private Boolean disabled;

}
