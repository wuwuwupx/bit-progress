package com.bitprogress.ormcontext.service;

import com.bitprogress.ormmodel.info.user.BaseUserInfo;
import com.bitprogress.util.JsonUtils;

/**
 * 用户上下文信息
 *
 * @param <T> 用户上下文信息类型
 */
public interface UserContextService<T extends BaseUserInfo> {

    /**
     * 获取数据范围信息
     */
    T getUserInfo();

    /**
     * 设置用户信息
     *
     * @param userInfo 用户信息
     */
    void setUserInfo(T userInfo);

    /**
     * 清除用户信息
     */
    void clearUserInfo();

    /**
     * 数据范围信息序列化
     */
    default String getUserInfoJson() {
        return JsonUtils.serializeObject(getUserInfo(), "");
    }

    /**
     * 数据范围信息反序列化
     */
    default void setUserInfoJson(String dataScopeInfoJson) {
        setUserInfo(JsonUtils.deserializeObject(dataScopeInfoJson, getUserInfoClass()));
    }

    /**
     * 获取用户信息类型
     *
     * @return 用户信息类型
     */
    Class<T> getUserInfoClass();

}
