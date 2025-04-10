package com.bitprogress.ormcontext.context;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionMessage;
import com.bitprogress.ormcontext.info.SingleTypeDataScopeInfo;
import com.bitprogress.util.JsonUtils;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class SingleTypeDataScopeContext {

    private static final ThreadLocal<SingleTypeDataScopeInfo> DATA_SCOPE_INFO = new ThreadLocal<>();

    /**
     * 获取数据范围信息
     */
    public static SingleTypeDataScopeInfo getDataScopeInfo() {
        return DATA_SCOPE_INFO.get();
    }

    /**
     * 设置数据范围信息
     */
    public static void setDataScopeInfo(SingleTypeDataScopeInfo singleTypeDataScopeInfo) {
        DATA_SCOPE_INFO.set(singleTypeDataScopeInfo);
    }

    /**
     * 设置数据范围信息
     */
    public static void setDataScopeInfo(Set<String> dataScopes, Long userId) {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = new SingleTypeDataScopeInfo();
        singleTypeDataScopeInfo.setManagedDataScopes(dataScopes);
        singleTypeDataScopeInfo.setUserId(userId);
        setDataScopeInfo(singleTypeDataScopeInfo);
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
    public static SingleTypeDataScopeInfo getDataScopeInfoOrNew() {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .orElseGet(SingleTypeDataScopeInfo::new);
    }

    /**
     * 获取数据范围信息
     */
    public static SingleTypeDataScopeInfo getDataScopeInfoOrThrow() {
        return getDataScopeInfoOrThrow("未读取到租户信息");
    }

    /**
     * 获取数据范围信息
     */
    public static SingleTypeDataScopeInfo getDataScopeInfoOrThrow(String message) {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .orElseThrow(() -> CommonException.error(message));
    }

    /**
     * 获取数据范围信息
     */
    public static SingleTypeDataScopeInfo getDataScopeInfoOrThrow(ExceptionMessage exception) {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .orElseThrow(() -> CommonException.error(exception));
    }

    /**
     * 获取数据范围信息
     */
    public static SingleTypeDataScopeInfo getDataScopeInfoOrThrow(CommonException exception) {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .orElseThrow(() -> exception);
    }

    /**
     * 获取租户信息的某一信息
     */
    public static <T> T getFieldOrDefault(Function<SingleTypeDataScopeInfo, T> fieldFunction, T defaultValue) {
        return Optional
                .ofNullable(DATA_SCOPE_INFO.get())
                .map(fieldFunction)
                .orElse(defaultValue);
    }

    /**
     * 获取租户信息的某一信息
     */
    public static <T> T getFieldOrThrow(Function<SingleTypeDataScopeInfo, T> fieldFunction,
                                        Supplier<? extends CommonException> supplier) {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = getDataScopeInfoOrThrow();
        return Optional
                .of(singleTypeDataScopeInfo)
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
        DATA_SCOPE_INFO.set(JsonUtils.deserializeObject(dataScopeInfoJson, SingleTypeDataScopeInfo.class));
    }

}
