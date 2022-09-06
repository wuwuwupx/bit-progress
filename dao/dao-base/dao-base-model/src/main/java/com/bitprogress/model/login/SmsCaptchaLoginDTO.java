package com.bitprogress.model.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author wuwuwupx
 */
@Data
public class SmsCaptchaLoginDTO {

    /**
     * 应用标识
     */
    @ApiModelProperty(value = "应用标识")
    @NotNull(message = "应用标识不能为空")
    @NotBlank(message = "应用标识不能为空")
    private String appSign;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @Size(max = 11, min = 11, message = "手机号码只能11位数")
    private String phone;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "短信验证码")
    @NotNull(message = "短信验证码不能为空")
    @NotBlank(message = "短信验证码不能为空")
    private String smsCaptcha;

}
