package com.bitprogress.excel.data;

import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据表数据信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WriteSheetData {

    /**
     * 数据表信息
     */
    private WriteSheet writeSheet;

    /**
     * 数据
     */
    private List<?> data;

}
