package com.bitprogress.model.login;

import com.bitprogress.model.wechatapp.envm.WechatAppTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wuwuwupx
 */
@ApiModel(value = "兼容新的获取用户信息方式，直接从前端获取用户信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WechatLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "js_code")
    @NotNull(message = "js_code不能为空")
    @NotBlank(message = "js_code不能为空")
    private String jsCode;

    @ApiModelProperty(value = "应用标识标识")
    @NotNull(message = "appSign 不能为空")
    @NotBlank(message = "appSign 不能为空")
    private String appSign;

    @ApiModelProperty(value = "微信应用类型")
    @NotNull(message = "微信应用类型不能为空")
    private WechatAppTypeEnum wechatAppType;

    @ApiModelProperty(value = "是否带有用户信息")
    @NotNull(message = "是否带有用户信息不能为空")
    private Boolean authorized;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户性别 0:未知 1:男 2:女")
    private Integer gender;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省份昵称")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

}
