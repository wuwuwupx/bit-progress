package com.bitprogress.basemodel.util;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;

public class EnumUtils {

    public static <T extends ValueEnum> T getByValue(Class<T> enumClass, Integer value) {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (enumConstant.getValue().equals(value)) {
                return enumConstant;
            }
        }
        return null;
    }

    public static <T extends MessageEnum> T getByMessage(Class<T> enumClass, String message) {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (enumConstant.getMessage().equals(message)) {
                return enumConstant;
            }
        }
        return null;
    }

}
