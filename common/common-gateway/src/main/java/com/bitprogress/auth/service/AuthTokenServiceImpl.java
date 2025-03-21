package com.bitprogress.auth.service;

import com.bitprogress.auth.base.AuthInfo;
import com.bitprogress.auth.base.AuthProperties;
import com.bitprogress.auth.base.AuthResult;
import com.bitprogress.auth.base.TokenUtils;
import com.bitprogress.service.AuthTokenService;
import com.bitprogress.service.StringRedisService;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.bitprogress.auth.base.AuthException.AUTH_TOKEN_WRONG;

/**
 * 采用shiro模式的token校验，而非继承了shiro
 * 相较于集成鉴权框架更加轻量，只进行了token的生成和校验，而不干涉登录的逻辑
 */
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final Logger logger = LoggerFactory.getLogger(AuthTokenServiceImpl.class);

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private AuthProperties authProperties;

    private static final String BASE_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 登录
     *
     * @param userId   用户ID
     * @param authInfo 用户登录信息
     * @return 登录后的token
     */
    @Override
    public <T extends AuthInfo> String login(String userId, T authInfo) {
        return updateToken(userId, authInfo);
    }

    /**
     * 更新token
     *
     * @param userId   用户ID
     * @param authInfo 登录信息
     * @return 登录后的token
     */
    private <T extends AuthInfo> String updateToken(String userId, T authInfo) {
        String salt = randomString(5);
        String tokenPrefix = authProperties.getTokenPrefix();
        String tokenName = authProperties.getTokenName();
        Long cacheDays = authProperties.getCacheDays();
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        String content = generateEncodeContent(userId, salt);
        String token = TokenUtils.encode(content);
        logger.info("setToken userId: [{}] = token:[{}]", userId, token);
        if (authInfo == null) {
            authInfo = (T) new AuthInfo();
        }
        authInfo.setToken(token);
        String value = JsonUtils.serializeObject(authInfo);
        stringRedisService.setForValueTtl(tokenPrefix + tokenName + userId, value, cacheDays, TimeUnit.DAYS);
        return token;
    }

    /**
     * 生成需要加密的content
     */
    private String generateEncodeContent(String userId, String salt) {
        return userId + "." + salt;
    }

    /**
     * 登出
     *
     * @param userId 用户ID
     */
    @Override
    public void logout(String userId) {
        if (!authProperties.isMultiLogin()) {
            String tokenPrefix = authProperties.getTokenPrefix();
            String tokenName = authProperties.getTokenName();
            stringRedisService.delete(tokenPrefix + tokenName + userId);
        }
    }

    /**
     * 校验token
     *
     * @param token  需要检验的token
     * @param target 检验token后返回的AuthResult类型
     * @return token检验结果
     */
    @Override
    public <T extends AuthInfo> AuthResult<T> checkToken(String token, Class<T> target) {
        AuthResult<T> authResult = new AuthResult<>();
        // 解密token
        String userIdTokenRole = TokenUtils.decode(token);

        // 读取token信息
        String userId;
        try {
            String[] split = userIdTokenRole.split("\\.");
            userId = split[0];
        } catch (Exception var7) {
            authResult.setResult(false);
            authResult.setAuthException(AUTH_TOKEN_WRONG);
            return authResult;
        }

        // 获取用户信息
        String tokenPrefix = authProperties.getTokenPrefix();
        String tokenName = authProperties.getTokenName();
        String value = stringRedisService.getForValue(tokenPrefix + tokenName + userId);
        if (StringUtils.isEmpty(value)) {
            authResult.setResult(false);
            authResult.setAuthException(AUTH_TOKEN_WRONG);
            return authResult;
        }
        T userInfo = JsonUtils.deserializeObject(value, target);
        String redisToken = userInfo.getToken();
        authResult.setUserInfo(userInfo);
        boolean result = StringUtils.isNotEmpty(redisToken) && token.equals(redisToken);
        authResult.setResult(result);
        if (!result) {
            authResult.setAuthException(AUTH_TOKEN_WRONG);
        }
        return authResult;
    }

    /**
     * 生成随机数
     *
     * @param length 生成的字符串长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        if (StringUtils.isEmpty(BASE_STRING)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(length);
            if (length < 1) {
                length = 1;
            }

            int baseLength = BASE_STRING.length();

            for (int i = 0; i < length; ++i) {
                int number = ThreadLocalRandom.current().nextInt(baseLength);
                sb.append(BASE_STRING.charAt(number));
            }

            return sb.toString();
        }
    }

    /**
     * 从token中解析出userID
     *
     * @param token 需要解析的token
     * @return 解析token后获得的UserId
     */
    @Override
    public String getUserIdInToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String userIdTokenRole = decodeToken(token);
        String userId;
        try {
            String[] split = userIdTokenRole.split("\\.");
            userId = split[0];
        } catch (Exception var7) {
            logger.error("token decode error", var7);
            return null;
        }
        return userId;
    }

    /**
     * 加密token
     *
     * @param rawToken 需要加密的字符串
     * @return 加密后的token字符串
     */
    @Override
    public String encodeToken(String rawToken) {
        return TokenUtils.encode(rawToken);
    }

    /**
     * 解密token
     *
     * @param token 需要解析的token
     * @return 解析token后获得的字符串
     */
    @Override
    public String decodeToken(String token) {
        return TokenUtils.decode(token);
    }
}
