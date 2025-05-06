package com.bitprogress.securityspring.service;

import com.bitprogress.securityspring.entity.AuthMsg;

public interface AuthenticationCacheService {

    /**
     * 获取缓存的授权信息
     *
     * @param userId 用户ID
     */
    <T extends AuthMsg> T getAuthentication(String userId, Class<T> target);

    /**
     * 缓存授权信息
     *
     * @param userId  用户ID
     * @param authMsg 权限信息
     */
    <T extends AuthMsg> void cacheAuthentication(String userId, T authMsg);

    /**
     * 刷新缓存授权信息
     *
     * @param userId  用户ID
     * @param authMsg 权限信息
     */
    <T extends AuthMsg> void refreshCacheAuthentication(String userId, T authMsg);

}
