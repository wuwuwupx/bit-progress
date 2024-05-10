package com.bitprogress.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * additional option view object
 */
@ApiModel("附加选项业务字典选项")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachedOptionVO extends OptionVO {

    /**
     * additional option
     */
    @ApiModelProperty(value = "附加选项")
    private OptionVO attachedOption;

}
