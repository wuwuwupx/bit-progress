package com.bitprogress.processor;

import com.bitprogress.constant.WechatUrl;
import com.bitprogress.model.AccessToken;
import com.bitprogress.util.WechatRequestUtils;
import com.bitprogress.util.WechatResultUtils;

import java.util.HashMap;
import java.util.Map;

import static com.bitprogress.constant.WechatConstant.*;

/**
 * @author wuwuwupx
 *  微信应用处理器
 */
public class WechatProcessor {

    /**
     * 获取微信应用接口调用凭证
     *
     * @param appId
     * @param appSecret
     */
    public static AccessToken getAccessToken(String appId, String appSecret) {
        Map<String, String> params = new HashMap<>(8);
        params.put(GRANT_TYPE, ACCESS_TOKEN_CLIENT_CREDENTIAL);
        params.put(APP_ID, appId);
        params.put(APP_SECRET, appSecret);
        String result = WechatRequestUtils.doGet(WechatUrl.ACCESS_TOKEN_URL, params);
        return WechatResultUtils.wechatResultCheck(result, AccessToken.class);
    }

}
