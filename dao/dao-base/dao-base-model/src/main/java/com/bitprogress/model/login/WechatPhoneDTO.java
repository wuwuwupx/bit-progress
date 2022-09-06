package com.bitprogress.model.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wuwuwupx
 */
@Data
public class WechatPhoneDTO {

    @ApiModelProperty(value = "加密数据")
    @NotNull(message = "加密数据不能为空")
    @NotBlank(message = "加密数据不能为空")
    private String encryptedData;

    @ApiModelProperty(value = "加密算法的初始向量")
    @NotNull(message = "加密算法的初始向量不能为空")
    @NotBlank(message = "加密算法的初始向量不能为空")
    private String iv;

}
