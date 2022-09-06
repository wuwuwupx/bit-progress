package com.bitprogress.model.quartzjob.pojo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 * @since 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuartzJobAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    @ApiModelProperty(value = "任务触发类型，SIMPLE：倒计时出发，CRON：表达式触发")
    @NotNull(message = "任务触发类型，SIMPLE：倒计时出发，CRON：表达式触发不能为空")
    private Integer triggerType;

    @ApiModelProperty(value = "重试次数，出发类型为倒计时出发时才生效")
    @NotNull(message = "重试次数，出发类型为倒计时出发时才生效不能为空")
    private Integer retry;

    @ApiModelProperty(value = "cron表达式")
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    @ApiModelProperty(value = "倒计时触发时长")
    @NotNull(message = "倒计时触发时长不能为空")
    private Long duration;

    @ApiModelProperty(value = "触发的bean名称")
    @NotBlank(message = "触发的bean名称不能为空")
    private String beanName;

    @ApiModelProperty(value = "触发的方法名称")
    @NotBlank(message = "触发的方法名称不能为空")
    private String methodName;

    @ApiModelProperty(value = "触发参数")
    @NotBlank(message = "触发参数不能为空")
    private String args;

    @ApiModelProperty(value = "触发的参数类型")
    @NotBlank(message = "触发的参数类型不能为空")
    private String argsClass;

    @ApiModelProperty(value = "分组ID")
    @NotNull(message = "分组ID不能为空")
    private Long groupId;

    @ApiModelProperty(value = "定时任务服务名称")
    private String applicationName;

    @ApiModelProperty(value = "定时任务触达地址")
    private String serverUrl;

}
