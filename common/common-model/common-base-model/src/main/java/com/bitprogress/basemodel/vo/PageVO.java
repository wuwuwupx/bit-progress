package com.bitprogress.basemodel.vo;

import com.bitprogress.basemodel.VO;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> extends VO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * data list
     */
    private List<T> records;

    /**
     * data num
     */
    private int total;

    /**
     * create pageVO
     */
    public static <T> PageVO<T> of(List<T> list, Long total) {
        return new PageVO<>(list, total.intValue());
    }

    /**
     * create pageVO
     */
    public static <T> PageVO<T> of(List<T> list, Integer total) {
        return new PageVO<>(list, total);
    }

    public static <T> PageVO<T> of(List<T> list) {
        return new PageVO<>(list, CollectionUtils.size(list));
    }

    public static <T> PageVO<T> empty() {
        return new PageVO<>(CollectionUtils.emptyList(), 0);
    }

}
