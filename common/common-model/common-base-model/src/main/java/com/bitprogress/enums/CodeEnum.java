package com.bitprogress.enums;

import com.bitprogress.IEnum;

import java.io.Serializable;

/**
 * 需要定义code的枚举
 */
public interface CodeEnum<T extends Serializable> extends IEnum {

    /**
     * 获取枚举的code
     */
    T getCode();

}
