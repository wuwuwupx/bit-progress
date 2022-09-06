package com.bitprogress.model.kfsession.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 *  客服会话
 */
public class KfSessionDTO {

    /**
     * 客服账号
     */
    @JsonProperty("kf_account")
    private String kfAccount;

    /**
     * 用户openId
     */
    @JsonProperty("openid")
    private String openId;

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
