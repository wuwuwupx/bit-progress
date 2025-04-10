package com.bitprogress.ormcontext.utils;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionMessage;
import com.bitprogress.ormcontext.context.SingleTypeDataScopeContext;
import com.bitprogress.ormcontext.info.SingleTypeDataScopeInfo;
import com.bitprogress.ormmodel.enums.DataScopeType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DataScopeContextUtils {

    /**
     * 获取数据范围类型
     */
    public static DataScopeType getDataScopeType() {
        return SingleTypeDataScopeContext.getFieldOrDefault(SingleTypeDataScopeInfo::getDataScopeType, null);
    }

    /**
     * 获取数据范围类型，为空则返回 {@link DataScopeType#SELF}
     * 即默认只能访问自身数据
     */
    public static DataScopeType getDataScopeTypeOrDefault() {
        return SingleTypeDataScopeContext.getFieldOrDefault(SingleTypeDataScopeInfo::getDataScopeType, DataScopeType.SELF);
    }

    /**
     * 获取数据范围类型，为空则返回 传入的值
     */
    public static DataScopeType getDataScopeTypeOrDefault(DataScopeType dataScopeType) {
        return SingleTypeDataScopeContext.getFieldOrDefault(SingleTypeDataScopeInfo::getDataScopeType, dataScopeType);
    }

    /**
     * 获取数据范围类型，为空则抛出异常
     */
    public static DataScopeType getDataScopeTypeOrThrow() {
        return SingleTypeDataScopeContext
                .getFieldOrThrow(SingleTypeDataScopeInfo::getDataScopeType, () -> CommonException.error("数据范围类型未读取到"));
    }

    /**
     * 获取数据范围类型，为空则抛出异常
     */
    public static DataScopeType getDataScopeTypeOrThrow(String message) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getDataScopeType, () -> CommonException.error(message));
    }

    /**
     * 获取数据范围类型，为空则抛出异常
     */
    public static DataScopeType getDataScopeTypeOrThrow(ExceptionMessage exception) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getDataScopeType, () -> CommonException.error(exception));
    }

    /**
     * 获取数据范围类型，为空则抛出异常
     */
    public static DataScopeType getDataScopeTypeOrThrow(CommonException exception) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getDataScopeType, () -> exception);
    }

    /**
     * 设置数据范围类型
     */
    public static void setDataScopeType(DataScopeType dataScopeType) {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = SingleTypeDataScopeContext.getDataScopeInfoOrNew();
        singleTypeDataScopeInfo.setDataScopeType(dataScopeType);
        SingleTypeDataScopeContext.setDataScopeInfo(singleTypeDataScopeInfo);
    }

    /**
     * 设置数据范围类型，未初始化则抛出异常
     */
    public static void setDataScopeTypeOrThrow(DataScopeType dataScopeType, String message) {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = SingleTypeDataScopeContext.getDataScopeInfoOrThrow(message);
        singleTypeDataScopeInfo.setDataScopeType(dataScopeType);
        SingleTypeDataScopeContext.setDataScopeInfo(singleTypeDataScopeInfo);
    }

    /**
     * 清除数据范围类型
     */
    public static void clearDataScopeType() {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = SingleTypeDataScopeContext.getDataScopeInfo();
    }

    /**
     * 获取数据范围
     */
    public static String getDataScope() {
        return getDataScopeOrDefault();
    }

    /**
     * 获取数据范围，默认返回空
     */
    public static String getDataScopeOrDefault() {
        return SingleTypeDataScopeContext.getFieldOrDefault(SingleTypeDataScopeInfo::getDataScope, "");
    }

    /**
     * 获取数据范围，为空则抛出异常
     */
    public static String getDataScopeOrThrow() {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getDataScope, () -> CommonException.error("数据范围未读取到"));
    }

    /**
     * 获取数据范围，为空则抛出异常
     */
    public static String getDataScopeOrThrow(String message) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getDataScope, () -> CommonException.error(message));
    }

    /**
     * 获取数据范围，为空则抛出异常
     */
    public static String getDataScopeOrThrow(ExceptionMessage exception) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getDataScope, () -> CommonException.error(exception));
    }

    /**
     * 获取数据范围，为空则抛出异常
     */
    public static String getDataScopeOrThrow(CommonException exception) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getDataScope, () -> exception);
    }

    /**
     * 获取可查询数据范围列表
     */
    public static Set<String> getDataScopes() {
        return getDataScopesOrDefault();
    }

    /**
     * 获取可查询数据范围列表，默认返回空
     */
    public static Set<String> getDataScopesOrDefault() {
        return getDataScopesOrDefault(new HashSet<>());
    }

    /**
     * 获取可查询数据范围列表，为空则返回 传入的值
     */
    public static Set<String> getDataScopesOrDefault(Set<String> dataScopes) {
        return SingleTypeDataScopeContext.getFieldOrDefault(SingleTypeDataScopeInfo::getManagedDataScopes, dataScopes);
    }

    /**
     * 获取可查询数据范围列表，为空则抛出异常
     */
    public static Set<String> getDataScopesOrThrow() {
        return getDataScopesOrThrow("可查询数据范围未读取到");
    }

    /**
     * 获取可查询数据范围列表，为空则抛出异常
     */
    public static Set<String> getDataScopesOrThrow(String message) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getManagedDataScopes, () -> CommonException.error(message));
    }

    /**
     * 获取可查询数据范围列表，为空则抛出异常
     */
    public static Set<String> getDataScopesOrThrow(ExceptionMessage exception) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getManagedDataScopes, () -> CommonException.error(exception));
    }

    /**
     * 获取可查询数据范围列表，为空则抛出异常
     */
    public static Set<String> getDataScopesOrThrow(CommonException exception) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getManagedDataScopes, () -> exception);
    }

    /**
     * 设置可查询数据范围列表
     * 覆盖原本的数据范围信息
     */
    public static void setDataScopes(String... dataScopes) {
        setDataScopes(Arrays.asList(dataScopes));
    }

    /**
     * 设置可查询数据范围列表
     * 覆盖原本的数据范围信息
     */
    public static void setDataScopes(Collection<String> dataScopes) {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = SingleTypeDataScopeContext.getDataScopeInfoOrNew();
        Set<String> dataScopeSet = new HashSet<>(dataScopes);
        singleTypeDataScopeInfo.setManagedDataScopes(dataScopeSet);
        SingleTypeDataScopeContext.setDataScopeInfo(singleTypeDataScopeInfo);
    }

    /**
     * 设置可查询数据范围列表
     * 覆盖原本的数据范围信息
     */
    public static void setDataScopes(Set<String> dataScopes) {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = SingleTypeDataScopeContext.getDataScopeInfoOrNew();
        singleTypeDataScopeInfo.setManagedDataScopes(dataScopes);
        SingleTypeDataScopeContext.setDataScopeInfo(singleTypeDataScopeInfo);
    }

    /**
     * 新增可查询数据范围列表
     * 不覆盖原本的数据范围信息
     */
    public static void addDataScopes(String... dataScopes) {
        addDataScopes(Arrays.asList(dataScopes));
    }

    /**
     * 新增可查询数据范围列表
     * 不覆盖原本的数据范围信息
     */
    public static void addDataScopes(Collection<String> dataScopes) {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = SingleTypeDataScopeContext.getDataScopeInfoOrNew();
        Set<String> dataScopeSet = singleTypeDataScopeInfo.getManagedDataScopes();
        dataScopeSet.addAll(dataScopes);
        singleTypeDataScopeInfo.setManagedDataScopes(dataScopeSet);
        SingleTypeDataScopeContext.setDataScopeInfo(singleTypeDataScopeInfo);
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return SingleTypeDataScopeContext.getFieldOrDefault(SingleTypeDataScopeInfo::getUserId, null);
    }

    /**
     * 获取用户ID，为空则返回 0
     */
    public static Long getUserIdOrDefault() {
        return SingleTypeDataScopeContext.getFieldOrDefault(SingleTypeDataScopeInfo::getUserId, 0L);
    }

    /**
     * 获取用户ID，为空返回 传入的值
     */
    public static Long getUserIdOrDefault(Long userId) {
        return SingleTypeDataScopeContext.getFieldOrDefault(SingleTypeDataScopeInfo::getUserId, userId);
    }

    /**
     * 获取用户ID，为空则抛出异常
     */
    public static Long getUserIdOrThrow() {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getUserId, () -> CommonException.error("用户ID未读取到"));
    }

    /**
     * 获取用户ID，为空则抛出异常
     */
    public static Long getUserIdOrThrow(String message) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getUserId, () -> CommonException.error(message));
    }

    /**
     * 获取用户ID，为空则抛出异常
     */
    public static Long getUserIdOrThrow(ExceptionMessage exception) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getUserId, () -> CommonException.error(exception));
    }

    /**
     * 获取用户ID，为空则抛出异常
     */
    public static Long getUserIdOrThrow(CommonException exception) {
        return SingleTypeDataScopeContext.getFieldOrThrow(SingleTypeDataScopeInfo::getUserId, () -> exception);
    }

    /**
     * 设置用户ID
     */
    public static void setUserId(Long userId) {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = SingleTypeDataScopeContext.getDataScopeInfoOrNew();
        singleTypeDataScopeInfo.setUserId(userId);
        SingleTypeDataScopeContext.setDataScopeInfo(singleTypeDataScopeInfo);
    }

}
