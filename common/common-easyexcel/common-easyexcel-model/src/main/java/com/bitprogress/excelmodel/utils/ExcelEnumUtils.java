package com.bitprogress.excelmodel.utils;

import com.bitprogress.excelmodel.ExcelEnum;

import java.io.Serializable;
import java.util.Objects;

public class ExcelEnumUtils {

    public static <T extends ExcelEnum<R>, R extends Serializable> T getByMessage(Class<T> tClass, R message) {
        T[] enumConstants = tClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (Objects.equals(enumConstant.getMessage(), message)) {
                return enumConstant;
            }
        }
        return null;
    }

}
