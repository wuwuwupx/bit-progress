package com.bitprogress.service;

import com.bitprogress.util.JsonUtils;
import com.bitprogress.constant.WechatPayConstants;
import com.bitprogress.constant.WechatPayUrl;
import com.bitprogress.util.WechatRequestUtils;

import java.util.HashMap;
import java.util.Map;

import static com.bitprogress.constant.WechatPayConstants.MCH_ID;

/**
 * @author wuwuwupx
 * 微信支付订单关闭服务
 */
public class WechatPayCloseService {

    /**
     * 关闭订单
     *
     * @param mchId
     * @param outTradeNo
     */
    public void closeOrder(String mchId, String outTradeNo) {
        String closeUrl = WechatPayUrl.ORDER_CLOSE_URL;
        String url = closeUrl.replace(WechatPayConstants.OUT_TRADE_NO_PLACEHOLDER, outTradeNo);
        Map<String, String> params = new HashMap<>(4);
        params.put(MCH_ID, mchId);
        WechatRequestUtils.doPost(url, JsonUtils.serializeObject(params));
    }

}
