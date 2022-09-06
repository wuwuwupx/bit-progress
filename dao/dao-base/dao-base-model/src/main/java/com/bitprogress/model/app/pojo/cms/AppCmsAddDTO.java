package com.bitprogress.model.app.pojo.cms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import com.bitprogress.model.app.envm.AppTypeEnum;
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
public class AppCmsAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用类型")
    @NotNull(message = "应用类型不能为空")
    private AppTypeEnum appType;

    @ApiModelProperty(value = "应用标识")
    @NotBlank(message = "应用标识不能为空")
    private String appSign;

    @ApiModelProperty(value = "应用名称")
    @NotBlank(message = "应用名称不能为空")
    private String appName;

}
