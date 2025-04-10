package com.bitprogress.ormcontext.context;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionMessage;
import com.bitprogress.ormcontext.info.TenantInfo;
import com.bitprogress.util.JsonUtils;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 租户上下文
 */
public class TenantContext {

    private static final ThreadLocal<TenantInfo> TENANT_INFO = new ThreadLocal<>();

    /**
     * 获取租户信息
     */
    public static TenantInfo getTenantInfo() {
        return TENANT_INFO.get();
    }

    /**
     * 获取租户信息
     */
    public static void setTenantInfo(TenantInfo tenantInfo) {
        TENANT_INFO.set(tenantInfo);
    }

    /**
     * 清除租户信息
     */
    public static void clearTenantInfo() {
        TENANT_INFO.remove();
    }

    /**
     * 获取租户信息
     */
    public static TenantInfo getTenantInfoOrNew() {
        return Optional
                .ofNullable(TENANT_INFO.get())
                .orElse(new TenantInfo());
    }

    /**
     * 获取租户信息
     */
    public static TenantInfo getTenantInfoOrThrow() {
        return getTenantInfoOrThrow("未读取到租户信息");
    }

    /**
     * 获取租户信息
     */
    public static TenantInfo getTenantInfoOrThrow(String message) {
        return Optional
                .ofNullable(TENANT_INFO.get())
                .orElseThrow(() -> CommonException.error(message));
    }

    /**
     * 获取租户信息
     */
    public static TenantInfo getTenantInfoOrThrow(ExceptionMessage exception) {
        return Optional
                .ofNullable(TENANT_INFO.get())
                .orElseThrow(() -> CommonException.error(exception));
    }

    /**
     * 获取租户信息
     */
    public static TenantInfo getTenantInfoOrThrow(CommonException exception) {
        return Optional
                .ofNullable(TENANT_INFO.get())
                .orElseThrow(() -> exception);
    }

    /**
     * 设置租户信息
     */
    public static void setTenantInfo(Long tenantId, Long operateTenantId) {
        TenantInfo tenantInfo = getTenantInfoOrNew();
        tenantInfo.setTenantId(tenantId);
        tenantInfo.setOperateTenantId(operateTenantId);
        TENANT_INFO.set(tenantInfo);
    }

    /**
     * 获取租户信息的某一信息
     */
    public static <T> T getFieldOrDefault(Function<TenantInfo, T> fieldFunction, T defaultValue) {
        return Optional
                .ofNullable(TENANT_INFO.get())
                .map(fieldFunction)
                .orElse(defaultValue);
    }

    /**
     * 获取租户信息的某一信息
     */
    public static <T> T getFieldOrThrow(Function<TenantInfo, T> fieldFunction,
                                        Supplier<? extends CommonException> supplier) {
        TenantInfo tenantInfo = getTenantInfoOrThrow();
        return Optional
                .of(tenantInfo)
                .map(fieldFunction)
                .orElseThrow(supplier);
    }

    /**
     * 获取租户序列化信息
     */
    public static String getTenantInfoJson() {
        return JsonUtils.serializeObject(getTenantInfo(), "");
    }

    /**
     * 反序列化租户信息
     */
    public static void setTenantInfoJson(String tenantInfoJson) {
        TENANT_INFO.set(JsonUtils.deserializeObject(tenantInfoJson, TenantInfo.class));
    }

}
