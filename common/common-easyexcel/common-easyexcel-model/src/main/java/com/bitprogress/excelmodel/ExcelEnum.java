package com.bitprogress.excelmodel;

import com.bitprogress.basemodel.IEnum;

import java.io.Serializable;

/**
 * excel 枚举接口
 */
public interface ExcelEnum<T extends Serializable> extends IEnum {

    /**
     * 获取枚举信息
     */
    T getMessage();

}
