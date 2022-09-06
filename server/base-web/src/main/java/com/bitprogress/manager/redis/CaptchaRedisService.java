package com.bitprogress.manager.redis;

import com.bitprogress.constant.BaseRedisKeyPrefix;
import com.bitprogress.util.RedisCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuwuwupx
 */
@Service
public class CaptchaRedisService {

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    /**
     * 通过手机号码获取短信验证码
     *
     * @param phone
     */
    public String getSmsCaptcha(String phone) {
        String captchaKey = BaseRedisKeyPrefix.smsCaptcha(phone);
        return redisCacheUtils.getForValue(captchaKey);
    }

}
