package com.bitprogress.model.jsapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.pay.*;

/**
 * @author wuwuwupx
 * create on 2021/7/19 0:00
 * 官方文档 https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_1.shtml
 * 微信支付 JSAPI支付下单
 */
public class JsApiUnifiedOrder {

    /**
     * 必填
     * 公众账号ID
     */
    private String appid;

    /**
     * 必填
     * 商户号，直连商户的商户号，由微信支付生成并下发
     */
    private String mchid;

    /**
     * 必填
     * 商品描述
     * string[1,127]
     */
    private String description;

    /**
     * 必填
     * 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
     * string[6,32]
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 非必填
     * 订单失效时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE
     * YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒
     * TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * string[1,64]
     * LocalDateTime.toString()
     */
    @JsonProperty("time_expire")
    private String timeExpire;

    /**
     * 非必填
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     */
    private String attach;

    /**
     * 必填
     * 通知URL必须为直接可访问的URL，不允许携带查询串，要求必须为https地址。
     * 即支付结果的回调地址
     * string[1,256]
     */
    @JsonProperty("notify_url")
    private String notifyUrl;

    /**
     * 非必填
     * 订单优惠标记
     * string[1,32]
     */
    private String goodsTag;

    /**
     * 必填
     * 订单金额信息
     */
    private Amount amount;

    /**
     * 必填
     * 支付人信息
     */
    private Payer payer;

    /**
     * 非必填
     * 优惠功能
     */
    private Detail detail;

    /**
     * 非必填
     * 支付场景描述
     */
    @JsonProperty("scene_info")
    private SceneInfo sceneInfo;

    /**
     * 结算信息
     */
    @JsonProperty("settle_info")
    private SettleInfo settleInfo;

    public String getAppid() {
        return appid;
    }

    public JsApiUnifiedOrder setAppid(String appid) {
        this.appid = appid;
        return this;
    }

    public String getMchid() {
        return mchid;
    }

    public JsApiUnifiedOrder setMchid(String mchid) {
        this.mchid = mchid;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public JsApiUnifiedOrder setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public JsApiUnifiedOrder setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public JsApiUnifiedOrder setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public JsApiUnifiedOrder setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public JsApiUnifiedOrder setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public JsApiUnifiedOrder setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
        return this;
    }

    public Amount getAmount() {
        return amount;
    }

    public JsApiUnifiedOrder setAmount(Amount amount) {
        this.amount = amount;
        return this;
    }

    public Payer getPayer() {
        return payer;
    }

    public JsApiUnifiedOrder setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    public Detail getDetail() {
        return detail;
    }

    public JsApiUnifiedOrder setDetail(Detail detail) {
        this.detail = detail;
        return this;
    }

    public SceneInfo getSceneInfo() {
        return sceneInfo;
    }

    public JsApiUnifiedOrder setSceneInfo(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
        return this;
    }

    public SettleInfo getSettleInfo() {
        return settleInfo;
    }

    public JsApiUnifiedOrder setSettleInfo(SettleInfo settleInfo) {
        this.settleInfo = settleInfo;
        return this;
    }

}
