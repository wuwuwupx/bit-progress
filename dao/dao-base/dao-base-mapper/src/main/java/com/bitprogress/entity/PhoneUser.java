package com.bitprogress.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 * @since 2021-08-31
 */
@ApiModel(value = "手机用户信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_phone_user")
public class PhoneUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机用户ID")
    @TableId(value = "phone_user_id", type = IdType.AUTO)
    private Long phoneUserId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "姓")
    private String surname;

    @ApiModelProperty(value = "名")
    private String name;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "登录密码")
    private String password;

}
