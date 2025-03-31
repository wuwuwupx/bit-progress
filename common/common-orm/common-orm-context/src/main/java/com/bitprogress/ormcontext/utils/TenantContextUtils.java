package com.bitprogress.ormcontext.utils;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionMessage;
import com.bitprogress.ormcontext.context.TenantContext;
import com.bitprogress.ormcontext.entity.TenantInfo;
import com.bitprogress.ormmodel.enums.TenantType;

import java.util.HashSet;
import java.util.Set;

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
    public static Long getTenantIdOrThrow(ExceptionMessage exception) {
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
    public static Long getOperateTenantIdOrThrow(ExceptionMessage exception) {
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
    public static void setOperateTenantIdOrThrow(Long operateTenantId, ExceptionMessage exception) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrThrow(exception);
        tenantInfo.setOperateTenantId(operateTenantId);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 获取 tenantType
     */
    public static TenantType getTenantType() {
        return TenantContext.getFieldOrDefault(TenantInfo::getTenantType, null);
    }

    /**
     * 获取 tenantType
     */
    public static TenantType getTenantTypeOrDefault() {
        return TenantContext.getFieldOrDefault(TenantInfo::getTenantType, TenantType.CURRENT);
    }

    /**
     * 获取 tenantType
     */
    public static TenantType getTenantTypeOrThrow() {
        return TenantContext.getFieldOrThrow(TenantInfo::getTenantType, () -> CommonException.error("租户类型未读取到"));
    }

    /**
     * 获取 tenantType
     */
    public static TenantType getTenantTypeOrThrow(String message) {
        return TenantContext.getFieldOrThrow(TenantInfo::getTenantType, () -> CommonException.error(message));
    }

    /**
     * 获取 tenantType
     */
    public static TenantType getTenantTypeOrThrow(ExceptionMessage exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getTenantType, () -> CommonException.error(exception));
    }

    /**
     * 获取 tenantType
     */
    public static TenantType getTenantTypeOrThrow(CommonException exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getTenantType, () -> exception);
    }

    /**
     * 设置 tenantType
     */
    public static void setTenantType(TenantType tenantType) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrNew();
        tenantInfo.setTenantType(tenantType);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 设置 tenantType
     */
    public static void setTenantTypeOrThrow(TenantType tenantType, String exception) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrThrow(exception);
        tenantInfo.setTenantType(tenantType);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 设置 tenantType
     */
    public static void setTenantTypeOrThrow(TenantType tenantType, ExceptionMessage exception) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrThrow(exception);
        tenantInfo.setTenantType(tenantType);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 设置 tenantType
     */
    public static void setTenantTypeOrThrow(TenantType tenantType, CommonException exception) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrThrow(exception);
        tenantInfo.setTenantType(tenantType);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 获取 是否可以操作所有租户
     */
    public static Boolean getCanOperateAllTenant() {
        return TenantContext.getFieldOrDefault(TenantInfo::getCanOperateAllTenant, false);
    }

    /**
     * 获取 是否可以操作所有租户
     */
    public static Boolean getCanOperateAllTenantOrDefault() {
        return TenantContext.getFieldOrDefault(TenantInfo::getCanOperateAllTenant, false);
    }

    /**
     * 获取 是否可以操作所有租户
     */
    public static Boolean getCanOperateAllTenantOrThrow() {
        return TenantContext.getFieldOrThrow(TenantInfo::getCanOperateAllTenant, () -> CommonException.error("是否可以操作所有租户未读取到"));
    }

    /**
     * 获取 是否可以操作所有租户
     */
    public static Boolean getCanOperateAllTenantOrThrow(String message) {
        return TenantContext.getFieldOrThrow(TenantInfo::getCanOperateAllTenant, () -> CommonException.error(message));
    }

    /**
     * 获取 是否可以操作所有租户
     */
    public static Boolean getCanOperateAllTenantOrThrow(ExceptionMessage exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getCanOperateAllTenant, () -> CommonException.error(exception));
    }

    /**
     * 获取 是否可以操作所有租户
     */
    public static Boolean getCanOperateAllTenantOrThrow(CommonException exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getCanOperateAllTenant, () -> exception);
    }

    /**
     * 设置 是否可以操作所有租户
     */
    public void setCanOperateAllTenant(Boolean canOperateAllTenant) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrNew();
        tenantInfo.setCanOperateAllTenant(canOperateAllTenant);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 设置 是否可以操作所有租户
     */
    public static void setCanOperateAllTenantOrThrow(Boolean canOperateAllTenant, String exception) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrThrow(exception);
        tenantInfo.setCanOperateAllTenant(canOperateAllTenant);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 获取 operateTenantIds
     */
    public static Set<Long> getOperateTenantIds() {
        return TenantContext.getFieldOrDefault(TenantInfo::getOperateTenantIds, null);
    }

    /**
     * 设置 operateTenantIds
     */
    public static void setOperateTenantIds(Set<Long> operateTenantIds) {
        TenantInfo tenantInfo = TenantContext.getTenantInfoOrNew();
        tenantInfo.setOperateTenantIds(operateTenantIds);
        TenantContext.setTenantInfo(tenantInfo);
    }

    /**
     * 获取 operateTenantIds
     */
    public static Set<Long> getOperateTenantIdsOrDefault() {
        return TenantContext.getFieldOrDefault(TenantInfo::getOperateTenantIds, new HashSet<>());
    }

    /**
     * 获取 operateTenantIds
     */
    public static Set<Long> getOperateTenantIdsOrThrow() {
        return TenantContext.getFieldOrThrow(TenantInfo::getOperateTenantIds, () -> CommonException.error("可操作租户集合未读取到"));
    }

    /**
     * 获取 operateTenantIds
     */
    public static Set<Long> getOperateTenantIdsOrThrow(String message) {
        return TenantContext.getFieldOrThrow(TenantInfo::getOperateTenantIds, () -> CommonException.error(message));
    }

    /**
     * 获取 operateTenantIds
     */
    public static Set<Long> getOperateTenantIdsOrThrow(ExceptionMessage exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getOperateTenantIds, () -> CommonException.error(exception));
    }

    /**
     * 获取 operateTenantIds
     */
    public static Set<Long> getOperateTenantIdsOrThrow(CommonException exception) {
        return TenantContext.getFieldOrThrow(TenantInfo::getOperateTenantIds, () -> exception);
    }

    /**
     * 当前操作为全局模式
     */
    public static boolean isOperateAllTenant() {
        return isOperateAllTenant(getTenantType());
    }

    /**
     * 当前操作为全局模式
     */
    public static boolean isOperateAllTenant(TenantType tenantType) {
        return TenantType.ALL.equals(tenantType) && Boolean.TRUE.equals(getCanOperateAllTenant());
    }

}
