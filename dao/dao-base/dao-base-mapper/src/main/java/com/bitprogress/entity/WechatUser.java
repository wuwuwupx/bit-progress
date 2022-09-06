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
 * created on 2021-08-13
 */
@ApiModel(value = "用户信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_wechat_user")
public class WechatUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信生态用户ID")
    @TableId(value = "wechat_user_id", type = IdType.AUTO)
    private Long wechatUserId;

    @ApiModelProperty(value = "微信用户unionId")
    private String unionId;

    @ApiModelProperty(value = "微信用户昵称")
    private String nickname;

    @ApiModelProperty(value = "微信用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户性别")
    private Integer gender;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除，0：false，未删除；1：true，已删除")
    @TableLogic
    private Boolean deleted;


}
