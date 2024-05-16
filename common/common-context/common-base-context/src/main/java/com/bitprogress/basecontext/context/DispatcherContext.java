package com.bitprogress.basecontext.context;

import com.bitprogress.basecontext.enums.DispatcherType;

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
        return Objects.isNull(dispatcherType) ? DispatcherType.SCHEDULE_DISPATCH : dispatcherType;
    }

    public static void setDispatcherType(DispatcherType dispatcherType) {
        DISPATCHER_TYPE.set(dispatcherType);
    }

    /**
     * 标记系统调度
     */
    public static void markSystemDispatch() {
        setDispatcherType(DispatcherType.SCHEDULE_DISPATCH);
    }

    /**
     * 标记用户请求
     */
    public static void markUserRequest() {
        setDispatcherType(DispatcherType.USER_REQUEST);
    }

    /**
     * 检查是否系统调度
     *
     * @return true：系统调度，false：非系统调度
     */
    public static boolean isSystemSchedule() {
        return DispatcherType.SCHEDULE_DISPATCH == getDispatcherType();
    }

}
