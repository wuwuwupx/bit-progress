package com.bitprogress.util;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.IExceptionMessage;

import java.util.Collection;

/**
 * @author wuwuwupx
 * create on 2021/6/27 2:09
 * 断言类
 */
public class Assert {

    /**
     * 判断是否为真，不为真则直接抛出异常
     *
     * @param expression
     * @param exception
     */
    public static void isTrue(boolean expression, IExceptionMessage exception) {
        isTrue(expression, CommonException.error(exception));
    }

    /**
     * 判断是否为真，不为真则直接抛出异常
     *
     * @param expression
     * @param exception
     */
    public static void isTrue(boolean expression, CommonException exception) {
        if (!expression) {
            throw exception;
        }
    }

    /**
     * 判断是否为null，不为null则抛出异常
     *
     * @param object
     * @param exception
     */
    public static void isNull(Object object, IExceptionMessage exception) {
        isNull(object, CommonException.error(exception));
    }

    /**
     * 判断是否为null，不为null则抛出异常
     *
     * @param object
     * @param exception
     */
    public static void isNull(Object object, CommonException exception) {
        if (object != null) {
            throw exception;
        }
    }

    /**
     * 判断是否不为null，为null则抛出异常
     *
     * @param object
     * @param exception
     */
    public static void notNull(Object object, IExceptionMessage exception) {
        notNull(object, CommonException.error(exception));
    }

    /**
     * 判断是否不为null，为null则抛出异常
     *
     * @param object
     * @param exception
     */
    public static void notNull(Object object, CommonException exception) {
        if (object == null) {
            throw exception;
        }
    }

    /**
     * 判断是否为空字符串，不为空字符串则抛出异常
     *
     * @param str
     * @param exception
     */
    public static void isEmpty(String str, IExceptionMessage exception) {
        isEmpty(str, CommonException.error(exception));
    }

    /**
     * 判断是否为空字符串，不为空字符串则抛出异常
     *
     * @param str
     * @param exception
     */
    public static void isEmpty(String str, CommonException exception) {
        if (str != null && !str.isEmpty()) {
            throw exception;
        }
    }

    /**
     * 判断是否不为空字符串，为空字符串则抛出异常
     *
     * @param str
     * @param exception
     */
    public static void isNotEmpty(String str, IExceptionMessage exception) {
        isNotEmpty(str, CommonException.error(exception));
    }

    /**
     * 判断是否不为空字符串，为空字符串则抛出异常
     *
     * @param str
     * @param exception
     */
    public static void isNotEmpty(String str, CommonException exception) {
        if (str == null || str.isEmpty()) {
            throw exception;
        }
    }

    /**
     * 判断是否为空集合，不为空集合则抛出异常
     *
     * @param collection
     * @param exception
     */
    public static void isEmpty(Collection collection, IExceptionMessage exception) {
        isEmpty(collection, CommonException.error(exception));
    }

    /**
     * 判断是否为空集合，不为空集合则抛出异常
     *
     * @param collection
     * @param exception
     */
    public static void isEmpty(Collection collection, CommonException exception) {
        if (collection != null && !collection.isEmpty()) {
            throw exception;
        }
    }

    /**
     * 判断是否不为空集合，为空集合则抛出异常
     *
     * @param collection
     * @param exception
     */
    public static void isNotEmpty(Collection collection, IExceptionMessage exception) {
        isNotEmpty(collection, CommonException.error(exception));
    }

    /**
     * 判断是否不为空集合，为空集合则抛出异常
     *
     * @param collection
     * @param exception
     */
    public static void isNotEmpty(Collection collection, CommonException exception) {
        if (collection == null || collection.isEmpty()) {
            throw exception;
        }
    }

}
