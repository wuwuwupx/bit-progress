package com.bitprogress.securityspring.service;

import com.bitprogress.securityspring.entity.AuthMsg;
import com.bitprogress.securityspring.entity.AuthResult;
import org.springframework.http.HttpHeaders;

public interface AuthenticationService {

    /**
     * 创建授权信息
     *
     * @param userId  用户ID
     * @param authMsg 权限信息
     */
    <T extends AuthMsg> String createAuthentication(String userId, T authMsg);

    /**
     * 刷新权限信息
     *
     * @param userId  用户ID
     * @param authMsg 权限信息
     */
    <T extends AuthMsg> void refreshAuthentication(String userId, T authMsg);

    /**
     * 从请求头中获取鉴权字符
     *
     * @param headers 请求头
     * @return 鉴权字符
     */
    String getAuthenticationByHeader(HttpHeaders headers);

    /**
     * 检验是否有访问权限
     *
     * @param authentication 权限字符串
     * @return Result
     */
    <T extends AuthMsg> AuthResult<T> checkToken(String authentication, Class<T> target);

}
