package com.bitprogress.context;

import com.bitprogress.type.DispatcherType;

import java.util.Objects;

/**
 * 调度上下文
 * 用于识别线程的调度类型
 */
public class DispatcherContext {

    private static final ThreadLocal<DispatcherType> DISPATCHER_TYPE = new ThreadLocal<>();

    public static void markUserRequest() {
        DISPATCHER_TYPE.set(DispatcherType.USER_REQUEST);
    }

    public static void markSystemSchedule() {
        DISPATCHER_TYPE.set(DispatcherType.SYSTEM_SCHEDULe);
    }

    /**
     * 获取调度类型，默认为系统调度
     */
    public static DispatcherType getDispatcherType() {
        DispatcherType dispatcherType = DISPATCHER_TYPE.get();
        return Objects.isNull(dispatcherType) ? DispatcherType.SYSTEM_SCHEDULe : dispatcherType;
    }

}
