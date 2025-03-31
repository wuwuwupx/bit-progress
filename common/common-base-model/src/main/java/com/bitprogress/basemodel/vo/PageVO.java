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
    private long total;

    /**
     * page size
     */
    private long size = 10;

    /**
     * current page
     */
    private long current = 1;

    /**
     * create pageVO
     */
    public static <T> PageVO<T> of(List<T> list, Long total, Long size, Long current) {
        return new PageVO<>(list, total, size, current);
    }

    /**
     * create pageVO
     */
    public static <T> PageVO<T> of(List<T> records, Long total) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(records);
        pageVO.setTotal(total.intValue());
        return pageVO;
    }

    /**
     * create pageVO
     */
    public static <T> PageVO<T> of(List<T> records) {
        int size = Objects.isNull(records) ? 0 : records.size();
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(records);
        pageVO.setTotal(size);
        return pageVO;
    }

    /**
     * create empty pageVO
     */
    public static <T> PageVO<T> empty() {
        return new PageVO<>(new ArrayList<>(), 0, 0, 0);
    }

    /**
     * create empty pageVO
     */
    public static <T> PageVO<T> empty(Long total, Long size, Long current) {
        return new PageVO<>(new ArrayList<>(), total, size, current);
    }

}
