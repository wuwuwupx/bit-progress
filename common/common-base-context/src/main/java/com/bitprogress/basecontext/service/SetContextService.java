package com.bitprogress.basecontext.service;

import com.bitprogress.basecontext.enums.DeserializeType;
import com.bitprogress.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * 上下文服务
 */
public interface SetContextService<T> {

    /**
     * 获取上下文信息
     *
     * @return 上下文信息
     */
    Set<T> getContextInfo();

    /**
     * 设置上下文信息
     *
     * @param contextInfo 上下文信息
     */
    void setContextInfo(Set<T> contextInfo);

    /**
     * 清除上下文信息
     */
    void clearContextInfo();

    /**
     * 获取上下文信息json
     *
     * @return 上下文信息json
     */
    default String getContextInfoJson() {
        return JsonUtils.serializeObject(getContextInfo());
    }

    /**
     * 设置上下文信息json
     *
     * @param contextInfoJson 上下文信息json
     */
    default void setContextInfoJson(String contextInfoJson) {
        setContextInfo(JsonUtils.deserializeObject(contextInfoJson, getInfoTypeReference()));
    }

    /**
     * 获取上下文信息类型引用
     *
     * @return 上下文信息类型引用
     */
    default TypeReference<Set<T>> getInfoTypeReference() {
        return new TypeReference<>() {
            @Override
            public Type getType() {
                return getInfoClass();
            }
        };
    }

    /**
     * 获取上下文信息类型
     *
     * @return 上下文信息类型
     */
    Class<T> getInfoClass();

}
