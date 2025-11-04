package com.bitprogress.basemodel.enums.text;

import com.bitprogress.basemodel.enums.NameEnum;
import com.bitprogress.basemodel.enums.RegexEnum;
import com.bitprogress.basemodel.enums.TextEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TextType implements TextEnum, ValueEnum, NameEnum, RegexEnum {

    /**
     * 全部数字（\p{Nd} + \p{Nl} + \p{No}）
     */
    NUMBER(0, "全部数字", "\\p{N}"),

    /**
     * 十进制数字（0-9及其全角形式）
     * 0-9, ０-９
     */
    NUMBER_DIGIT(1, "十进制数字", "\\p{Nd}"),

    /**
     * 字母式数字（如罗马数字）
     * Ⅰ, Ⅱ, Ⅲ, Ⅳ, Ⅴ
     */
    NUMBER_LETTER(2, "字母式数字", "\\p{Nl}"),

    /**
     * 其他数字（带圈、分数、上标等）
     * ①, ², ½, ㊷, 卍
     */
    NUMBER_OTHER(3, "其他数字", "\\p{No}"),

    /**
     * 所有字母，所有语言的字母字符，A, Б, あ, 汉
     * \p{Lu} + \p{Ll} + \p{Lt} + \p{Lm} + \p{Lo}
     */
    LETTER(4, "字母", "\\p{L}"),

    /**
     * 大写字母，A, B, Ä, Σ
     */
    LETTER_UPPERCASE(5, "大写字母", "\\p{Lu}"),

    /**
     * 小写字母，a, b, ä, σ
     */
    LETTER_LOWERCASE(6, "小写字母", "\\p{Ll}"),

    /**
     * 标题字母，单词首字母大写形式（罕见）ǅ, ǈ, ǋ
     */
    LETTER_TITLE(7, "标题字母", "\\p{Lt}"),

    /**
     * 修饰字母，字母修饰符（如上标字母）	ª, º, ᵃ, ᵇ
     */
    LETTER_MODIFIER(8, "修饰字母", "\\p{Lm}"),

    /**
     * 其他字母，非大小写的字母（如汉字、假名）	汉, あ, ア, ㄅ
     */
    LETTER_OTHER(9, "其他字母", "\\p{Lo}"),

    /**
     * 拉丁字母，A, b, Ç, ñ
     */
    LETTER_LATIN(10, "拉丁字母", "\\p{Script=Latin}"),

    /**
     * 汉字
     */
    LETTER_HAN(11, "汉字", "\\p{Script=Han}"),

    /**
     * 西里尔字母，Б, д, Ё, ж
     */
    LETTER_CYRILLIC(12, "西里尔字母", "\\p{Script=Cyrillic}"),

    /**
     * 希腊字母，α, Β, Σ, ω
     */
    LETTER_GREEK(13, "希腊字母", "\\p{Script=Greek}"),

    /**
     * 日文假名，あ, ア, こ, コ
     */
    LETTER_HIRAGANA(14, "日文假名", "\\p{Script=Hiragana}"),

    /**
     * 韩文谚文，가, 나, 다, 한
     */
    LETTER_HANGUL(15, "韩文谚文", "\\p{Script=Hangul}"),

    /**
     * 注音符号，ㄅ, ㄆ, ㄇ, ㄈ
     */
    LETTER_BOPOMOFO(16, "注音符号", "\\p{Script=Bopomofo}"),

    /**
     * 标点符号
     */
    PUNCTUATION(17, "标点符号", "\\p{P}"),

    /**
     * 所有符号（So + Sm + Sc + Sk 的合集）
     */
    SYMBOL(18, "符号", "\\p{S}"),

    /**
     * 其他符号（非数学、非货币、非修饰符）
     */
    SYMBOL_OTHER(19, "其他符号", "\\p{So}"),

    /**
     * 数学符号（如变音符号、肤色修饰符）
     */
    SYMBOL_MATH(20, "数学符号", "\\p{Sm}"),

    /**
     * 货币符号
     */
    SYMBOL_CURRENCY(21, "货币符号", "\\p{Sc}"),

    /**
     * 修饰符号
     */
    SYMBOL_MODIFIER(22, "修饰符号", "\\p{Sk}"),

    ;

    private final Integer value;

    private final String name;

    private final String regex;

}
