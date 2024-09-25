package com.bitprogress.ormcontext.context;

import com.bitprogress.basemodel.IException;
import com.bitprogress.exception.CommonException;
import com.bitprogress.ormcontext.entity.TenantInfo;
import com.bitprogress.util.JsonUtils;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 租户上下文
 */
public class TenantContextUtils {

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
        return TenantContext.getFieldOrDefault(TenantInfo::getTenantId, tenantId);
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
        return TenantContext.getFieldOrThrow(TenantInfo::getTenantId, () -> CommonException.error(message));
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getTenantIdOrThrow(IException exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getTenantId, () -> CommonException.error(exception));
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getTenantIdOrThrow(CommonException exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getTenantId, () -> exception);
    }

    /**
     * 设置 tenantId
     */
    public static void setTenantId(Long tenantId) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrNew();
        tenantInfo.setTenantId(tenantId);
        TenantContext.setTenantInfo(tenantInfo);
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
        return TenantContext.getFieldOrDefault(TenantInfo::getOperateTenantId, operateTenantId);
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
        return TenantContext.getFieldOrThrow(TenantInfo::getOperateTenantId, () -> CommonException.error(message));
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getOperateTenantIdOrThrow(IException exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getOperateTenantId, () -> CommonException.error(exception));
    }

    /**
     * 获取 tenantId，为空则抛出异常
     */
    public static Long getOperateTenantIdOrThrow(CommonException exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getOperateTenantId, () -> exception);
    }

    /**
     * 设置 operateTenantId
     */
    public static void setOperateTenantId(Long operateTenantId) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrNew();
        tenantInfo.setOperateTenantId(operateTenantId);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 设置 operateTenantId
     */
    public static void setOperateTenantIdOrThrow(Long operateTenantId, String exception) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrThrow(exception);
        tenantInfo.setOperateTenantId(operateTenantId);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 设置 operateTenantId
     */
    public static void setOperateTenantIdOrThrow(Long operateTenantId, CommonException exception) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrThrow(exception);
        tenantInfo.setOperateTenantId(operateTenantId);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 设置 operateTenantId
     */
    public static void setOperateTenantIdOrThrow(Long operateTenantId, IException exception) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrThrow(exception);
        tenantInfo.setOperateTenantId(operateTenantId);
        TenantContext.setTenantInfo(tenantInfo);
    }

}
