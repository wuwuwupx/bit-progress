package com.bitprogress.model.quartzjob.pojo.dto;

import java.io.Serializable;

import com.bitprogress.model.quartzjob.pojo.envm.TriggerType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuwuwupx
 * @since 2021-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuartzJobQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务触发类型，SIMPLE：倒计时出发，CRON：表达式触发")
    private TriggerType triggerType;

    @ApiModelProperty(value = "分组ID")
    private Long groupId;

}
