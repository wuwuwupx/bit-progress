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
     * 标记用户请求
     */
    public static void markUserRequest() {
        DISPATCHER_TYPE.set(DispatcherType.USER_REQUEST);
    }

    /**
     * 标记系统调度
     */
    public static void markSystemSchedule() {
        DISPATCHER_TYPE.set(DispatcherType.SYSTEM_SCHEDULE);
    }

    /**
     * 获取调度类型，默认为系统调度
     */
    public static DispatcherType getDispatcherType() {
        DispatcherType dispatcherType = DISPATCHER_TYPE.get();
        return Objects.isNull(dispatcherType) ? DispatcherType.SYSTEM_SCHEDULE : dispatcherType;
    }

    /**
     * 检查是否系统调度
     *
     * @return true：系统调度，false：非系统调度
     */
    public static boolean isSystemSchedule() {
        return DispatcherType.SYSTEM_SCHEDULE == getDispatcherType();
    }

}
