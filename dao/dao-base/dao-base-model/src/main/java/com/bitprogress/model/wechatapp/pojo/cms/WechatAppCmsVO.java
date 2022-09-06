package com.bitprogress.model.wechatapp.pojo.cms;

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
public class WechatAppCmsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信应用ID")
    private Long wechatAppId;

    @ApiModelProperty(value = "应用ID")
    private Long appId;

    @ApiModelProperty(value = "微信应用类型，0：小程序，1：公众号")
    private Integer wechatAppType;

    @ApiModelProperty(value = "应用标识")
    private String appSign;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "微信appid")
    private String wxAppId;

    @ApiModelProperty(value = "微信app_secret")
    private String appSecret;

}
