package com.bitprogress.model.captcha;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author wuwuwupx
 */
public class PhoneCaptchaDTO {

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @NotNull(message = "手机号码不能为空")
    @Size(max = 11, min = 11, message = "手机号码长度只能为11")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PhoneCaptchaDTO{" +
                "phone='" + phone + '\'' +
                '}';
    }
}
