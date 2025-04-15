package com.bitprogress.systemcontext.service;

import com.bitprogress.basecontext.service.EnumContextService;
import com.bitprogress.basemodel.util.EnumUtils;
import com.bitprogress.systemcontext.enums.DispatcherType;

public interface DispatcherContextService extends EnumContextService<DispatcherType> {

    ThreadLocal<DispatcherType> DISPATCHER_TYPE = new ThreadLocal<>();

    /**
     * 获取调度类型，默认为系统调度
     *
     * @return 调度类型
     */
    static DispatcherType getDispatcherType() {
        DispatcherType dispatcherType = DISPATCHER_TYPE.get();
        return dispatcherType == null ? DispatcherType.SYSTEM_DISPATCH : dispatcherType;
    }

    /**
     * 设置调度类型
     *
     * @param dispatcherType 调度类型
     */
    static void setDispatcherType(DispatcherType dispatcherType) {
        DISPATCHER_TYPE.set(dispatcherType);
    }

    /**
     * 清除线程调度信息
     */
    static void clearDispatcherType() {
        DISPATCHER_TYPE.remove();
    }

    /**
     * 标记系统调度
     */
    static void markSystemDispatch() {
        setDispatcherType(DispatcherType.SYSTEM_DISPATCH);
    }

    /**
     * 标记用户请求
     */
    static void markUserRequest() {
        setDispatcherType(DispatcherType.USER_REQUEST);
    }

    /**
     * 标记匿名请求
     */
    static void markAnonymousRequest() {
        setDispatcherType(DispatcherType.ANONYMOUS_REQUEST);
    }

    /**
     * 检查是否无状态调度，即是否非用户登录状态
     *
     * @return true：无状态调度，false：有状态调度
     */
    static boolean isNoneStatusDispatch() {
        return DispatcherType.USER_REQUEST != getDispatcherType();
    }

    /**
     * 获取调度类型序列化信息
     */
    static String getDispatcherTypeJson() {
        return getDispatcherType().getValue().toString();
    }

    /**
     * 反序列化调度类型
     */
    static void setDispatcherTypeJson(String dispatcherTypeJson) {
        setDispatcherType(EnumUtils.getByValue(DispatcherType.class, Integer.parseInt(dispatcherTypeJson)));
    }


    /**
     * 获取上下文信息
     *
     * @return 上下文信息
     */
    @Override
    default DispatcherType getContextInfo() {
        return getDispatcherType();
    }

    /**
     * 设置上下文信息
     *
     * @param contextInfo 上下文信息
     */
    @Override
    default void setContextInfo(DispatcherType contextInfo) {
        setDispatcherType(contextInfo);
    }

    /**
     * 清除上下文信息
     */
    @Override
    default void clearContextInfo() {
        clearDispatcherType();
    }

    /**
     * 获取上下文信息json
     *
     * @return 上下文信息json
     */
    @Override
    default String getContextInfoJson() {
        return getDispatcherTypeJson();
    }

    /**
     * 设置上下文信息json
     *
     * @param contextInfoJson 上下文信息json
     */
    @Override
    default void setContextInfoJson(String contextInfoJson) {
        setDispatcherTypeJson(contextInfoJson);
    }

    /**
     * 获取上下文信息类型
     *
     * @return 上下文信息类型
     */
    @Override
    default Class<DispatcherType> getInfoClass() {
        return DispatcherType.class;
    }

}
