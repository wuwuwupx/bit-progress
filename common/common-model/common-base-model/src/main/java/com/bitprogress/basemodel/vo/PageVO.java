package com.bitprogress.basemodel.vo;

import com.bitprogress.basemodel.VO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        int size = Objects.isNull(list) ? 0 : list.size();
        return new PageVO<>(list, size);
    }

    public static <T> PageVO<T> empty() {
        return new PageVO<>(new ArrayList<>(), 0);
    }

}
