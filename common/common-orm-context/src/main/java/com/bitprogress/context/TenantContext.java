package com.bitprogress.context;

/**
 * 租户上下文
 */
public class TenantContext {

    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();

    private static final ThreadLocal<Long> OPERATE_TENANT_ID = new ThreadLocal<>();

    /**
     * 获取 tenantId
     */
    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    /**
     * 设置 tenantId
     */
    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    /**
     * 获取 operateTenantId
     */
    public static Long getOperateTenantId() {
        return OPERATE_TENANT_ID.get();
    }

    /**
     * 设置 operateTenantId
     */
    public static void setOperateTenantId(Long operateTenantId) {
        OPERATE_TENANT_ID.set(operateTenantId);
    }

}
