package com.bitprogress.model.pay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 * 单品列表
 * 单品列表信息
 * 对于json结构体参数，内层参数的必填属性仅在结构体有传参的情况下才生效，即如果json结构体都不传参，则内层参数必定全部都不传参。
 * 条目个数限制：【1，6000】
 */
public class GoodsDetail {

    /**
     * 必填
     * 由半角的大小写字母、数字、中划线、下划线中的一种或几种组成
     * string[1,32]
     */
    @JsonProperty("merchant_goods_id")
    private String merchantGoodsId;

    /**
     * 非必填
     * 微信侧商品编码
     * 微信支付定义的统一商品编号（没有可不传）
     * string[1,32]
     */
    @JsonProperty("wechatpay_goods_id")
    private String wechatpayGoodsId;

    /**
     * 非必填
     * 商品的实际名称
     * string[1,256]
     */
    @JsonProperty("goods_name")
    private String goodsName;

    /**
     * 必填
     * 商品数量
     * 用户购买的数量
     */
    private Integer quantity;

    /**
     * 必填
     * 商品单价，单位为分
     */
    @JsonProperty("unit_price")
    private Integer unitPrice;

    public String getMerchantGoodsId() {
        return merchantGoodsId;
    }

    public GoodsDetail setMerchantGoodsId(String merchantGoodsId) {
        this.merchantGoodsId = merchantGoodsId;
        return this;
    }

    public String getWechatpayGoodsId() {
        return wechatpayGoodsId;
    }

    public GoodsDetail setWechatpayGoodsId(String wechatpayGoodsId) {
        this.wechatpayGoodsId = wechatpayGoodsId;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public GoodsDetail setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public GoodsDetail setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public GoodsDetail setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

}
