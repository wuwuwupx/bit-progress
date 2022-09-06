package com.bitprogress.service.app;

import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.mapper.AppMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.bitprogress.entity.App;

import com.bitprogress.util.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bitprogress.constant.AppConstants.PHONE_APP;

/**
 * <p>
 * 应用信息 服务类
 * </p>
 *
 * @author wuwuwupx
 */
@Service
@Slf4j
public class AppService extends ServiceImpl<AppMapper, App> {

    /**
     * 获取手机登录用户所属应用
     */
    public App getPhoneApp() {
        return getAppByAppSign(PHONE_APP);
    }

    /**
     * 根据应用标识获取应用信息
     *
     * @param appSign
     */
    public App getAppByAppSign(String appSign) {
        App app = getOne(lambdaQuery().eq(App::getAppSign, appSign));
        Assert.notNull(app, BaseExceptionMessage.APP_NOT_EXIST_EXCEPTION);
        return app;
    }

}
