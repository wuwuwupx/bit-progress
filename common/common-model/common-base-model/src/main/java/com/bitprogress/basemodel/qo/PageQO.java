package com.bitprogress.basemodel.qo;

import com.bitprogress.basemodel.QO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQO extends QO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * current page index
     */
    private int pageIndex = 1;

    /**
     * a page data size
     * default 10
     */
    private int pageSize = 10;

    /**
     * order column list
     */
    private List<OrderColumnItem> orders = new ArrayList<>();

}
