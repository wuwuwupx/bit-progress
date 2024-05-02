package com.bitprogress.eo;

import com.bitprogress.EO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * excel import read object
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
