package com.bitprogress.eo;

import com.bitprogress.EO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * excel export write object
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ExportEO extends EO {

    /**
     * excel rowIndex
     */
    private Integer rowIndex;

}
