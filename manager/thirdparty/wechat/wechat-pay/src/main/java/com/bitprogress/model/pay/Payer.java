package com.bitprogress.model.pay;

/**
 * @author wuwuwupx
 * create on 2021/7/19 0:22
 * 支付人
 */
public class Payer {

    /**
     * 必填
     * 用户在直连商户appid下的唯一标识。 下单前需获取到用户的Openid
     * string[1,128]
     */
    private String openid;

    public String getOpenid() {
        return openid;
    }

    public Payer setOpenid(String openid) {
        this.openid = openid;
        return this;
    }

    public Payer(String openid) {
        this.openid = openid;
    }

}
