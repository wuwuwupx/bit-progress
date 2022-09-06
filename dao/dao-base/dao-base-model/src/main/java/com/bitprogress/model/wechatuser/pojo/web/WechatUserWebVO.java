package com.bitprogress.model.wechatuser.pojo.web;

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
public class WechatUserWebVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信生态用户ID")
    private Long wechatUserId;

    @ApiModelProperty(value = "微信用户unionId")
    private String unionId;

    @ApiModelProperty(value = "微信用户昵称")
    private String nickname;

    @ApiModelProperty(value = "微信用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户性别")
    private Integer gender;

}
