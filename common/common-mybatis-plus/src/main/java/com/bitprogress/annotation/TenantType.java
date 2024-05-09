package com.bitprogress.annotation;

import com.bitprogress.context.TenantContext;

/**
 * 租户类型
 * 后续考虑拓展 租户列表，用于 {@link SqlType#SELECT}
 */
public enum TenantType {

    /**
     * 当前租户，获取的值为 {@link TenantContext#getTenantId()}
     */
    CURRENT,

    /**
     * 被操作租户，获取的值为 {@link TenantContext#getOperateTenantId()}
     * 适用于租户将数据托管给平台操作的情况
     */
    OPERATE,

}
