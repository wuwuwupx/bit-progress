package com.bitprogress.model.transactionquery;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 * 订单查询金额信息
 */
public class TransactionQueryAmount {

    /**
     * 非必返回
     * 订单总金额，单位为分
     */
    private Integer total;

    /**
     * 非必返回
     * 用户支付金额，单位为分
     */
    @JsonProperty("payer_total")
    private Integer payerTotal;

    /**
     * 非必返回
     * 货币类型：CNY：人民币，境内商户号仅支持人民币
     */
    private Integer currency;

    /**
     * 非必返回
     * 用户支付币种
     */
    @JsonProperty("payer_currency")
    private Integer payerCurrency;

    public Integer getTotal() {
        return total;
    }

    public TransactionQueryAmount setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public Integer getPayerTotal() {
        return payerTotal;
    }

    public TransactionQueryAmount setPayerTotal(Integer payerTotal) {
        this.payerTotal = payerTotal;
        return this;
    }

    public Integer getCurrency() {
        return currency;
    }

    public TransactionQueryAmount setCurrency(Integer currency) {
        this.currency = currency;
        return this;
    }

    public Integer getPayerCurrency() {
        return payerCurrency;
    }

    public TransactionQueryAmount setPayerCurrency(Integer payerCurrency) {
        this.payerCurrency = payerCurrency;
        return this;
    }

}
