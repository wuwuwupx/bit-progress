package com.bitprogress.model.transactionquery;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 * 单品列表信息
 */
public class TransactionQueryGoodsDetail {

    /**
     * 必返回
     * 商品编码
     * string[1,32]
     */
    @JsonProperty("goods_id")
    private String goodsId;

    /**
     * 必返回
     * 商品数量，用户购买的数量
     */
    private Integer quantity;

    /**
     * 必返回
     * 商品单价，单位为分
     */
    @JsonProperty("unit_price")
    private Integer unitPrice;

    /**
     * 必返回
     * 商品优惠金额（傻逼，单位是谜）
     */
    @JsonProperty("discount_amount")
    private Integer discountAmount;

    /**
     * 非必返回
     * 商品备注信息
     */
    @JsonProperty("goods_remark")
    private Integer goodsRemark;

    public String getGoodsId() {
        return goodsId;
    }

    public TransactionQueryGoodsDetail setGoodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public TransactionQueryGoodsDetail setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public TransactionQueryGoodsDetail setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public TransactionQueryGoodsDetail setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public Integer getGoodsRemark() {
        return goodsRemark;
    }

    public TransactionQueryGoodsDetail setGoodsRemark(Integer goodsRemark) {
        this.goodsRemark = goodsRemark;
        return this;
    }

}
