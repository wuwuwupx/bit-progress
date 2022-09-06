package com.bitprogress.model.wechatapp.pojo.cms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 * create on 2021-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WechatAppCmsAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "应用ID")
    @NotNull(message = "应用ID不能为空")
    private Long appId;

    @ApiModelProperty(value = "微信应用类型，0：小程序，1：公众号")
    @NotNull(message = "微信应用类型，0：小程序，1：公众号不能为空")
    private Integer wechatAppType;

    @ApiModelProperty(value = "应用标识")
    @NotBlank(message = "应用标识不能为空")
    private String appSign;

    @ApiModelProperty(value = "应用名称")
    @NotBlank(message = "应用名称不能为空")
    private String appName;

    @ApiModelProperty(value = "微信appid")
    @NotBlank(message = "微信appid不能为空")
    private String wxAppId;

    @ApiModelProperty(value = "微信app_secret")
    @NotBlank(message = "微信app_secret不能为空")
    private String appSecret;

}
