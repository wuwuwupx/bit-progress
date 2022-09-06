package com.bitprogress.service;

import com.bitprogress.auth.base.AuthMsg;
import com.bitprogress.model.login.LoginVO;
import com.bitprogress.auth.base.AuthResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wpx
 * Created on 2021/1/27 17:44
 */
@Service
public class AuthorizationService {

    @Autowired
    private AuthTokenService authTokenService;

    /**
     * 用户进行登录
     *
     * @param    userId
     * @return   String
     */
    public LoginVO login(String userId) {
        LoginVO loginVO = new LoginVO();
        String token = authTokenService.login(userId, new AuthMsg());
        loginVO.setUserId(Long.parseLong(userId));
        loginVO.setToken(token);
        return loginVO;
    }

    /**
     * 用户进行登出
     *
     * @param    userId
     */
    public void logout(String userId) {
        authTokenService.logout(userId);
    }

    /**
     * 从用户的token解析用户的userId
     *
     * @param token
     */
    public LoginVO getUserIdInToken(String token) {
        LoginVO loginVO = new LoginVO();
        String userId = authTokenService.getUserIdInToken(token);
        loginVO.setUserId(Long.parseLong(userId));
        loginVO.setToken(token);
        return loginVO;
    }

    /**
     * 加密token
     *
     * @param rawToken
     */
    public String encodeToken(String rawToken) {
        return authTokenService.encodeToken(rawToken);
    }

    /**
     * 解密token
     *
     * @param token
     */
    public String decodeToken(String token) {
        return authTokenService.decodeToken(token);
    }

    /**
     * 检验token是否有效
     *
     * @param token
     */
    public AuthResult<AuthMsg> checkToken(String token) {
        return authTokenService.checkToken(token, AuthMsg.class);
    }
}
