package com.bitprogress.servermodel.vo;

import com.bitprogress.basemodel.VO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * option view object
 * 业务服务数据字典一般以 id - name 形式存在
 */
@Schema(description = "基础业务字典选项")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionVO extends VO {

    /**
     * id -- key
     */
    @Schema(description = "id，即key")
    private Long id;

    /**
     * name -- value
     */
    @Schema(description = "name，即value")
    private String name;

}
