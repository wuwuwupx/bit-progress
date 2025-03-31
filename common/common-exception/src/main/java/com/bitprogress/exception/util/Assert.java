package com.bitprogress.exception.util;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionMessage;

import java.util.Collection;

/**
 * 断言类
 */
public class Assert {

    /**
     * 判断是否为真，不为真则直接抛出异常
     *
     * @param expression 表达式结果
     * @param exception  异常信息
     */
    public static void isTrue(boolean expression, String exception) {
        isTrue(expression, CommonException.error(exception));
    }

    /**
     * 判断是否为真，不为真则直接抛出异常
     *
     * @param expression 表达式结果
     * @param exception  异常信息
     */
    public static void isTrue(boolean expression, ExceptionMessage exception) {
        isTrue(expression, CommonException.error(exception));
    }

    /**
     * 判断是否为真，不为真则直接抛出异常
     *
     * @param expression 表达式结果
     * @param exception  异常信息
     */
    public static void isTrue(boolean expression, CommonException exception) {
        if (!expression) {
            throw exception;
        }
    }

    /**
     * 判断是否不为真，为真则直接抛出异常
     *
     * @param expression 表达式结果
     * @param exception  异常信息
     */
    public static void isFalse(boolean expression, String exception) {
        isFalse(expression, CommonException.error(exception));
    }

    /**
     * 判断是否不为真，为真则直接抛出异常
     *
     * @param expression 表达式结果
     * @param exception  异常信息
     */
    public static void isFalse(boolean expression, ExceptionMessage exception) {
        isFalse(expression, CommonException.error(exception));
    }

    /**
     * 判断是否不为真，为真则直接抛出异常
     *
     * @param expression 表达式结果
     * @param exception  异常信息
     */
    public static void isFalse(boolean expression, CommonException exception) {
        if (expression) {
            throw exception;
        }
    }

    /**
     * 判断是否为null，不为null则抛出异常
     *
     * @param object    判断对象
     * @param exception 异常信息
     */
    public static void isNull(Object object, String exception) {
        isNull(object, CommonException.error(exception));
    }

    /**
     * 判断是否为null，不为null则抛出异常
     *
     * @param object    判断对象
     * @param exception 异常信息
     */
    public static void isNull(Object object, ExceptionMessage exception) {
        isNull(object, CommonException.error(exception));
    }

    /**
     * 判断是否为null，不为null则抛出异常
     *
     * @param object    判断对象
     * @param exception 异常信息
     */
    public static void isNull(Object object, CommonException exception) {
        if (object != null) {
            throw exception;
        }
    }

    /**
     * 判断是否不为null，为null则抛出异常
     *
     * @param object    判断对象
     * @param exception 异常信息
     */
    public static void notNull(Object object, String exception) {
        notNull(object, CommonException.error(exception));
    }

    /**
     * 判断是否不为null，为null则抛出异常
     *
     * @param object    判断对象
     * @param exception 异常信息
     */
    public static void notNull(Object object, ExceptionMessage exception) {
        notNull(object, CommonException.error(exception));
    }

    /**
     * 判断是否不为null，为null则抛出异常
     *
     * @param object    判断对象
     * @param exception 异常信息
     */
    public static void notNull(Object object, CommonException exception) {
        if (object == null) {
            throw exception;
        }
    }

    /**
     * 判断是否为空字符串，不为空字符串则抛出异常
     *
     * @param str     需判断字符串
     * @param message 异常信息
     */
    public static void isEmpty(String str, String message) {
        isEmpty(str, CommonException.error(message));
    }

    /**
     * 判断是否为空字符串，不为空字符串则抛出异常
     *
     * @param str       需判断字符串
     * @param exception 异常信息
     */
    public static void isEmpty(String str, ExceptionMessage exception) {
        isEmpty(str, CommonException.error(exception));
    }

    /**
     * 判断是否为空字符串，不为空字符串则抛出异常
     *
     * @param str       需判断字符串
     * @param exception 异常信息
     */
    public static void isEmpty(String str, CommonException exception) {
        if (str != null && !str.isEmpty()) {
            throw exception;
        }
    }

    /**
     * 判断是否不为空字符串，为空字符串则抛出异常
     *
     * @param str     需判断字符串
     * @param message 异常信息
     */
    public static void isNotEmpty(String str, String message) {
        isNotEmpty(str, CommonException.error(message));
    }

    /**
     * 判断是否不为空字符串，为空字符串则抛出异常
     *
     * @param str       需判断字符串
     * @param exception 异常信息
     */
    public static void isNotEmpty(String str, ExceptionMessage exception) {
        isNotEmpty(str, CommonException.error(exception));
    }

    /**
     * 判断是否不为空字符串，为空字符串则抛出异常
     *
     * @param str       需判断字符串
     * @param exception 异常信息
     */
    public static void isNotEmpty(String str, CommonException exception) {
        if (str == null || str.isEmpty()) {
            throw exception;
        }
    }

    /**
     * 判断是否为空集合，不为空集合则抛出异常
     *
     * @param collection 需判断集合
     * @param exception  异常信息
     */
    public static <T> void isEmpty(Collection<T> collection, ExceptionMessage exception) {
        isEmpty(collection, CommonException.error(exception));
    }

    /**
     * 判断是否为空集合，不为空集合则抛出异常
     *
     * @param collection 需判断集合
     * @param exception  异常信息
     */
    public static <T> void isEmpty(Collection<T> collection, CommonException exception) {
        if (collection != null && !collection.isEmpty()) {
            throw exception;
        }
    }

    /**
     * 判断是否不为空集合，为空集合则抛出异常
     *
     * @param collection 需判断集合
     * @param exception  异常信息
     */
    public static <T> void isNotEmpty(Collection<T> collection, ExceptionMessage exception) {
        isNotEmpty(collection, CommonException.error(exception));
    }

    /**
     * 判断是否不为空集合，为空集合则抛出异常
     *
     * @param collection 需判断集合
     * @param exception  异常信息
     */
    public static <T> void isNotEmpty(Collection<T> collection, CommonException exception) {
        if (collection == null || collection.isEmpty()) {
            throw exception;
        }
    }

}
