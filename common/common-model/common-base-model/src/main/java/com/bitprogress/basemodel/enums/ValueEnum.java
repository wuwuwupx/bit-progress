package com.bitprogress.basemodel.enums;

import com.bitprogress.basemodel.IEnum;

import java.io.Serializable;

/**
 * 需要定义value的枚举
 */
public interface ValueEnum<T extends Serializable> extends IEnum {

    /**
     * 获取枚举的value
     */
    T getValue();

}
