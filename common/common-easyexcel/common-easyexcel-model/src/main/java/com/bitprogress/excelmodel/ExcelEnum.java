package com.bitprogress.excelmodel;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;

/**
 * excel 枚举接口
 * 因为excel展示多为文本信息，所以 Message 接口是默认的转换策略接口
 *
 * @see MessageEnum 才是关键接口
 */
public interface ExcelEnum extends ValueEnum, MessageEnum {

}
