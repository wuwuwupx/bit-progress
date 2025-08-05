package com.bitprogress.service;

import com.bitprogress.basemodel.filler.IdByNameIFiller;
import com.bitprogress.basemodel.filler.NameByIdIFiller;

import java.util.List;

public interface FillService {

    /**
     * 填充id
     *
     * @param filler 需要填充的实体
     */
    <T extends IdByNameIFiller> void fillId(T filler);

    /**
     * 填充id
     *
     * @param fillers 需要填充的实体列表
     */
    <T extends IdByNameIFiller> void fillId(List<T> fillers);

    /**
     * 填充name
     *
     * @param filler 需要填充的实体
     */
    <T extends NameByIdIFiller> void fillName(T filler);

    /**
     * 填充name
     *
     * @param fillers 需要填充的实体列表
     */
    <T extends NameByIdIFiller> void fillName(List<T> fillers);

}
