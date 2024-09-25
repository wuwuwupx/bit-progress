package com.bitprogress.basemodel.util;

import com.bitprogress.basemodel.filler.IdByNameFiller;
import com.bitprogress.basemodel.filler.NameByIdFiller;

import java.util.Objects;
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

}
