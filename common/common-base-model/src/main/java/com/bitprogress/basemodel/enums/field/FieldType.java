package com.bitprogress.basemodel.enums.field;

import com.bitprogress.basemodel.enums.FieldEnum;
import com.bitprogress.basemodel.enums.NameEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FieldType implements FieldEnum, ValueEnum, NameEnum {

    /**
     * 单行文本
     */
    SINGLE_LINE_TEXT(0, "单行文本"),

    /**
     * 多行文本
     */
    MULTI_LINE_TEXT(1, "多行文本"),

    /**
     * 单选
     */
    SINGLE_SELECT(2, "单选"),

    /**
     * 多选
     */
    MULTI_SELECT(3, "多选"),

    /**
     * 多级下拉
     */
    MULTI_LEVEL_DROPDOWN(4, "多级下拉"),

    /**
     * 自动编号
     */
    AUTO_NUMBER(5, "自动编号"),

    /**
     * 日期
     */
    DATE(6, "日期"),

    /**
     * 日期时间
     */
    DATE_TIME(7, "日期时间"),

    /**
     * 数值
     */
    NUMBER(8, "数值"),

    /**
     * 公式
     */
    FORMULA(9, "公式"),

    /**
     * 图片
     */
    IMAGE(10, "图片"),

    ;

    private final Integer value;

    private final String name;

}
