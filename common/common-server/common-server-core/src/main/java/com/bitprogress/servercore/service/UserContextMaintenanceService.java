package com.bitprogress.servercore.service;

import com.bitprogress.usercontext.entity.UserInfo;

/**
 * 用户上下文维护服务
 * 用于维护各类从用户登录信息中获取的用于组件功能的上下文
 */
public interface UserContextMaintenanceService {

    /**
     * 设置用户上下文
     *
     * @param userInfo 用户信息
     */
    void setContextByUserInfo(UserInfo userInfo);

    /**
     * 清除用户上下文
     */
    void clearContext();

}
