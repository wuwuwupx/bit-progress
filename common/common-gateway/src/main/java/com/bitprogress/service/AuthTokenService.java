package com.bitprogress.service;

import com.bitprogress.auth.base.AuthInfo;
import com.bitprogress.auth.base.AuthResult;

public interface AuthTokenService {

    /**
     * 登录
     *
     * @param userId  用户ID
     * @param authMsg 用户登录信息
     * @return 登录后的token
     */
    <T extends AuthInfo> String login(String userId, T authMsg);

    /**
     * 登出
     *
     * @param userId 用户ID
     */
    void logout(String userId);

    /**
     * 校验token
     *
     * @param token  需要检验的token
     * @param target 检验token后返回的AuthResult类型
     * @return token检验结果
     */
    <T extends AuthInfo> AuthResult<T> checkToken(String token, Class<T> target);

    /**
     * 从token中解析出userID
     *
     * @param token 需要解析的token
     * @return 解析token后获得的UserId
     */
    String getUserIdInToken(String token);

    /**
     * 加密token
     *
     * @param rawToken 需要加密的字符串
     * @return 加密后的token字符串
     */
    String encodeToken(String rawToken);

    /**
     * 解密token
     *
     * @param token 需要解析的token
     * @return 解析token后获得的字符串
     */
    String decodeToken(String token);

}
