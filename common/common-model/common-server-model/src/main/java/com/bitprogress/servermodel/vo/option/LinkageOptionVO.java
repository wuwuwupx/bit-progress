package com.bitprogress.servermodel.vo.option;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Linkage option
 * 附带子选项列表
 */
@Schema(description = "联动选项展示信息")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkageOptionVO extends OptionVO {

    /**
     * children option lit
     */
    @Schema(description = "子选项列表")
    private List<OptionVO> childOptions;

}
