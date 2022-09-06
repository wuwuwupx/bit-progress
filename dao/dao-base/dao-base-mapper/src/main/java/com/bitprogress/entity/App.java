package com.bitprogress.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import com.bitprogress.model.app.envm.AppTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 */
@ApiModel(value = "应用信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_app")
public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用ID")
    @TableId(value = "app_id", type = IdType.AUTO)
    private Long appId;

    @ApiModelProperty(value = "应用类型")
    private AppTypeEnum appType;

    @ApiModelProperty(value = "应用标识")
    private String appSign;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除，0：false，未删除；1：true，已删除")
    @TableLogic
    private Boolean deleted;

}
