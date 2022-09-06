package com.bitprogress.model.manager.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wpx
 */
@Data
public class ManagerLoginDTO {

    @ApiModelProperty("账户")
    @NotNull(message = "账户不能为空")
    @NotBlank(message = "账户不能为空")
    private String account;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("验证码")
    @NotNull(message = "验证码不能为空")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @ApiModelProperty("uuid")
    @NotNull(message = "uuid不能为空")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

}
