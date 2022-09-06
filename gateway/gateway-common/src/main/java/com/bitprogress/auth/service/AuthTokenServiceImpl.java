package com.bitprogress.auth.service;

import com.alibaba.fastjson.JSON;
import com.bitprogress.auth.base.AuthMsg;
import com.bitprogress.service.AuthTokenService;
import com.bitprogress.util.StringUtils;
import com.bitprogress.auth.base.AuthResult;
import com.bitprogress.auth.base.AuthProperties;
import com.bitprogress.auth.base.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.bitprogress.auth.base.AuthException.AUTH_TOKEN_WRONG;

/**
 * 采用shiro模式的token校验，而非继承了shiro
 * 相较于集成鉴权框架更加轻量，只进行了token的生成和校验，而不干涉登录的逻辑
 *
 * @author wpx
 **/
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final Logger logger = LoggerFactory.getLogger(AuthTokenServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private TokenUtils tokenUtils;

    private static final String BASE_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 登录
     *
     * @param userId  用户ID
     * @param authMsg 用户登录信息
     * @return 登录后的token
     */
    @Override
    public <T extends AuthMsg> String login(String userId, T authMsg) {
        return updateToken(userId, authMsg);
    }

    /**
     * 更新token
     *
     * @param userId  用户ID
     * @param authMsg 登录信息
     * @return 登录后的token
     */
    private <T extends AuthMsg> String updateToken(String userId, T authMsg) {
        String salt = randomString(5);
        String tokenPrefix = authProperties.getTokenPrefix();
        String tokenName = authProperties.getTokenName();
        int cacheDays = authProperties.getCacheDays();
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        String content = generateEncodeContent(userId, salt);
        String token = tokenUtils.encode(content);
        logger.info("setToken userId: [{}] = token:[{}]", userId, token);
        if (authMsg == null) {
            authMsg = (T) new AuthMsg();
        }
        authMsg.setToken(token);
        String value = JSON.toJSONString(authMsg);
        stringRedisTemplate.opsForValue().set(tokenPrefix + tokenName + userId, value, cacheDays, TimeUnit.DAYS);
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
            stringRedisTemplate.delete(tokenPrefix + tokenName + userId);
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
    public <T extends AuthMsg> AuthResult<T> checkToken(String token, Class<T> target) {
        AuthResult<T> auth = new AuthResult<>();
        String userIdTokenRole = this.tokenUtils.decode(token);
        String userId;
        try {
            String[] split = userIdTokenRole.split("\\.");
            userId = split[0];
            auth.setUserId(userId);
        } catch (Exception var7) {
            auth.setResult(false);
            auth.setAuthException(AUTH_TOKEN_WRONG);
            return auth;
        }
        String tokenPrefix = authProperties.getTokenPrefix();
        String tokenName = authProperties.getTokenName();
        String value = stringRedisTemplate.opsForValue().get(tokenPrefix + tokenName + userId);
        if (StringUtils.isEmpty(value)) {
            auth.setResult(false);
            auth.setAuthException(AUTH_TOKEN_WRONG);
            return auth;
        }
        T authMsg = JSON.parseObject(value, target);
        String redisToken = authMsg.getToken();
        auth.setAuthMsg(authMsg);
        boolean result = StringUtils.nonEmpty(redisToken) && token.equals(redisToken);
        auth.setResult(result);
        if (!result) {
            auth.setAuthException(AUTH_TOKEN_WRONG);
        }
        return auth;
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
        return tokenUtils.encode(rawToken);
    }

    /**
     * 解密token
     *
     * @param token 需要解析的token
     * @return 解析token后获得的字符串
     */
    @Override
    public String decodeToken(String token) {
        return tokenUtils.decode(token);
    }
}
