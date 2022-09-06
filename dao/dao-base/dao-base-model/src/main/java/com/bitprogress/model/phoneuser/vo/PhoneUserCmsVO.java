package com.bitprogress.model.phoneuser.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 * @since 2021-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PhoneUserCmsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机用户ID")
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
