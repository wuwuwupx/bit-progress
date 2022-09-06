package com.bitprogress.model.manager.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ManagerEditPasswordDTO {

    @ApiModelProperty("主键id")
    @NotNull(message = "id不能为空")
    private Long managerId;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
