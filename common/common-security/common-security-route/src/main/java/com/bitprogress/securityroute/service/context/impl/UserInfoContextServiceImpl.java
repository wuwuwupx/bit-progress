package com.bitprogress.securityroute.service.context.impl;

import com.bitprogress.securityroute.entity.UserAuthorisationInfo;
import com.bitprogress.securityroute.service.context.UserAuthorisationContextService;

public class UserInfoContextServiceImpl implements UserAuthorisationContextService {

    private static final ThreadLocal<UserAuthorisationInfo> USER_INFO = new ThreadLocal<>();

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Override
    public UserAuthorisationInfo getUserInfo() {
        return USER_INFO.get();
    }

    /**
     * 设置用户信息
     *
     * @param userInfo 用户信息
     */
    @Override
    public void setUserInfo(UserAuthorisationInfo userInfo) {
        USER_INFO.set(userInfo);
    }

    /**
     * 清除用户信息
     */
    @Override
    public void clearUserInfo() {
        USER_INFO.remove();
    }

}
