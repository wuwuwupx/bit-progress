package com.bitprogress.ormcontext.context;

import com.bitprogress.basemodel.IException;
import com.bitprogress.exception.CommonException;
import com.bitprogress.ormcontext.entity.DataScopeInfo;
import com.bitprogress.util.JsonUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class DataScopeContext {

    private static final ThreadLocal<DataScopeInfo> DATA_SCOPE_INFO = new ThreadLocal<>();

    /**
     * 获取数据范围信息
     */
    public static DataScopeInfo getDataScopeInfo() {
        return DATA_SCOPE_INFO.get();
    }

    /**
     * 设置数据范围信息
     */
    public static void setDataScopeInfo(DataScopeInfo dataScopeInfo) {
        DATA_SCOPE_INFO.set(dataScopeInfo);
    }

    /**
     * 设置数据范围信息
     */
    public static void setDataScopeInfo(Set<String> dataScopes, Long userId) {
        DataScopeInfo dataScopeInfo = new DataScopeInfo();
        dataScopeInfo.setDataScopes(dataScopes);
        dataScopeInfo.setUserId(userId);
        setDataScopeInfo(dataScopeInfo);
    }

    /**
     * 清除数据范围信息
     */
    public static void clearDataScopeInfo() {
        DATA_SCOPE_INFO.remove();
    }

    /**
     * 获取数据范围信息
     */
    public static DataScopeInfo getDataScopeInfoOrNew() {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .orElseGet(DataScopeInfo::new);
    }

    /**
     * 获取数据范围信息
     */
    public static DataScopeInfo getDataScopeInfoOrThrow() {
        return getDataScopeInfoOrThrow("未读取到租户信息");
    }

    /**
     * 获取数据范围信息
     */
    public static DataScopeInfo getDataScopeInfoOrThrow(String message) {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .orElseThrow(() -> CommonException.error(message));
    }

    /**
     * 获取数据范围信息
     */
    public static DataScopeInfo getDataScopeInfoOrThrow(IException exception) {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .orElseThrow(() -> CommonException.error(exception));
    }

    /**
     * 获取数据范围信息
     */
    public static DataScopeInfo getDataScopeInfoOrThrow(CommonException exception) {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .orElseThrow(() -> exception);
    }

    /**
     * 获取租户信息的某一信息
     */
    public static <T> T getFieldOrDefault(Function<DataScopeInfo, T> fieldFunction, T defaultValue) {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .map(fieldFunction)
                .orElse(defaultValue);
    }

    /**
     * 获取租户信息的某一信息
     */
    public static <T> T getFieldOrThrow(Function<DataScopeInfo, T> fieldFunction,
                                        Supplier<? extends CommonException> supplier) {
        DataScopeInfo dataScopeInfo = getDataScopeInfoOrThrow();
        return Optional
                .of(dataScopeInfo)
                .map(fieldFunction)
                .orElseThrow(supplier);
    }

    /**
     * 数据范围信息序列化
     */
    public static String getDataScopeInfoJson() {
        return JsonUtils.serializeObject(getDataScopeInfo(), "");
    }

    /**
     * 数据范围信息反序列化
     */
    public static void setDataScopeInfoJson(String dataScopeInfoJson) {
        DATA_SCOPE_INFO.set(JsonUtils.deserializeObject(dataScopeInfoJson, DataScopeInfo.class));
    }

}
