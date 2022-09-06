package com.bitprogress.model.wechatappletuser.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WechatAppletUserWebVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信小程序用户ID")
    private Long wechatAppletUserId;

    @ApiModelProperty(value = "微信用户ID")
    private Long userId;

    @ApiModelProperty(value = "微信用户ID")
    private Long wechatUserId;

    @ApiModelProperty(value = "应用ID")
    private Long appId;

    @ApiModelProperty(value = "所属小程序标识")
    private String appSign;

    @ApiModelProperty(value = "小程序用户唯一标识")
    private String openId;

    @ApiModelProperty(value = "微信用户唯一标识")
    private String unionId;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户性别")
    private Integer gender;

    @ApiModelProperty(value = "所在地")
    private String location;

    @ApiModelProperty(value = "微信用户登录获取的会话密钥，每次登录会刷新")
    private String sessionKey;

}
