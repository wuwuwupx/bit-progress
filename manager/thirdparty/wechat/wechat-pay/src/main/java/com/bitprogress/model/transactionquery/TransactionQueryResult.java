package com.bitprogress.model.transactionquery;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.pay.Payer;
import com.bitprogress.model.pay.SceneInfo;

import java.util.List;

/**
 * @author wuwuwupx
 * JSAPI订单查询结果
 */
public class TransactionQueryResult {

    /**
     * 必返回
     * 直连商户申请的公众号或移动应用appid
     * string[1,32]
     */
    private String appid;

    /**
     * 必返回
     * 直连商户的商户号，由微信支付生成并下发
     * string[1,32]
     */
    private String mchid;

    /**
     * 必返回
     * 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
     * string[6,32]
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 非必返回
     * 微信支付订单号
     * 微信支付系统生成的订单号
     * string[1,32]
     */
    @JsonProperty("transaction_id")
    private String transactionId;

    /**
     * 非必返回
     * 交易类型，枚举值：JSAPI：公众号支付，NATIVE：扫码支付，APP：APP支付，MICROPAY：付款码支付，MWEB：H5支付，FACEPAY：刷脸支付
     * string[1,16]
     */
    @JsonProperty("trade_type")
    private String tradeType;

    /**
     * 必返回
     * 交易状态，枚举值：SUCCESS：支付成功，REFUND：转入退款，NOTPAY：未支付，CLOSED：已关闭，REVOKED：已撤销（付款码支付）
     * USERPAYING：用户支付中（付款码支付），PAYERROR：支付失败(其他原因，如银行返回失败)，ACCEPT：已接收，等待扣款
     * string[1,32]
     */
    @JsonProperty("trade_state")
    private String tradeState;

    /**
     * 必返回
     * 交易状态描述，示例值：支付成功
     * string[1,256]
     */
    @JsonProperty("trade_state_desc")
    private String tradeStateDesc;

    /**
     * 非必返回
     * 付款银行
     * string[1,16]
     */
    @JsonProperty("bank_type")
    private String bankType;

    /**
     * 非必返回
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     * string[1,128]
     */
    private String attach;

    /**
     * 非必返回
     * 支付完成时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日
     * T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * string[1,64]
     */
    @JsonProperty("success_time")
    private String successTime;

    /**
     * 必返回
     * 支付者信息
     */
    private Payer payer;

    /**
     * 非必返回
     * 订单金额信息，当支付成功时返回该字段
     */
    private TransactionQueryAmount amount;

    /**
     * 非必返回
     * 支付场景描述，这里只返回终端设备号
     */
    @JsonProperty("scene_info")
    private SceneInfo sceneInfo;

    /**
     * 非必返回
     * 优惠功能，享受优惠时返回该字段
     */
    @JsonProperty("promotion_detail")
    private List<PromotionDetail> promotionDetail;

    public String getAppid() {
        return appid;
    }

    public TransactionQueryResult setAppid(String appid) {
        this.appid = appid;
        return this;
    }

    public String getMchid() {
        return mchid;
    }

    public TransactionQueryResult setMchid(String mchid) {
        this.mchid = mchid;
        return this;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public TransactionQueryResult setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionQueryResult setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getTradeType() {
        return tradeType;
    }

    public TransactionQueryResult setTradeType(String tradeType) {
        this.tradeType = tradeType;
        return this;
    }

    public String getTradeState() {
        return tradeState;
    }

    public TransactionQueryResult setTradeState(String tradeState) {
        this.tradeState = tradeState;
        return this;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public TransactionQueryResult setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
        return this;
    }

    public String getBankType() {
        return bankType;
    }

    public TransactionQueryResult setBankType(String bankType) {
        this.bankType = bankType;
        return this;
    }

    public String getAttach() {
        return attach;
    }

    public TransactionQueryResult setAttach(String attach) {
        this.attach = attach;
        return this;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public TransactionQueryResult setSuccessTime(String successTime) {
        this.successTime = successTime;
        return this;
    }

    public Payer getPayer() {
        return payer;
    }

    public TransactionQueryResult setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    public TransactionQueryAmount getAmount() {
        return amount;
    }

    public TransactionQueryResult setAmount(TransactionQueryAmount amount) {
        this.amount = amount;
        return this;
    }

    public SceneInfo getSceneInfo() {
        return sceneInfo;
    }

    public TransactionQueryResult setSceneInfo(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
        return this;
    }

    public List<PromotionDetail> getPromotionDetail() {
        return promotionDetail;
    }

    public TransactionQueryResult setPromotionDetail(List<PromotionDetail> promotionDetail) {
        this.promotionDetail = promotionDetail;
        return this;
    }

}
