package com.bitprogress.basecontext.service;

/**
 * 上下文服务
 */
public interface ContextService<T> {

    /**
     * 获取上下文信息
     *
     * @return 上下文信息
     */
    T getContextInfo();

    /**
     * 设置上下文信息
     *
     * @param contextInfo 上下文信息
     */
    void setContextInfo(T contextInfo);

    /**
     * 清除上下文信息
     */
    void clearContextInfo();

    /**
     * 获取上下文信息json
     *
     * @return 上下文信息json
     */
    String getContextInfoJson();

    /**
     * 设置上下文信息json
     *
     * @param contextInfoJson 上下文信息json
     */
    void setContextInfoJson(String contextInfoJson);

    /**
     * 获取上下文信息类型
     *
     * @return 上下文信息类型
     */
    Class<T> getInfoClass();

}
