package com.bitprogress.qo;

import com.bitprogress.QO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ApiModel("日期查询")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateQO extends QO {

    @ApiModelProperty(value = "日期")
    private LocalDate date;

}
