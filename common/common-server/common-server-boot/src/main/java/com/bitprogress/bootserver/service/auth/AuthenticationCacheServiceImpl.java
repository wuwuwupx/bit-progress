package com.bitprogress.bootserver.service.auth;

import com.bitprogress.bootserver.propertity.AuthProperties;
import com.bitprogress.securityspring.entity.AuthMsg;
import com.bitprogress.securityspring.service.AuthenticationCacheService;
import com.bitprogress.service.RedissonService;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.StringUtils;
import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class AuthenticationCacheServiceImpl implements AuthenticationCacheService {

    private final RedissonService redissonService;
    private final AuthProperties authProperties;

    /**
     * 获取缓存的授权信息
     *
     * @param userId 用户ID
     * @param target 目标类型
     */
    @Override
    public <T extends AuthMsg> T getAuthentication(String userId, Class<T> target) {
        String value = redissonService.getFromValue(userId);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return JsonUtils.deserializeObject(value, target);
    }

    /**
     * 缓存授权信息
     *
     * @param userId  用户ID
     * @param authMsg 权限信息
     */
    @Override
    public <T extends AuthMsg> void cacheAuthentication(String userId, T authMsg) {
        String cacheKey = generateTokenKey(userId);
        String value = JsonUtils.serializeObject(authMsg);
        redissonService.setExpireFromValue(cacheKey, value, authProperties.getCacheTimes(), TimeUnit.SECONDS);
    }

    /**
     * 生成存储token的key
     *
     * @param userId 需要处理的userId
     * @return key
     */
    private String generateTokenKey(String userId) {
        String tokenPrefix = authProperties.getTokenPrefix();
        String tokenName = authProperties.getTokenName();
        return tokenPrefix + tokenName + userId;
    }

    /**
     * 刷新缓存授权信息
     *
     * @param userId  用户ID
     * @param authMsg 权限信息
     */
    @Override
    public <T extends AuthMsg> void refreshCacheAuthentication(String userId, T authMsg) {
        cacheAuthentication(userId, authMsg);
    }

}
