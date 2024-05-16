package com.bitprogress.servermodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 多值选项
 */
@Schema(description = "多值选项")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultivaluedOptionVO extends OptionVO {

    @Schema(description = "选项的值")
    private String value;

}
