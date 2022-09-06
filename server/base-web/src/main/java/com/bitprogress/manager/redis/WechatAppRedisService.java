package com.bitprogress.manager.redis;

import com.bitprogress.constant.BaseRedisKeyPrefix;
import com.bitprogress.model.wechatapp.pojo.WechatAppRO;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.RedisCacheUtils;
import com.bitprogress.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wuwuwupx
 * @desc: 微信应用redis服务
 */
@Component
public class WechatAppRedisService {

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    /**
     * 刷新redis中wechatApp信息
     *
     * @param wechatAppRO
     */
    public void refreshWechatApp(WechatAppRO wechatAppRO) {
        String key = BaseRedisKeyPrefix.wechatAppKey();
        String value = JsonUtils.serializeObject(wechatAppRO);
        redisCacheUtils.putForHash(key, wechatAppRO.getAppSign(), value);
    }

    /**
     * 刷新redis中wechatApp信息
     *
     * @param appSign
     */
    public WechatAppRO getWechatApp(String appSign) {
        String key = BaseRedisKeyPrefix.wechatAppKey();
        String value = redisCacheUtils.getForHash(key, appSign);
        return StringUtils.isEmpty(value) ? null : JsonUtils.deserializeObject(value, WechatAppRO.class);
    }

}
