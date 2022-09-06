package com.bitprogress.model.apppay;

/**
 * @author wuwuwupx
 * create on 2021/7/19 1:08
 * APP支付 发起支付
 */
public class AppPayOrder {

    /**
     * 必填
     * 微信开放平台审核通过的移动应用appid
     * string[1,32]
     */
    private String appid;

    /**
     * 必填
     * 请填写商户号mchid对应的值
     * string[1,32]
     */
    private String partnerid;

    /**
     * 必填
     * 微信返回的支付交易会话id
     * string[1,64]
     */
    private String prepayid;

    /**
     * 必填
     * 实际字段名称是 package
     * 暂填写固定值Sign=WXPay
     * string[1,128]
     */
    private String packaged;

    /**
     * 必填
     * 随机字符串，不长于32位。推荐随机数生成算法
     * string[1,32]
     */
    private String noncestr;

    /**
     * 必填
     * 时间戳
     * string[1,10]
     */
    private String timestamp;

    /**
     * 必填
     * 签名，使用字段appId、timeStamp、nonceStr、prepayid计算得出的签名值
     * string[1,256]
     */
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackaged() {
        return packaged;
    }

    public void setPackaged(String packaged) {
        this.packaged = packaged;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
