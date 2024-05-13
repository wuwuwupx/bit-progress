package com.bitprogress.annotation;

import com.bitprogress.context.TenantContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 租户类型
 * 后续考虑拓展 租户列表，用于 {@link SqlType#SELECT}
 */
@Getter
@AllArgsConstructor
public enum TenantType {

    /**
     * 当前租户，获取的值为 {@link TenantContext#getTenantId()}
     */
    CURRENT(0),

    /**
     * 被操作租户，获取的值为 {@link TenantContext#getOperateTenantId()}
     * 适用于租户将数据托管给平台操作的情况
     */
    OPERATE(1),

    ;

    private final int value;

}
