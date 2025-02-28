package com.bitprogress.basemodel.util;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;

import java.util.Objects;
import java.util.function.Predicate;

public class EnumUtils {

    /**
     * 根据枚举值获取枚举
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <T>       枚举类型
     * @return 枚举
     */
    public static <T extends ValueEnum> T getByValue(Class<T> enumClass, Integer value) {
        checkEnum(enumClass);
        T[] enumConstants = enumClass.getEnumConstants();
        return getEnum(enumConstants, enumConstant -> enumConstant.getValue().equals(value));
    }

    /**
     * 根据枚举值获取枚举
     *
     * @param enumClass 枚举类
     * @param message   枚举值
     * @param <T>       枚举类型
     * @return 枚举
     */
    public static <T extends MessageEnum> T getByMessage(Class<T> enumClass, String message) {
        checkEnum(enumClass);
        T[] enumConstants = enumClass.getEnumConstants();
        return getEnum(enumConstants, enumConstant -> enumConstant.getMessage().equals(message));
    }

    /**
     * 根据枚举值获取枚举
     *
     * @param enumConstants 枚举类
     * @param predicate     枚举值
     * @param <T>           枚举类型
     * @return 枚举
     */
    private static <T> T getEnum(T[] enumConstants, Predicate<T> predicate) {
        if (Objects.isNull(enumConstants)) {
            return null;
        }
        for (T enumConstant : enumConstants) {
            if (predicate.test(enumConstant)) {
                return enumConstant;
            }
        }
        return null;
    }

    /**
     * 校验枚举值是否存在
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <T>       枚举类型
     * @return 枚举
     */
    public static <T extends ValueEnum> boolean checkExistByValue(Class<T> enumClass, Integer value) {
        checkEnum(enumClass);
        T[] enumConstants = enumClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (enumConstant.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验枚举值是否存在
     *
     * @param enumClass 枚举类
     * @param message   枚举值
     * @param <T>       枚举类型
     * @return 枚举
     */
    public static <T extends MessageEnum> boolean checkExistByMessage(Class<T> enumClass, String message) {
        checkEnum(enumClass);
        T[] enumConstants = enumClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (enumConstant.getMessage().equals(message)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验枚举类
     *
     * @param enumClass 枚举类
     */
    private static void checkEnum(Class<?> enumClass) {
        if (enumClass == null) {
            throw new IllegalArgumentException("enumClass cannot be null");
        }
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException(enumClass.getSimpleName() + " is not an enum class.");
        }
    }

}
