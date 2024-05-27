package com.bitprogress.basecontext.context;

import com.bitprogress.basecontext.enums.DispatcherType;
import com.bitprogress.util.JsonUtils;

import java.util.Objects;

/**
 * 调度上下文
 * 用于识别线程的调度类型
 */
public class DispatcherContext {

    private static final ThreadLocal<DispatcherType> DISPATCHER_TYPE = new ThreadLocal<>();

    /**
     * 获取调度类型，默认为系统调度
     */
    public static DispatcherType getDispatcherType() {
        DispatcherType dispatcherType = DISPATCHER_TYPE.get();
        return Objects.isNull(dispatcherType) ? DispatcherType.SYSTEM_DISPATCH : dispatcherType;
    }

    public static void setDispatcherType(DispatcherType dispatcherType) {
        DISPATCHER_TYPE.set(dispatcherType);
    }

    /**
     * 清除线程调度信息
     */
    public static void clearDispatcherType() {
        DISPATCHER_TYPE.remove();
    }

    /**
     * 标记系统调度
     */
    public static void markSystemDispatch() {
        setDispatcherType(DispatcherType.SYSTEM_DISPATCH);
    }

    /**
     * 标记用户请求
     */
    public static void markUserRequest() {
        setDispatcherType(DispatcherType.USER_REQUEST);
    }

    /**
     * 标记匿名请求
     */
    public static void markAnonymousRequest() {
        setDispatcherType(DispatcherType.ANONYMOUS_REQUEST);
    }

    /**
     * 检查是否无状态调度，即是否非用户登录状态
     *
     * @return true：无状态调度，false：有状态调度
     */
    public static boolean isNoneStatusDispatch() {
        return DispatcherType.USER_REQUEST != getDispatcherType();
    }

    public static String getDispatcherTypeJson() {
        return JsonUtils.serializeObject(getDispatcherType());
    }

}
