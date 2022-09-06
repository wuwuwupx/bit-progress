package com.bitprogress.model.transactionquery;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author wuwuwupx
 * 优惠功能，享受优惠时返回该字段
 */
public class PromotionDetail {

    /**
     * 必返回
     * 券ID
     * string[1,32]
     */
    @JsonProperty("coupon_id")
    private String couponId;

    /**
     * 非必返回
     * 优惠名称
     * string[1,64]
     */
    private String name;

    /**
     * 非必返回
     * 优惠范围：GLOBAL：全场代金券，SINGLE：单品优惠
     * string[1,32]
     */
    private String scope;

    /**
     * 非必返回
     * 优惠类型：CASH：充值，NOCASH：预充值
     * string[1,32]
     */
    private String type;

    /**
     * 必返回
     * 优惠券面额（傻逼微信，单位是谜）
     * string[1,32]
     */
    private Integer amount;

    /**
     * 非必返回
     * 活动ID
     * string[1,32]
     */
    @JsonProperty("stock_id")
    private String stockId;

    /**
     * 非必返回
     * 微信出资，单位为分
     */
    @JsonProperty("wechatpay_contribute")
    private Integer wechatpayContribute;

    /**
     * 非必返回
     * 商户出资，单位为分
     */
    @JsonProperty("merchant_contribute")
    private Integer merchantContribute;

    /**
     * 非必返回
     * 其他出资，单位为分
     */
    @JsonProperty("other_contribute")
    private Integer otherContribute;

    /**
     * 非必返回
     * 优惠币种，CNY：人民币，境内商户号仅支持人民币
     * string[1,16]
     */
    private String currency;

    /**
     * 非必返回
     * 单品列表
     */
    @JsonProperty("goods_detail")
    private List<TransactionQueryGoodsDetail> goodsDetail;

    public String getCouponId() {
        return couponId;
    }

    public PromotionDetail setCouponId(String couponId) {
        this.couponId = couponId;
        return this;
    }

    public String getName() {
        return name;
    }

    public PromotionDetail setName(String name) {
        this.name = name;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public PromotionDetail setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getType() {
        return type;
    }

    public PromotionDetail setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public PromotionDetail setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public String getStockId() {
        return stockId;
    }

    public PromotionDetail setStockId(String stockId) {
        this.stockId = stockId;
        return this;
    }

    public Integer getWechatpayContribute() {
        return wechatpayContribute;
    }

    public PromotionDetail setWechatpayContribute(Integer wechatpayContribute) {
        this.wechatpayContribute = wechatpayContribute;
        return this;
    }

    public Integer getMerchantContribute() {
        return merchantContribute;
    }

    public PromotionDetail setMerchantContribute(Integer merchantContribute) {
        this.merchantContribute = merchantContribute;
        return this;
    }

    public Integer getOtherContribute() {
        return otherContribute;
    }

    public PromotionDetail setOtherContribute(Integer otherContribute) {
        this.otherContribute = otherContribute;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public PromotionDetail setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public List<TransactionQueryGoodsDetail> getGoodsDetail() {
        return goodsDetail;
    }

    public PromotionDetail setGoodsDetail(List<TransactionQueryGoodsDetail> goodsDetail) {
        this.goodsDetail = goodsDetail;
        return this;
    }

}
