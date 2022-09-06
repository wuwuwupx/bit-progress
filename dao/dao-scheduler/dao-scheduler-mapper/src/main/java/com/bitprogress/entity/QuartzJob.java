package com.bitprogress.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import com.bitprogress.model.quartzjob.pojo.envm.TriggerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wuwuwupx
 * @since 2021-11-19
 */
@ApiModel(value = "定时任务")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sdr_quartz_job")
public class QuartzJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "定时任务ID")
    @TableId(value = "quartz_job_id", type = IdType.AUTO)
    private Long quartzJobId;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务触发类型，SIMPLE：倒计时出发，CRON：表达式触发")
    private TriggerType triggerType;

    @ApiModelProperty(value = "重试次数，出发类型为倒计时出发时才生效")
    private Integer retry;

    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    @ApiModelProperty(value = "倒计时触发时长")
    private Long duration;

    @ApiModelProperty(value = "触发的bean名称")
    private String beanName;

    @ApiModelProperty(value = "触发的方法名称")
    private String methodName;

    @ApiModelProperty(value = "触发参数")
    private String args;

    @ApiModelProperty(value = "触发的参数类型")
    private String argsClass;

    @ApiModelProperty(value = "分组ID")
    private Long groupId;

    @ApiModelProperty(value = "定时任务服务名称")
    private String applicationName;

    @ApiModelProperty(value = "定时任务触达地址")
    private String serverUrl;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除，0：false，未删除；1：true，已删除")
    @TableLogic
    private Boolean deleted;


}
