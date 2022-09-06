package com.bitprogress.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 *  微信登录信息结果
 */
public class JsCode2SessionResult extends WechatResult {

    /**
     * openId
     */
    @JsonProperty("openid")
    private String openId;

    /**
     * unionId(符合条件才会下发)
     */
    @JsonProperty("unionid")
    private String unionId;

    /**
     * sessionKey
     */
    @JsonProperty("session_key")
    private String sessionKey;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}
