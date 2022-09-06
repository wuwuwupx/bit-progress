package com.bitprogress.model.manager.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description
 * create by LW-mochengdong
 * 2018/7/24
 */
@Data
public class ManagerStateDTO {

    @ApiModelProperty("管理员ID")
    @NotNull(message = "管理员ID不能为空")
    private Long managerId;

}
