package com.bitprogress.securityspring.service;

public interface JwtTokenService {

    /**
     * 创建token，只使用 userId 即可，多余信息应该另外存储
     *
     * @param userId 用户ID
     * @return token
     */
    String createToken(String userId);

    /**
     * 获取用户ID，从token中解析用户ID
     *
     * @param token token
     * @return 用户ID
     */
    String getUserId(String token);

    /**
     * 加密token，与 {@link #decodeToken(String)} 对应，主要用于自定义token
     *
     * @param rawToken 需要加密成token的字符串
     * @return 加密成token的字符串
     */
    String encodeToken(String rawToken);

    /**
     * 解析token
     *
     * @param token 需要解析的token
     * @return token解析后的字符串
     */
    String decodeToken(String token);

}
