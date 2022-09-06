package com.bitprogress.service;

import com.bitprogress.constant.WechatLoginUrl;
import com.bitprogress.model.JsCode2SessionResult;
import com.bitprogress.model.DecryptResult;
import com.bitprogress.util.*;

import java.util.HashMap;
import java.util.Map;

import static com.bitprogress.constant.WechatConstant.*;
import static com.bitprogress.constant.WechatLoginConstants.*;

/**
 * @author wuwuwupx
 *  微信登录服务
 */
public class WechatLoginService {

    /**
     * 微信登录
     *
     * @param appId     微信下发的appId
     * @param appSecret 微信下发的secret
     * @param jsCode    临时登录凭证 code
     * @return JsCode2SessionResult
     */
    public static JsCode2SessionResult jsCode2Session(String appId, String appSecret, String jsCode) {
        Map<String, String> params = new HashMap<>(8);
        params.put(APP_ID, appId);
        params.put(APP_SECRET, appSecret);
        params.put(JS_CODE, jsCode);
        params.put(GRANT_TYPE, AUTHORIZATION_CODE);
        String result = WechatRequestUtils.doGet(WechatLoginUrl.JS_CODE_TO_SESSION_URL, params);
        return WechatResultUtils.wechatResultCheck(result, JsCode2SessionResult.class);
    }

    /**
     * 获取微信用户加密的手机信息
     *
     * @param encryptedData 加密后的数据
     * @param iv            AES加密的位偏移值
     * @param sessionKey    code2session中获取的sessionKey
     * @return 解密后的用户手机数据
     */
    public static DecryptResult encryptedWechatData(String encryptedData, String iv, String sessionKey) {
        String data = WechatDecryptUtils.decrypt(encryptedData, sessionKey, iv);
        return StringUtils.isEmpty(data) ? null : JsonUtils.deserializeObject(data, DecryptResult.class);
    }

}
