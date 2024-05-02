package com.bitprogress.qo;

import com.bitprogress.QO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQO extends QO {

    /**
     * current page index
     */
    private int pageIndex = 1;

    /**
     * a page data size
     * default 10
     */
    private int pageSize = 10;

}
