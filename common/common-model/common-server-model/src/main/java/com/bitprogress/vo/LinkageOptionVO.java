package com.bitprogress.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Linkage option
 * 附带子选项列表
 */
@ApiModel("联动业务字典选项")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkageOptionVO extends OptionVO {

    /**
     * children option lit
     */
    @ApiModelProperty(value = "子选项列表")
    private List<OptionVO> childOptions;

}
