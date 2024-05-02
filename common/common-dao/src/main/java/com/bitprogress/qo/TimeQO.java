package com.bitprogress.qo;

import com.bitprogress.QO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 时间查询
 */
@ApiModel("时间点查询")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeQO extends QO {

    @ApiModelProperty(value = "查询时间")
    private LocalDateTime time;

}
