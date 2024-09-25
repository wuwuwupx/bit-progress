package com.bitprogress.util;

import com.bitprogress.basemodel.dto.IdNameDTO;
import com.bitprogress.basemodel.filler.IdByNameFiller;
import com.bitprogress.basemodel.filler.NameByIdFiller;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * 数据填充工具类
 */
public class FillUtils {

    /**
     * 根据名称填充ID
     *
     * @param filler   需要填充ID的对象
     * @param function 获取ID的方法
     */
    public static <T extends IdByNameFiller> void fillId(T filler, Function<String, Long> function) {
        if (Objects.isNull(filler)) {
            return;
        }
        filler.setId(function.apply(filler.getName()));
    }

    /**
     * 根据名称填充ID
     *
     * @param fillers  需要填充ID的对象集合
     * @param function 获取ID的方法
     */
    public static <T extends IdByNameFiller> void fillId(List<T> fillers,
                                                         Function<Set<String>, List<IdNameDTO>> function) {
        if (CollectionUtils.isEmpty(fillers)) {
            return;
        }
        Set<String> names = CollectionUtils.toSet(fillers, IdByNameFiller::getName);
        List<IdNameDTO> list = function.apply(names);
        Map<String, Long> map = CollectionUtils.toMap(list, IdNameDTO::getName, IdNameDTO::getId);
        fillers.forEach(filler -> filler.setId(CollectionUtils.getForMap(map, filler.getName())));
    }

    /**
     * 根据ID填充名称
     *
     * @param filler   需要填充名称的对象
     * @param function 获取名称的方法
     */
    public static <T extends NameByIdFiller> void fillName(T filler, Function<Long, String> function) {
        if (Objects.isNull(filler)) {
            return;
        }
        filler.setName(function.apply(filler.getId()));
    }

    /**
     * 根据ID填充名称
     *
     * @param fillers  需要填充名称的对象集合
     * @param function 获取名称的方法
     */
    public static <T extends NameByIdFiller> void fillName(List<T> fillers,
                                                           Function<Set<Long>, List<IdNameDTO>> function) {
        if (CollectionUtils.isEmpty(fillers)) {
            return;
        }
        Set<Long> ids = CollectionUtils.toSet(fillers, NameByIdFiller::getId);
        List<IdNameDTO> list = function.apply(ids);
        Map<Long, String> map = CollectionUtils.toMap(list, IdNameDTO::getId, IdNameDTO::getName);
        fillers.forEach(filler -> filler.setName(CollectionUtils.getForMap(map, filler.getId())));
    }

}
