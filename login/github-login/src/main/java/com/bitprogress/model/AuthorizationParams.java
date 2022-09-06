package com.bitprogress.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 */
public  class AuthorizationParams {

    /**
     * GitHub申请的client_id
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 重定向获取的code
     */
    private String code;

    /**
     * redirect_uri
     */
    @JsonProperty("redirect_uri")
    private String redirectUri;

    /**
     * client_secret
     */
    @JsonProperty("client_secret")
    private String clientSecret;

}