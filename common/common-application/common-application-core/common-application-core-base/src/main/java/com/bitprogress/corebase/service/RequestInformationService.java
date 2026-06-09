package com.bitprogress.corebase.service;

import com.bitprogress.request.enums.RequestSource;
import com.bitprogress.request.enums.RequestType;
import com.bitprogress.usercontext.entity.UserInfo;

/**
 * 请求信息服务
 */
public interface RequestInformationService {

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    String getTenantId();

    /**
     * 获取请求来源
     *
     * @return 请求来源
     */
    RequestSource getRequestSource();

    /**
     * 获取请求类型
     *
     * @return 请求类型
     */
    RequestType getRequestType();

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    UserInfo getUserInfo();

}
