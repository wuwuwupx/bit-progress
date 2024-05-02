package com.bitprogress.util;

import com.bitprogress.constant.StringConstants;

import java.util.Random;
import java.util.UUID;

/**
 * @author wuwuwupx
 * create on 2021/5/31 22:40
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static final String SPACE = " ";

    public static final String ZERO = "0";

    public static final String MINUS_ONE = "-1";

    public static final String STRING_VALUE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean nonEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean startWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return startWith(str, prefix, true);
    }

    public static boolean startWith(CharSequence str, CharSequence prefix, boolean ignoreCase) {
        return startWith(str, prefix, ignoreCase, false);
    }

    /**
     * 检查字符串是否由指定字符开始
     *
     * @param str          检查字符串
     * @param prefix       需要检查前缀
     * @param ignoreCase   是否忽略大小写
     * @param ignoreEquals 是否忽略相等情况
     */
    public static boolean startWith(CharSequence str, CharSequence prefix, boolean ignoreCase, boolean ignoreEquals) {
        if (null != str && null != prefix) {
            boolean isStartWith;
            if (ignoreCase) {
                isStartWith = str.toString().toLowerCase().startsWith(prefix.toString().toLowerCase());
            } else {
                isStartWith = str.toString().startsWith(prefix.toString());
            }

            if (!isStartWith) {
                return false;
            } else {
                return !ignoreEquals || !equals(str, prefix, ignoreCase);
            }
        } else if (!ignoreEquals) {
            return false;
        } else {
            return null == str && null == prefix;
        }
    }

    /**
     * 比较两个字符串（大小写敏感）。
     *
     * <pre>
     * equals(null, null)   = true
     * equals(null, &quot;abc&quot;)  = false
     * equals(&quot;abc&quot;, null)  = false
     * equals(&quot;abc&quot;, &quot;abc&quot;) = true
     * equals(&quot;abc&quot;, &quot;ABC&quot;) = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是{@code null}，则返回{@code true}
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }

    /**
     * 比较两个字符串是否相等。
     *
     * @param str1       要比较的字符串1
     * @param str2       要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 如果两个字符串相同，或者都是{@code null}，则返回{@code true}
     * created on 3.2.0
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都为null才判断相等
            return str2 == null;
        }
        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.toString().contentEquals(str2);
        }
    }

    /**
     * 驼峰转换
     * camelCase -> camel_case
     *
     * @param str 需要转换的字符串
     */
    public static String camelCase(String str) {
        if (isEmpty(str)) {
            return "";
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(StringConstants.UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 生成随机字符串
     *
     * @param len 字符串长度
     */
    public static String randomString(int len) {
        StringBuilder builder = new StringBuilder(len);
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(STRING_VALUE.length());
            builder.append(STRING_VALUE.charAt(index));
        }
        return builder.toString();
    }

    /**
     * 生成32位随机字符串
     */
    public static String randomStringByUUID() {
        return UUID.randomUUID().toString().replaceAll(StringConstants.MINUS_SIGN, StringConstants.EMPTY);
    }

    /**
     * 判断是否为空，并且不是空白字符
     *
     * @param str 要判断的value
     * @return 结果
     */
    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
