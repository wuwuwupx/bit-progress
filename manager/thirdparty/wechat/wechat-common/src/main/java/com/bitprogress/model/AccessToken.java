package com.bitprogress.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 *  微信接口调用凭证
 */
public class AccessToken extends WechatResult {

    /**
     * 微信接口调用凭证 access_token
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 微信接口调用凭证有效时间，单位秒
     */
    @JsonProperty("expires_in")
    private Long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
