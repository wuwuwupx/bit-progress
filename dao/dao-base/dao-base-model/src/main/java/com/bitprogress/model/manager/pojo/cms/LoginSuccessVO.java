package com.bitprogress.model.manager.pojo.cms;

import com.bitprogress.model.manager.envm.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jdq
 * @date 2017/12/9 17:30
 */
@Data
public class LoginSuccessVO {

    @ApiModelProperty("role。admin：普通管理员，root：超级管理员")
    private RoleEnum role;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("名称")
    private String name;
}
