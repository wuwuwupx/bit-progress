package com.bitprogress.ormparser.annotation;

import com.bitprogress.basemodel.enums.ValueEnum;
import com.bitprogress.ormcontext.context.TenantContextUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 租户类型
 */
@Getter
@AllArgsConstructor
public enum TenantType implements ValueEnum {

    /**
     * 当前租户，获取的值为 {@link TenantContextUtils#getTenantId()}
     */
    CURRENT(0),

    /**
     * 被操作租户，获取的值为 {@link TenantContextUtils#getOperateTenantId()}
     * 适用于租户将数据托管给平台操作的情况
     */
    OPERATE(1),

    ;

    private final Integer value;

}
