package com.bitprogress.excel.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 冻结行列样式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreezePaneData {

    /**
     * 冻结的首列
     */
    private Integer colSplit;

    /**
     * 冻结的首行
     */
    private Integer rowSplit;

    /**
     * 冻结后第一列的列号
     */
    private Integer leftmostColumn;

    /**
     * 冻结后第一行的行号
     */
    private Integer topRow;

}
