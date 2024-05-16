package com.bitprogress.basemodel.enums;

import com.bitprogress.basemodel.IEnum;

/**
 * 需要定义value的枚举
 */
public interface ValueEnum extends IEnum {

    /**
     * 获取枚举的value
     */
    Integer getValue();

}
