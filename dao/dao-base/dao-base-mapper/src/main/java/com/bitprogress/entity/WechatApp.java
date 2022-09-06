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
 * create on 2021-08-28
 */
@ApiModel(value = "微信应用信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_wechat_app")
public class WechatApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信应用ID")
    @TableId(value = "wechat_app_id", type = IdType.AUTO)
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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除，0：false，未删除；1：true，已删除")
    @TableLogic
    private Boolean deleted;

}
