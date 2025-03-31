package com.bitprogress.exception.util;

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
        return Objects.nonNull(getCause(throwable));
    }

    /**
     * 获取最底层的cause
     *
     * @param throwable   传入的异常
     * @param endAtCommon 是否在遇到CommonException时结束
     * @return 最底层cause
     */
    public static Throwable getLowestCause(Throwable throwable, Boolean endAtCommon) {
        if (endAtCommon && throwable instanceof CommonException) {
            return throwable;
        }
        while (hasCause(throwable)) {
            throwable = getCause(throwable);
            if (endAtCommon && throwable instanceof CommonException) {
                return throwable;
            }
        }
        return throwable;
    }

    /**
     * 获取最底层cause的message
     * 默认遇到commonException时结束
     *
     * @param throwable 传入的异常
     * @return 最底层cause的message
     */
    public static String getLowestMessage(Throwable throwable) {
        return getLowestMessage(throwable, true);
    }

    /**
     * 获取最底层cause的message
     *
     * @param throwable   传入的异常
     * @param endAtCommon 是否在遇到CommonException时结束
     * @return 最底层cause的message
     */
    public static String getLowestMessage(Throwable throwable, Boolean endAtCommon) {
        Throwable lowestCause = getLowestCause(throwable, endAtCommon);
        return lowestCause.getMessage();
    }

}
