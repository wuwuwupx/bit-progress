package com.bitprogress.basecontext.service;

import com.bitprogress.basecontext.enums.DeserializeType;
import com.bitprogress.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

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
    default String getContextInfoJson() {
        return JsonUtils.serializeObject(getContextInfo());
    }

    /**
     * 设置上下文信息json
     *
     * @param contextInfoJson 上下文信息json
     */
    default void setContextInfoJson(String contextInfoJson) {
        if (DeserializeType.TYPE_REFERENCE.equals(getDeserializeType())) {
            setContextInfo(JsonUtils.deserializeObject(contextInfoJson, getInfoTypeReference()));
        } else {
            setContextInfo(JsonUtils.deserializeObject(contextInfoJson, getInfoClass()));
        }
    }

    /**
     * 获取上下文信息反序列化类型
     *
     * @return 上下文信息反序列化类型
     */
    default DeserializeType getDeserializeType() {
        return DeserializeType.CLASS;
    }

    /**
     * 获取上下文信息类型引用
     *
     * @return 上下文信息类型引用
     */
    default TypeReference<T> getInfoTypeReference() {
        return new TypeReference<>() {
            @Override
            public Class<T> getType() {
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
