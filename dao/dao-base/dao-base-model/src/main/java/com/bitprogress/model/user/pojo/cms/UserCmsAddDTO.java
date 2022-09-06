package com.bitprogress.model.user.pojo.cms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class UserCmsAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户所属应用类型")
    @NotNull(message = "用户所属应用类型不能为空")
    private Integer appType;

    @ApiModelProperty(value = "用户所属应用标识")
    @NotBlank(message = "用户所属应用标识不能为空")
    private String appSign;

}
