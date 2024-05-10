package com.bitprogress.vo;

import com.bitprogress.VO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * option view object
 * 业务服务数据字典一般以 id - name 形式存在
 */
@ApiModel("基础业务字典选项")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionVO extends VO {

    /**
     * id -- key
     */
    @ApiModelProperty(value = "id，即key")
    private Long id;

    /**
     * name -- value
     */
    @ApiModelProperty(value = "name，即value")
    private String name;

}
