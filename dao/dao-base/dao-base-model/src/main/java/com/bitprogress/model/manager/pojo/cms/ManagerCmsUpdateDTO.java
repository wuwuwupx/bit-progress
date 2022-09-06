package com.bitprogress.model.manager.pojo.cms;

import com.bitprogress.model.manager.envm.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wuwuwupx
 * created on 2021-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManagerCmsUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员ID")
    @NotNull(message = "管理员ID不能为空")
    private Long managerId;

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "角色。ROOT：超级管理员，ADMIN：普通管理员")
    @NotNull(message = "角色。ROOT：超级管理员，ADMIN：普通管理员不能为空")
    private RoleEnum role;

}
