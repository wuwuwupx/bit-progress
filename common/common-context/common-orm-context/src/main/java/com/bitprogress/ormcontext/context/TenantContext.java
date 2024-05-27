package com.bitprogress.ormcontext.context;

import com.bitprogress.ormcontext.entity.TenantInfo;
import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.IException;
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
    public static TenantInfo getTenantInfoOrThrow(IException exception) {
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
     * 获取 tenantId
     */
    public static Long getTenantId() {
        return getTenantIdOrDefault(null);
    }

    /**
     * 获取 tenantId，默认返回 0
     */
    public static Long getTenantIdOrDefault() {
        return getTenantIdOrDefault(0L);
    }

    /**
     * 获取 tenantId，为空则返回 传入的值
     */
    public static Long getTenantIdOrDefault(Long tenantId) {
        return getFieldOrDefault(TenantInfo::getTenantId, tenantId);
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getTenantIdOrThrow() {
        return getTenantIdOrThrow("租户ID未读取到");
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getTenantIdOrThrow(String message) {
        return getFieldOrThrow(TenantInfo::getTenantId, () -> CommonException.error(message));
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getTenantIdOrThrow(IException exception) {
        return getFieldOrThrow(TenantInfo::getTenantId, () -> CommonException.error(exception));
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getTenantIdOrThrow(CommonException exception) {
        return getFieldOrThrow(TenantInfo::getTenantId, () -> exception);
    }

    /**
     * 设置 tenantId
     */
    public static void setTenantId(Long tenantId) {
        TenantInfo tenantInfo = getTenantInfoOrNew();
        tenantInfo.setTenantId(tenantId);
        TENANT_INFO.set(tenantInfo);
    }

    /**
     * 获取 operateTenantId
     */
    public static Long getOperateTenantId() {
        return getOperateTenantIdOrDefault(null);
    }

    /**
     * 获取 operateTenantId，默认返回 0
     */
    public static Long getOperateTenantIdOrDefault() {
        return getOperateTenantIdOrDefault(0L);
    }

    /**
     * 获取 operateTenantId，默认返回 传入的值
     */
    public static Long getOperateTenantIdOrDefault(Long operateTenantId) {
        return getFieldOrDefault(TenantInfo::getOperateTenantId, operateTenantId);
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getOperateTenantIdOrThrow() {
        return getOperateTenantIdOrThrow("操作租户ID未读取到");
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getOperateTenantIdOrThrow(String message) {
        return getFieldOrThrow(TenantInfo::getOperateTenantId, () -> CommonException.error(message));
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getOperateTenantIdOrThrow(IException exception) {
        return getFieldOrThrow(TenantInfo::getOperateTenantId, () -> CommonException.error(exception));
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getOperateTenantIdOrThrow(CommonException exception) {
        return getFieldOrThrow(TenantInfo::getOperateTenantId, () -> exception);
    }

    /**
     * 设置 operateTenantId
     */
    public static void setOperateTenantId(Long operateTenantId) {
        TenantInfo tenantInfo = getTenantInfoOrNew();
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

    public static String getTenantInfoJson() {
        return JsonUtils.serializeObject(getTenantInfo());
    }

    public static void setTenantInfoJson(String tenantInfoJson) {
        TENANT_INFO.set(JsonUtils.deserializeObject(tenantInfoJson, TenantInfo.class));
    }

}
