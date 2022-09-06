package com.bitprogress.model.jsapi;

/**
 * @author wuwuwupx
 * create on 2021/7/19 1:08
 * JsApi 发起支付
 */
public class JsApiPayOrder {

    /**
     * 必填
     * 商户申请的公众号对应的appid，由微信支付生成，可在公众号后台查看
     * string[1,32]
     */
    private String appId;

    /**
     * 必填
     * 当前的时间戳
     * 部分系统取到的值为毫秒级，需要转换成秒(10位数字)(傻逼微信，简直令人窒息的操作)
     * string[1,32]
     */
    private String timeStamp;

    /**
     * 必填
     * 随机字符串，不长于32位
     * string[1,32]
     */
    private String nonceStr;

    /**
     * 必填
     * 原字段package（微信真的令人窒息，字段名取关键字）
     * JSAPI下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
     * string[1,128]
     */
    private String packaged;

    /**
     * 必填
     * 签名类型，默认为RSA，仅支持RSA
     * string[1,32]
     */
    private String signType;

    /**
     * 必填
     * 签名，使用字段appId、timeStamp、nonceStr、package计算得出的签名值
     */
    private String paySign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackaged() {
        return packaged;
    }

    public void setPackaged(String packaged) {
        this.packaged = packaged;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

}
