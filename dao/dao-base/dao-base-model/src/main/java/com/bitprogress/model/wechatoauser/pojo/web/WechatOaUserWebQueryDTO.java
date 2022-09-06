package com.bitprogress.model.wechatoauser.pojo.web;

import java.time.LocalDateTime;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuwuwupx
 * create on 2021-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WechatOaUserWebQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信公众号用户ID")
    private Long wechatOaUserId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "微信用户ID")
    private Long wechatUserId;

    @ApiModelProperty(value = "应用ID")
    private Long appId;

    @ApiModelProperty(value = "所属公众号标识")
    private String appSign;

    @ApiModelProperty(value = "公众号用户唯一标识")
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

    @ApiModelProperty(value = "创建时间", dataType = "java.lang.Long")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", dataType = "java.lang.Long")
    private LocalDateTime updateTime;


}
