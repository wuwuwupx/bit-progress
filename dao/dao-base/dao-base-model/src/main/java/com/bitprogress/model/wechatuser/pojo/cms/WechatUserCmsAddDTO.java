package com.bitprogress.model.wechatuser.pojo.cms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 * created on 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WechatUserCmsAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信用户unionId")
    @NotBlank(message = "微信用户unionId不能为空")
    private String unionId;

    @ApiModelProperty(value = "微信用户昵称")
    @NotBlank(message = "微信用户昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "微信用户头像")
    @NotBlank(message = "微信用户头像不能为空")
    private String avatar;

    @ApiModelProperty(value = "用户性别")
    @NotNull(message = "用户性别不能为空")
    private Integer gender;

}
