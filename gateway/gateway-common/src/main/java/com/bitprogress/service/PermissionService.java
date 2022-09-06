package com.bitprogress.service;

import com.bitprogress.auth.base.AuthMsg;
import com.bitprogress.auth.base.AuthResult;

/**
 * @author wpx
 * 权限匹配服务
 */
public interface PermissionService {

    /**
     * 检验接口权限
     * 通过 method+url 获取接口的权限信息
     * 只有需要校验权限才实现此接口
     *
     * @param authResult 鉴权结果
     * @param method     方法
     * @param url        接口url
     */
    default void authorizePermission(AuthResult<AuthMsg> authResult, String method, String url) {
    }

}
