package com.bitprogress.service.captcha;

import com.aliyuncs.exceptions.ClientException;
import com.bitprogress.constant.BaseRedisKeyPrefix;
import com.bitprogress.constant.BaseRedisLockKeyPrefix;
import com.bitprogress.util.AliSmsService;
import com.bitprogress.util.RedisCacheUtils;
import com.bitprogress.util.RedisLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author wuwuwupx
 * @desc: 验证码服务
 */
@Service
@Slf4j
public class CaptchaService {

    @Autowired
    private AliSmsService aliSmsService;

    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Autowired
    private RedisLockUtils redisLockUtils;

    /**
     * 发送短信验证码
     * 避免重复发送短信，每次操作直接上锁一分钟，除非异常的情况都不解锁
     * 短信验证码的有效期为2分钟
     *
     * @param phone
     */
    public void sendSmsCaptcha(String phone) {
        String lock = BaseRedisLockKeyPrefix.sendSmsCaptchaLock(phone);
        String value = UUID.randomUUID().toString();
        boolean lockResult = redisLockUtils.lock(lock, value, 60);
        if (!lockResult) {
            return;
        }
        try {
            String code = aliSmsService.sendSms(phone);
            String smsCaptchaKey = BaseRedisKeyPrefix.smsCaptcha(phone);
            redisCacheUtils.setForValueTtl(smsCaptchaKey, code, 120);
        } catch (ClientException e) {
            log.error("smsCaptchaSend error ", e);
            redisLockUtils.unlock(lock, value);
        }
    }

}
