package com.bitprogress.ormmodel.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 租户类型
 */
@Getter
@AllArgsConstructor
public enum TenantType implements ValueEnum {

    /**
     * 当前租户，获取的值为
     */
    CURRENT(0),

    /**
     * 被操作租户，获取的值为
     * 适用于租户将数据托管给平台操作的情况
     */
    OPERATE(1),

    ;

    private final Integer value;

}
