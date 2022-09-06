package com.bitprogress.service.app;

import com.bitprogress.constant.BaseRedisKeyPrefix;
import com.bitprogress.constant.BaseRedisLockKeyPrefix;
import com.bitprogress.exception.BaseException;
import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionMessage;
import com.bitprogress.model.AccessToken;
import com.bitprogress.model.app.envm.AppTypeEnum;
import com.bitprogress.model.login.WechatPhoneDTO;
import com.bitprogress.processor.WechatProcessor;
import com.bitprogress.service.user.UserService;
import com.bitprogress.service.user.WechatAppletUserService;
import com.bitprogress.service.user.WechatOaUserService;
import com.bitprogress.util.Assert;
import com.bitprogress.util.RedisCacheUtils;
import com.bitprogress.util.RedisLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author wuwuwupx
 * @desc: 微信相关服务的manager层
 */
@Service
@Slf4j
public class WechatService {

    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Autowired
    private RedisLockUtils redisLockUtils;

    @Autowired
    private UserService userService;
    @Autowired
    private WechatAppletUserService wechatAppletUserService;
    @Autowired
    private WechatOaUserService wechatOaUserService;

    /**
     * 根据appSign获取accessToken
     *
     * @param appSign
     * @return accessToken
     */
    public String getAccessTokenByAppSign(String appSign) {
        String key = BaseRedisKeyPrefix.accessTokenKey(appSign);
        return redisCacheUtils.getForValue(key);
    }

    /**
     * 从微信端获取accessToken
     *
     * @param appSign
     */
    private String refreshAccessToken(String appSign, String appId, String appSecret) throws CommonException {
        String lock = BaseRedisLockKeyPrefix.wechatAccessTokenLock(appSign);
        String value = UUID.randomUUID().toString();
        boolean lockResult = redisLockUtils.lock(lock, value, 15);
        Assert.isTrue(lockResult, ExceptionMessage.ACQUIRE_LOCK_EXCEPTION);
        try {
            String key = BaseRedisKeyPrefix.accessTokenKey(appSign);
            AccessToken token = WechatProcessor.getAccessToken(appId, appSecret);
            String accessToken = token.getAccessToken();
            redisCacheUtils.setForValueTtl(key, accessToken, token.getExpiresIn());
            return accessToken;
        } catch (Exception e) {
            log.error("refreshWechatAccessToken error ", e);
            throw new BaseException(BaseExceptionMessage.REFRESH_ACCESSTOKEN_EXCEPTION);
        } finally {
            redisLockUtils.unlock(lock, value);
        }
    }

    /**
     * 更新用户的手机号码
     *
     * @param wechatPhoneDTO
     * @param userId
     */
    public void updatePhone(WechatPhoneDTO wechatPhoneDTO, Long userId) {
        AppTypeEnum appType = userService.getUserAppTypeById(userId);
        String encryptedData = wechatPhoneDTO.getEncryptedData();
        String iv = wechatPhoneDTO.getIv();
        switch (appType) {
            case WECHAT_APPLET: {
                wechatAppletUserService.updatePhone(encryptedData, iv, userId);
                break;
            }
            case WECHAT_OA: {
                wechatOaUserService.updatePhone(encryptedData, iv, userId);
                break;
            }
            default: {
                throw new BaseException(BaseExceptionMessage.NOT_WECHATUSER_EXCEPTION);
            }
        }
    }

}
