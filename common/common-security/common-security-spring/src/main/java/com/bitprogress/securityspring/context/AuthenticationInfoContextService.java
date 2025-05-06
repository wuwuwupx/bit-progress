package com.bitprogress.securityspring.context;

import com.bitprogress.basecontext.service.ContextService;
import com.bitprogress.securityspring.entity.AuthenticationInfo;

public class AuthenticationInfoContextService implements ContextService<AuthenticationInfo> {

    private static final ThreadLocal<AuthenticationInfo> AUTHENTICATION_INFO = new ThreadLocal<>();

    /**
     * 获取上下文信息
     *
     * @return 上下文信息
     */
    @Override
    public AuthenticationInfo getContextInfo() {
        return AUTHENTICATION_INFO.get();
    }

    /**
     * 设置上下文信息
     *
     * @param contextInfo 上下文信息
     */
    @Override
    public void setContextInfo(AuthenticationInfo contextInfo) {
        AUTHENTICATION_INFO.set(contextInfo);
    }

    /**
     * 清除上下文信息
     */
    @Override
    public void clearContextInfo() {
        AUTHENTICATION_INFO.remove();
    }

    /**
     * 获取上下文信息类型
     *
     * @return 上下文信息类型
     */
    @Override
    public Class<AuthenticationInfo> getInfoClass() {
        return AuthenticationInfo.class;
    }

}
