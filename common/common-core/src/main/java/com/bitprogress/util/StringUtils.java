package com.bitprogress.util;

import com.bitprogress.basemodel.constant.StringConstants;

import java.util.Random;
import java.util.UUID;

/**
 * @author wuwuwupx
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static final String SPACE = " ";

    public static final String ZERO = "0";

    public static final String MINUS_ONE = "-1";

    public static final String STRING_VALUE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final String DEFAULT_CAMEL_CASE_DELIMITER = "_";

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 字符串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 检查字符串是否以指定字符开始
     *
     * @param str    检查字符串
     * @param prefix 需要检查前缀
     */
    public static boolean startWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return startWith(str, prefix, true);
    }

    /**
     * 检查字符串是否以指定字符开始
     *
     * @param str        检查字符串
     * @param prefix     需要检查前缀
     * @param ignoreCase 是否忽略大小写
     */
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
     * 反向驼峰转换，使用默认分隔符 _
     * firstNameLastName -> first_name_last_name
     * FirstNameLastName -> first_name_last_name
     * FIRST_NAME_LAST_NAME -> first_name_last_name
     *
     * @param str 需要转换的字符串
     */
    public static String reverseCamelCase(String str) {
        return reverseCamelCase(str, DEFAULT_CAMEL_CASE_DELIMITER);
    }

    /**
     * 反向驼峰转换
     * 假设 ${camelCaseDelimiter} 为 _
     * firstNameLastName -> first_name_last_name
     * FirstNameLastName -> first_name_last_name
     * FIRST_NAME_LAST_NAME -> first_name_last_name
     *
     * @param str 需要转换的字符串
     */
    public static String reverseCamelCase(String str, String camelCaseDelimiter) {
        if (isEmpty(str)) {
            return "";
        }
        int length = str.length();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    result.append(camelCaseDelimiter);
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * 小驼峰命名转换方法，使用默认分隔符 _
     * first_name_last_name -> firstNameLastName
     *
     * @param input 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toLowerCamelCase(String input) {
        return toLowerCamelCase(input, DEFAULT_CAMEL_CASE_DELIMITER);
    }

    /**
     * 小驼峰命名转换方法
     * first_name_last_name -> firstNameLastName
     *
     * @param input              需要转换的字符串
     * @param camelCaseDelimiter 驼峰分隔符
     * @return 转换后的字符串
     */
    public static String toLowerCamelCase(String input, String camelCaseDelimiter) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split(camelCaseDelimiter);
        StringBuilder result = new StringBuilder(words[0].toLowerCase());

        for (int i = 1; i < words.length; i++) {
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1).toLowerCase());
        }

        return result.toString();
    }

    /**
     * 大驼峰命名转换方法，使用默认分隔符 _
     * first_name_last_name -> FirstNameLastName
     *
     * @param input 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toUpperCamelCase(String input) {
        return toUpperCamelCase(input, DEFAULT_CAMEL_CASE_DELIMITER);
    }

    /**
     * 大驼峰命名转换方法
     * first_name_last_name -> FirstNameLastName
     *
     * @param input              需要转换的字符串
     * @param camelCaseDelimiter 驼峰分隔符
     * @return 转换后的字符串
     */
    public static String toUpperCamelCase(String input, String camelCaseDelimiter) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split(camelCaseDelimiter);
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase());
        }

        return result.toString();
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
     * 判断字符串是否包含文本
     *
     * @param str 要判断的value
     * @return 结果
     */
    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    /**
     * 判断字符串是否包含文本
     *
     * @param str 要判断的value
     * @return 结果
     */
    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 去掉小数点后多余的0
     *
     * @param str 传入的字符串
     * @return 去除多余0后的字符串
     */
    public static String stripTrailingZeros(String str) {
        // 去掉小数点后的0
        if (str.contains(".")) {
            str = str.replaceAll("\\.0*$", "");
            str = str.replaceAll("\\.([1-9]*)0+$", ".$1");
        }
        return str;
    }

}
