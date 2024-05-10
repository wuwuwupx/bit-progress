package com.bitprogress.util;

import com.bitprogress.exception.CommonException;

import java.util.Objects;

/**
 * 异常处理工具类
 */
public class ErrorUtils {

    /**
     * 获取异常的cause
     *
     * @param throwable 异常信息
     * @return cause
     */
    public static Throwable getCause(Throwable throwable) {
        return throwable.getCause();
    }

    /**
     * 异常是否还有底层cause
     *
     * @param throwable 传入的异常
     * @return true：还有底层cause，false：没有底层cause
     */
    public static boolean hasCause(Throwable throwable) {
        return Objects.nonNull(throwable.getCause());
    }

    /**
     * 获取最底层的cause
     *
     * @param throwable 传入的异常
     * @return 最底层cause
     */
    public static Throwable getLowestCause(Throwable throwable) {
        while (hasCause(throwable)) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    /**
     * 获取最底层cause的message
     *
     * @param throwable 传入的异常
     * @return 最底层cause的message
     */
    public static String getLowestMessage(Throwable throwable) {
        Throwable lowestCause = getLowestCause(throwable);
        if (lowestCause instanceof CommonException) {
            CommonException exception = (CommonException) lowestCause;
            return exception.getMessage();
        }
        return lowestCause.getMessage();
    }

}
