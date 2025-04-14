package com.bitprogress.securityroute.service.context;

import com.bitprogress.securityroute.entity.BaseUserInfo;

public interface UserInfoContextService<T extends BaseUserInfo> {

    /**
     * 获取用户信息
     *
     * @return 用户信息
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

}
