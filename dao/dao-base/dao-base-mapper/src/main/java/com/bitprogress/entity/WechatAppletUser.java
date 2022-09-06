package com.bitprogress.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 */
@ApiModel(value = "微信小程序用户信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_wechat_applet_user")
public class WechatAppletUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信小程序用户ID")
    @TableId(value = "wechat_applet_user_id", type = IdType.AUTO)
    private Long wechatAppletUserId;

    @ApiModelProperty(value = "用户ID")
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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除，0：false，未删除；1：true，已删除")
    @TableLogic
    private Boolean deleted;

}
