package com.bitprogress.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @author wuwuwupx
 *  该类继承了 Spring BeanUtils, 对部分方法进行了增强与补充.
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 复制字段属性(包括{@code null}) (支持链式编程)
     *
     * @param source      源对象
     * @param targetClazz 目标对象类型class对象
     * @param <S>         源对象类型
     * @param <T>         目标对象类型
     * @return 目标对象
     */
    public static <S, T> T copyProperties(S source, Class<T> targetClazz) {
        T to;
        try {
            to = targetClazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        copyProperties(source, to);
        return to;
    }


    /**
     * 复制非{@code null}的字段属性
     *
     * @param src    源对象
     * @param target 目标对象
     */
    public static <T> T copyNonNullProperties(Object src, T target) {
        copyProperties(src, target, getNullPropertyNames(src));
        return target;
    }

    /**
     * 复制非{@code null}的字段属性 (支持链式编程)
     *
     * @param src    源对象
     * @param target 目标对象 class对象
     * @param <T>    目标对象的类型
     * @return 目标对象
     */
    public static <T> T copyNonNullProperties(Object src, Class<T> target) {
        T t;
        try {
            t = target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        copyProperties(src, t, getNullPropertyNames(src));
        return t;
    }

    /**
     * 获取对象字段为空{@code null} 的字段名
     *
     * @param obj 对象
     * @return 为空的字段名数组
     */
    private static String[] getNullPropertyNames(Object obj) {
        final BeanWrapper src = new BeanWrapperImpl(obj);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 判断对象是否存在属性为空(这里的空包括 NotBlank NotEmpty NotNull 的判断)
     *
     * @param obj          需要进行校验的obj对象
     * @param ignoreFields 跳过检查的字段名列表
     * @return true:存在属性为空 or false:所有属性均不为空
     */
    public static boolean anyNullProperties(Object obj, String... ignoreFields) {
        ArrayList<String> ignoreFieldList = new ArrayList<>(Arrays.asList(ignoreFields));
        if (!ObjectUtils.isEmpty(obj)) {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (ignoreFieldList.contains(field.getName())) {
                    continue;
                }
                Object val;
                try {
                    val = field.get(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (ObjectUtils.isEmpty(val)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断对象所有属性均为空(这里的空包括 NotBlank NotEmpty NotNull 的判断)
     *
     * @param obj          需要进行校验的obj对象
     * @param ignoreFields 跳过检查的字段名列表
     * @return true:所有属性均为空 or false:存在不为空的属性
     */
    public static boolean allNullProperties(Object obj, String... ignoreFields) {

        ArrayList<String> ignoreFieldList = new ArrayList<>(Arrays.asList(ignoreFields));

        if (!ObjectUtils.isEmpty(obj)) {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (ignoreFieldList.contains(field.getName())) {
                    continue;
                }
                Object val;
                try {
                    val = field.get(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (!ObjectUtils.isEmpty(val)) {
                    return false;
                }
            }
        }

        return true;
    }

}
