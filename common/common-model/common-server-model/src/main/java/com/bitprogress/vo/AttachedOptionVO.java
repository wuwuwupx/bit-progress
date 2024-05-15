package com.bitprogress.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * additional option view object
 */
@Schema(description = "附加选项业务字典选项")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachedOptionVO extends OptionVO {

    /**
     * additional option
     */
    @Schema(description = "附加选项")
    private OptionVO attachedOption;

}
