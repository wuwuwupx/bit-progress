package com.bitprogress.model.quartzjob.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wuwuwupx
 */
@Data
public class QuartzJobStateDTO {

    @ApiModelProperty(value = "定时任务ID")
    @NotNull(message = "定时任务ID不能为空")
    private Long quartzJobId;

}
