package com.bitprogress.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wuwuwupx
 * 数字工具类
 */
public class NumberUtils {

    /**
     * int类型的0
     */
    public static final int INT_ZERO = 0;

    /**
     * long类型的0
     */
    public static final long LONG_ZERO = 0L;

    /**
     * int类型的1
     */
    public static final int INT_ONE = 1;

    /**
     * long类型的1
     */
    public static final long LONG_ONE = 1L;

    /**
     * int类型的-1
     */
    public static final int INT_MINUS_ONE = -1;

    /**
     * long类型的-1
     */
    public static final int LONG_MINUS_ONE = -1;

    /**
     * int类型的200
     */
    public static final int INT_TWO_HUNDRED = 200;

    /**
     * 请求成功的状态码
     */
    public static final int REQUEST_SUCCESS = INT_TWO_HUNDRED;

    public static final String ALL_NUMBER = "0123456789";

    public static String randomNumbers(int length) {
        StringBuilder sb = new StringBuilder(length);
        if (length < 1) {
            length = 1;
        }
        int baseLength = ALL_NUMBER.length();

        for(int i = 0; i < length; ++i) {
            int number = ThreadLocalRandom.current().nextInt(baseLength);
            sb.append(ALL_NUMBER.charAt(number));
        }
        return sb.toString();
    }

}
