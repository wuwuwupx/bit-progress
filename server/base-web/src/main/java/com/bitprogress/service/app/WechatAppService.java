package com.bitprogress.service.app;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bitprogress.manager.redis.WechatAppRedisService;
import com.bitprogress.mapper.WechatAppMapper;
import com.bitprogress.entity.WechatApp;
import com.bitprogress.model.wechatapp.pojo.WechatAppRO;
import com.bitprogress.util.Assert;
import com.bitprogress.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.bitprogress.exception.BaseExceptionMessage.WECHATAPP_NOT_EXIST_EXCEPTION;

/**
* <p>
    * 微信应用信息 服务类
    * </p>
*
* @author wuwuwupx
* create on 2021-08-28
*/
@Service
@Slf4j
public class WechatAppService extends ServiceImpl<WechatAppMapper, WechatApp> {

    @Autowired
    private WechatAppRedisService wechatAppRedisService;

    /**
     * 获取微信应用信息
     *
     * @param appSign
     */
    public WechatAppRO getWechatAppByAppSign(String appSign) {
        WechatAppRO wechatApp = wechatAppRedisService.getWechatApp(appSign);
        return Objects.isNull(wechatApp) ? refreshWechatApp(appSign) : wechatApp;
    }

    /**
     * 从数据库查询wechatApp的信息刷新到redis
     *
     * @param appSign
     */
    public WechatAppRO refreshWechatApp(String appSign) {
        LambdaQueryChainWrapper<WechatApp> query = lambdaQuery().eq(WechatApp::getAppSign, appSign);
        WechatApp wechatApp = getOne(query);
        Assert.notNull(wechatApp, WECHATAPP_NOT_EXIST_EXCEPTION);
        WechatAppRO wechatAppRO = BeanUtils.copyNonNullProperties(wechatApp, WechatAppRO.class);
        wechatAppRedisService.refreshWechatApp(wechatAppRO);
        return wechatAppRO;
    }

}
