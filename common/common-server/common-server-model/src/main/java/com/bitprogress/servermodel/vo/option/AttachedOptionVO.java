package com.bitprogress.servermodel.vo.option;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * additional option view object
 */
@Schema(description = "附加选项展示信息")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachedOptionVO extends OptionVO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * additional option
     */
    @Schema(description = "附加选项")
    private OptionVO attachedOption;

}
