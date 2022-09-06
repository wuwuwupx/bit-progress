package com.bitprogress.service.login;

import com.bitprogress.constant.StringConstants;
import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionMessage;
import com.bitprogress.auth.AuthFeignService;
import com.bitprogress.manager.redis.CaptchaRedisService;
import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.JsCode2SessionResult;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatapp.envm.WechatAppTypeEnum;
import com.bitprogress.model.wechatapp.pojo.WechatAppRO;
import com.bitprogress.model.login.*;
import com.bitprogress.service.WechatLoginService;
import com.bitprogress.service.app.WechatAppService;
import com.bitprogress.service.user.PhoneUserService;
import com.bitprogress.service.user.WechatAppletUserService;
import com.bitprogress.service.user.WechatOaUserService;
import com.bitprogress.service.user.WechatUserService;
import com.bitprogress.util.Assert;
import com.bitprogress.util.ResultUtils;
import com.bitprogress.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuwuwupx
 * @desc: 微信登录服务
 */
@Service
public class LoginService {

    @Autowired
    private WechatAppService wechatAppService;

    @Autowired
    private WechatAppletUserService wechatAppletUserService;
    @Autowired
    private WechatOaUserService wechatOaUserService;
    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private PhoneUserService phoneUserService;

    @Autowired
    private AuthFeignService authFeignService;

    @Autowired
    private CaptchaRedisService captchaRedisService;

    /**
     * 短信验证码登录
     *
     * @param smsCaptchaLoginDTO
     */
    public LoginVO smsCaptchaLogin(SmsCaptchaLoginDTO smsCaptchaLoginDTO) {
        String phone = smsCaptchaLoginDTO.getPhone();
        String smsCaptcha = smsCaptchaLoginDTO.getSmsCaptcha();
        String captcha = captchaRedisService.getSmsCaptcha(phone);
        // 验证码正确
        Assert.isTrue(StringUtils.equals(smsCaptcha, captcha), ExceptionMessage.SMS_CAPTCHA_WRONG_EXCEPTION);
        Long userId = phoneUserService.updateUser(phone);
        return login(userId, null);
    }

    /**
     * 检查token是否合法
     *
     * @param tokenDTO
     */
    public BooleanVO tokenCheck(TokenDTO tokenDTO) {
        ResultVO<BooleanVO> result = authFeignService.checkToken(tokenDTO);
        return ResultUtils.checkResultVO(result);
    }

    /**
     * 微信登录
     *
     * @param wechatLoginDTO
     * @return token信息
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public LoginVO wechatLogin(WechatLoginDTO wechatLoginDTO) {
        String appSign = wechatLoginDTO.getAppSign();
        WechatAppRO wechatApp = wechatAppService.getWechatAppByAppSign(appSign);
        Long appId = wechatApp.getAppId();
        // 解析jsCode
        String jsCode = wechatLoginDTO.getJsCode();
        String wxAppId = wechatApp.getWxAppId();
        String appSecret = wechatApp.getAppSecret();
        JsCode2SessionResult result = WechatLoginService.jsCode2Session(jsCode, wxAppId, appSecret);
        String openId = result.getOpenId();
        String unionId = result.getUnionId();
        String sessionKey = result.getSessionKey();
        Assert.isNotEmpty(unionId, BaseExceptionMessage.UNION_ID_EMPTY_EXCEPTION);
        Assert.isNotEmpty(openId, BaseExceptionMessage.OPENID_ID_EMPTY_EXCEPTION);

        Boolean authorized = wechatLoginDTO.getAuthorized();
        if (!authorized) {
            wechatLoginDTO.setNickname(StringConstants.EMPTY)
                    .setGender(0)
                    .setCountry(StringConstants.EMPTY)
                    .setProvince(StringConstants.EMPTY)
                    .setCity(StringConstants.EMPTY);
        }

        WechatAppTypeEnum wechatAppType = wechatLoginDTO.getWechatAppType();
        Long wechatUserId = wechatUserService.updateUser(unionId, wechatLoginDTO);
        Long userId;
        switch (wechatAppType) {
            case APPLET: {
                userId = wechatAppletUserService.updateUser(wechatLoginDTO, openId, unionId, wechatUserId, appId,
                        appSign, sessionKey);
                break;
            }
            case OA: {
                userId = wechatOaUserService.updateUser(wechatLoginDTO, openId, unionId, wechatUserId, appSign, appId,
                        sessionKey);
                break;
            }
            default: {
                throw new CommonException(ExceptionMessage.TYPE_NOT_APPOINT);
            }
        }
        return login(userId, null);
    }

    /**
     * 进行登录获取token
     *
     * @param userId
     * @param role
     * @return  登录结果（包括登录获取的token）
     */
    private LoginVO login(Long userId, Integer role) {
        // 调用gateway登录接口获取token
        LoginWebDTO loginDTO = new LoginWebDTO();
        loginDTO.setUserId(String.valueOf(userId));
        ResultVO<LoginVO> loginResult = authFeignService.login(loginDTO);
        return ResultUtils.checkResultVO(loginResult);
    }

}
