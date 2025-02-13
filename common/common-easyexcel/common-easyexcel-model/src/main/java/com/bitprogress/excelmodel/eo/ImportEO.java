package com.bitprogress.excelmodel.eo;

import com.bitprogress.basemodel.EO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * excel import read object
 * 用于读取导入数据
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ImportEO extends EO {

    /**
     * excel rowIndex
     */
    private Integer rowIndex;

}
