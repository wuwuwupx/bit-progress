package com.bitprogress.auth.service;

import com.bitprogress.auth.base.AuthMsg;
import com.bitprogress.service.AuthService;
import com.bitprogress.service.AuthTokenService;
import com.bitprogress.util.StringUtils;
import com.bitprogress.auth.base.AuthException;
import com.bitprogress.auth.base.AuthResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author wuwuwupx
 * 授权鉴权服务
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthTokenService authTokenService;

    /**
     * Authorization认证开头是"token "
     */
    private static final String BEARER = "token ";

    /**
     * Authentication的值分割后的长度
     */
    private static final int AUTHENTICATION_SPIT_LENGTH = 2;

    /**
     * token的下标
     */
    private static final int TOKEN_INDEX = 1;

    /**
     * 获取header中的权限字符串
     *
     * @param headers 请求头
     * @return 权限字符串
     */
    @Override
    public String getAuthentication(HttpHeaders headers) {
        return headers.getFirst(HttpHeaders.AUTHORIZATION);
    }

    /**
     * 检查token是否正确
     *
     * @param authentication 包含token的字符串
     * @return Result
     */
    @Override
    public <T extends AuthMsg> AuthResult<T> checkToken(String authentication, Class<T> target) {
        String token;
        // 如果请求未正确携带token信息, 直接权限拒绝
        if (StringUtils.isEmpty(authentication) || !authentication.startsWith(BEARER)
                || Objects.isNull(token = getToken(authentication))) {
            AuthResult<T> authResult = new AuthResult<>();
            authResult.setResult(false);
            authResult.setAuthException(AuthException.AUTH_TOKEN_EMPTY);
            return authResult;
        }
        return authTokenService.checkToken(token, target);
    }

    /**
     * 获取 Authorization header 的 token 值
     *
     * @param authorizationHeader 请求头
     */
    private String getToken(String authorizationHeader) {
        String[] authTokens = authorizationHeader.split(StringUtils.SPACE);
        if (authTokens.length < AUTHENTICATION_SPIT_LENGTH) {
            return null;
        }
        return authTokens[TOKEN_INDEX];
    }

}
