package com.bitprogress.securityspring.service.impl;

import com.bitprogress.securityspring.entity.AuthMsg;
import com.bitprogress.securityspring.entity.AuthResult;
import com.bitprogress.securityspring.exception.AuthException;
import com.bitprogress.securityspring.service.AuthenticationCacheService;
import com.bitprogress.securityspring.service.AuthenticationService;
import com.bitprogress.securityspring.service.JwtTokenService;
import com.bitprogress.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.util.Objects;

@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * Authorization认证开头是"token "
     */
    private static final String BEARER = "token ";

    private final JwtTokenService jwtTokenService;
    private final AuthenticationCacheService authenticationCacheService;

    /**
     * 创建授权信息
     *
     * @param userId  用户ID
     * @param authMsg 权限信息
     */
    @Override
    public <T extends AuthMsg> String createAuthentication(String userId, T authMsg) {
        String token = jwtTokenService.createToken(userId);
        authMsg.setToken(token);
        authenticationCacheService.cacheAuthentication(userId, authMsg);
        return token;
    }

    /**
     * 刷新权限信息
     *
     * @param userId  用户ID
     * @param authMsg 权限信息
     */
    @Override
    public <T extends AuthMsg> void refreshAuthentication(String userId, T authMsg) {
        authenticationCacheService.refreshCacheAuthentication(userId, authMsg);
    }

    /**
     * 从请求头中获取鉴权字符
     *
     * @param headers 请求头
     * @return 鉴权字符
     */
    @Override
    public String getAuthenticationByHeader(HttpHeaders headers) {
        return headers.getFirst(HttpHeaders.AUTHORIZATION);
    }

    /**
     * 检验是否有访问权限
     *
     * @param authentication 权限字符串
     * @param target         目标类
     * @return Result
     */
    @Override
    public <T extends AuthMsg> AuthResult<T> checkToken(String authentication, Class<T> target) {
        String token;
        // 如果请求未携带token信息, 直接权限拒绝
        if (StringUtils.isEmpty(authentication) || !authentication.startsWith(BEARER)
                || Objects.isNull(token = getToken(authentication))) {
            AuthResult<T> authResult = new AuthResult<>();
            authResult.setResult(false);
            authResult.setAuthException(AuthException.AUTH_TOKEN_EMPTY);
            return authResult;
        }

        AuthResult<T> auth = new AuthResult<>();
        String decodeToken = jwtTokenService.decodeToken(token);
        String userId;
        // 对token进行解析获取userId
        try {
            String[] split = decodeToken.split("\\.");
            userId = split[0];
            auth.setUserId(userId);
        } catch (Exception e) {
            auth.setResult(false);
            auth.setAuthException(AuthException.AUTH_TOKEN_WRONG);
            return auth;
        }
        // 通过解析token得到的userId获取用户的其他信息
        T authMsg = authenticationCacheService.getAuthentication(userId, target);
        if (Objects.isNull(authMsg)) {
            auth.setResult(false);
            auth.setAuthException(AuthException.AUTH_TOKEN_WRONG);
        }
        String redisToken = authMsg.getToken();
        boolean result = StringUtils.isNotEmpty(redisToken) && redisToken.equals(token);
        auth.setResult(result);
        if (!result) {
            auth.setAuthException(AuthException.AUTH_TOKEN_WRONG);
        }
        auth.setAuthMsg(authMsg);
        return auth;

    }

    /**
     * 获取 Authorization header 的 token 值
     *
     * @param authorization 需要处理的字符串
     * @return token
     */
    private String getToken(String authorization) {
        String[] authTokens = authorization.split(StringUtils.SPACE);
        if (authTokens.length < 2) {
            return null;
        }
        return authTokens[1];
    }

}
