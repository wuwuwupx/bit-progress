package com.bitprogress.model.manager.pojo.cms;

import com.bitprogress.model.manager.envm.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManagerRoleDTO {

    @ApiModelProperty("主键id")
    @NotNull(message = "id不能为空")
    private Long managerId;

    @ApiModelProperty("角色")
    @NotNull(message = "角色不能为空")
    private RoleEnum role;

}
