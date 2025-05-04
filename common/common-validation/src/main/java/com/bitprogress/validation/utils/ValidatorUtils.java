package com.bitprogress.validation.utils;

import com.bitprogress.basemodel.enums.text.TextType;
import com.bitprogress.util.ArrayUtils;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ValidatorUtils {

    /**
     * 匹配数字、字母、下划线、汉字
     */
    private static final Pattern PATTERN_STANDARD_TEXT = Pattern.compile("^[a-z0-9A-Z_\\s\\u4E00-\\u9FA5]+$");

    /**
     * 匹配15位或18位身份证号码
     */
    private static final Pattern PATTERN_ID_CARD = Pattern.compile("\\d{15}(\\d{2}[0-9xX])?");

    /**
     * 匹配中文姓名，不过滤空格
     */
    private static final Pattern PATTERN_CHINESE_NAME = Pattern.compile("^[\\u4e00-\\u9fa5·]*$");
    /**
     * 匹配中文姓名，过滤空格
     */
    private static final Pattern PATTERN_CHINESE_NAME_SPACES = Pattern.compile("^[\\u4e00-\\u9fa5·\\s]*$");

    /**
     * 匹配手机号
     */
    private static final Pattern MOBILE_REGEX = Pattern.compile("^1[3-9]\\d{9}$");
    /**
     * 匹配座机
     */
    private static final Pattern LANDLINE_REGEX = Pattern.compile("^(0\\d{2,3}-\\d{7,8}(-\\d{1,5})?)|(\\d{7,8})$");

    /**
     * 匹配年份
     */
    private static final Pattern PATTERN_YEAR = Pattern.compile("^\\d{4}$");

    /**
     * 默认验证器
     */
    private static Validator VALIDATOR;

    static {
        try {
            VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
        } catch (Exception e) {
            VALIDATOR = null;
        }
    }

    /**
     * 默认分隔符
     */
    public static final String DEFAULT_DELIMITER = ",\n";

    /**
     * 判断是否匹配
     *
     * @param pattern 正则表达式
     * @param src     待匹配的字符串
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isLegal(Pattern pattern, String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m = pattern.matcher(src);
        return m.matches();
    }

    /**
     * 判断是否为手机号
     *
     * @param src 待匹配的字符串
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isMobile(String src) {
        return isLegal(MOBILE_REGEX, src);
    }

    /**
     * 判断是否为座机
     *
     * @param src 待匹配的字符串
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isLandline(String src) {
        return isLegal(LANDLINE_REGEX, src);
    }

    /**
     * 判断是否为手机号或座机
     *
     * @param src 待匹配的字符串
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isPhone(String src) {
        return isMobile(src) || isLandline(src);
    }

    /**
     * 判断是否为年份
     *
     * @param src 待匹配的字符串
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isYear(String src) {
        return isLegal(PATTERN_YEAR, src);
    }

    /**
     * 判断是否为字、字母、下划线、汉字组合字符串
     *
     * @param src 待匹配的字符串
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isStandardText(String src) {
        return isLegal(PATTERN_STANDARD_TEXT, src);
    }

    /**
     * 判断是否为身份证号码
     *
     * @param src 待匹配的字符串
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isIdCard(String src) {
        return isLegal(PATTERN_ID_CARD, src);
    }

    /**
     * 判断是否为中文姓名，默认不过滤空格
     *
     * @param src 待匹配的字符串
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isChineseName(String src) {
        return isChineseName(src, false);
    }

    /**
     * 判断是否为中文姓名
     *
     * @param src          待匹配的字符串
     * @param ignoreSpaces 是否过滤空格
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isChineseName(String src, boolean ignoreSpaces) {
        Pattern pattern = ignoreSpaces ? PATTERN_CHINESE_NAME_SPACES : PATTERN_CHINESE_NAME;
        return isLegal(pattern, src);
    }

    /**
     * 验证是否为允许的文本
     *
     * @param src          待匹配的文本
     * @param textTypes    允许的文本类型
     * @param ignoreSpaces 是否过滤空格
     * @return true：匹配成功，false：匹配失败
     */
    public static boolean isAllowedText(String src, TextType[] textTypes, boolean ignoreSpaces) {
        if (ArrayUtils.isEmpty(textTypes)) {
            return true;
        }
        String regex = Arrays.stream(textTypes)
                .map(TextType::getRegex)
                .collect(Collectors.joining());
        Pattern pattern = Pattern.compile(ignoreSpaces ? "^[" + regex + "]+\\s*$" : "^[" + regex + "]+$");
        return isLegal(pattern, src);
    }

    /**
     * 校验对象并返回原始结果集
     *
     * @param data 待校验对象
     * @return 原始验证结果集
     */
    public static <T> Set<ConstraintViolation<T>> validate(T data) {
        return VALIDATOR.validate(data);
    }

    /**
     * 校验对象并构建为去重的StringBuilder返回
     *
     * @param data 待校验对象
     * @param <T>  T
     * @return 封装为StringBuilder的去重结果集
     */
    public static <T> StringBuilder validateBuilder(T data) {
        return validateBuilder(data, DEFAULT_DELIMITER);
    }

    /**
     * 校验对象并构建为去重的StringBuilder返回
     *
     * @param data      待校验对象
     * @param delimiter 分隔符
     * @param <T>       T
     * @return 封装为StringBuilder的去重结果集
     */
    public static <T> StringBuilder validateBuilder(T data, String delimiter) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(data);
        StringBuilder failMessage = new StringBuilder();
        if (CollectionUtils.isNotEmpty(violations)) {
            Set<String> violationSet = CollectionUtils.toSet(violations, ConstraintViolation::getMessage);
            violationSet.forEach(violation -> failMessage.append(violation).append(delimiter));
        }
        return failMessage;
    }

    /**
     * 校验对象并构建为去重的String返回
     *
     * @param data 待校验对象
     * @param <T>  T
     * @return 验证结果字符串
     */
    public static <T> String validateStr(T data) {
        return validateStr(data, DEFAULT_DELIMITER);
    }

    /**
     * 校验对象并构建为去重的String返回
     *
     * @param data      待校验对象
     * @param delimiter 分隔符
     * @param <T>       T
     * @return 验证结果字符串
     */
    public static <T> String validateStr(T data, String delimiter) {
        StringBuilder failMessage = validateBuilder(data, delimiter);
        return failMessage.toString();
    }

}
