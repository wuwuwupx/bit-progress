package com.bitprogress.model.pay;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author wuwuwupx
 * 优惠功能
 */
public class Detail {

    /**
     * 非必填
     * 订单原价
     * 1、商户侧一张小票订单可能被分多次支付，订单原价用于记录整张小票的交易金额。
     * 2、当订单原价与支付金额不相等，则不享受优惠。
     * 3、该字段主要用于防止同一张小票分多次支付，以享受多次优惠的情况，正常支付订单不必上传此参数
     * int
     */
    @JsonProperty("cost_price")
    private Integer costPrice;

    /**
     * 非必填
     * 商品小票ID
     * string[1,32]
     */
    @JsonProperty("invoice_id")
    private String invoiceId;

    /**
     * 非必填
     * 单品列表信息
     * 条目个数限制：【1，6000】
     */
    @JsonProperty("goods_detail")
    private List<GoodsDetail> goodsDetail;

    public Integer getCostPrice() {
        return costPrice;
    }

    public Detail setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
        return this;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public Detail setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public List<GoodsDetail> getGoodsDetail() {
        return goodsDetail;
    }

    public Detail setGoodsDetail(List<GoodsDetail> goodsDetail) {
        this.goodsDetail = goodsDetail;
        return this;
    }

}
