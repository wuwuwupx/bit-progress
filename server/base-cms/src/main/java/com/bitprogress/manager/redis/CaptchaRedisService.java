package com.bitprogress.manager.redis;

import com.bitprogress.constant.BaseRedisKeyPrefix;
import com.bitprogress.util.RedisCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author wuwuwupx
 */
@Service
public class CaptchaRedisService {

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    /**
     * 获取验证码信息
     *
     * @param uuid
     */
    public String getCapText(String uuid) {
        String key = BaseRedisKeyPrefix.picCaptcha(uuid);
        return redisCacheUtils.getForValue(key);
    }

    /**
     * 保存验证码信息
     *
     * @param uuid
     * @param capText
     */
    public void setCapText(String uuid, String capText) {
        String key = BaseRedisKeyPrefix.picCaptcha(uuid);
        redisCacheUtils.setForValueTtl(key, capText, 5L, TimeUnit.MINUTES);
    }

    /**
     * 删除验证码信息
     *
     * @param uuid
     */
    public void deleteCaptcha(String uuid) {
        String key = BaseRedisKeyPrefix.picCaptcha(uuid);
        redisCacheUtils.delete(key);
    }

}
