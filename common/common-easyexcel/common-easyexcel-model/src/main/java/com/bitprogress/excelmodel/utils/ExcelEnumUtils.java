package com.bitprogress.excelmodel.utils;

import com.bitprogress.excelmodel.ExcelEnum;

import java.util.Objects;

public class ExcelEnumUtils {

    public static <T extends ExcelEnum> T getByMessage(Class<T> tClass, String message) {
        T[] enumConstants = tClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (Objects.equals(enumConstant.getMessage(), message)) {
                return enumConstant;
            }
        }
        return null;
    }

}
