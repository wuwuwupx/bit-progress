package com.bitprogress.model.pay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 * 结算信息
 */
public class SettleInfo {

    /**
     * 非必填
     * 是否指定分账
     */
    @JsonProperty("profit_sharing")
    private Boolean profitSharing;

    public Boolean getProfitSharing() {
        return profitSharing;
    }

    public SettleInfo setProfitSharing(Boolean profitSharing) {
        this.profitSharing = profitSharing;
        return this;
    }

}
