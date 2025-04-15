package com.bitprogress.usercontext.service;

import com.bitprogress.basecontext.service.InfoContextService;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.util.JsonUtils;

public interface UserInfoContextService extends InfoContextService<UserInfo> {

    /**
     * 用户信息
     */
    ThreadLocal<UserInfo> USER_INFO = new ThreadLocal<>();

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    static UserInfo getUserInfo() {
        return USER_INFO.get();
    }

    /**
     * 设置用户信息
     *
     * @param userInfo 用户信息
     */
    static void setUserInfo(UserInfo userInfo) {
        USER_INFO.set(userInfo);
    }

    /**
     * 清除用户信息
     */
    static void clearUserInfo() {
        USER_INFO.remove();
    }

    /**
     * 获取上下文信息json
     *
     * @return 上下文信息json
     */
    static String getUserInfoJson() {
        return JsonUtils.serializeObject(getUserInfo());
    }

    /**
     * 设置上下文信息json
     *
     * @param contextInfoJson 上下文信息json
     */
    static void setUserInfoJson(String contextInfoJson) {
        setUserInfo(JsonUtils.deserializeObject(contextInfoJson, UserInfo.class));
    }

    /**
     * 获取上下文信息
     *
     * @return 上下文信息
     */
    @Override
    default UserInfo getContextInfo() {
        return getUserInfo();
    }

    /**
     * 设置上下文信息
     *
     * @param contextInfo 上下文信息
     */
    @Override
    default void setContextInfo(UserInfo contextInfo) {
        setUserInfo(contextInfo);
    }

    /**
     * 清除上下文信息
     */
    @Override
    default void clearContextInfo() {
        clearUserInfo();
    }

    /**
     * 获取上下文信息类型
     *
     * @return 上下文信息类型
     */
    @Override
    default Class<UserInfo> getInfoClass() {
        return UserInfo.class;
    }

}
