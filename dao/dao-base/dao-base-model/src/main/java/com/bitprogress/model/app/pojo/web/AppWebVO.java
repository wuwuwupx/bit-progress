package com.bitprogress.model.app.pojo.web;

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
public class AppWebVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用ID")
    private Long appId;

    @ApiModelProperty(value = "应用类型")
    private AppTypeEnum appType;

    @ApiModelProperty(value = "应用标识")
    private String appSign;

    @ApiModelProperty(value = "应用名称")
    private String appName;

}
