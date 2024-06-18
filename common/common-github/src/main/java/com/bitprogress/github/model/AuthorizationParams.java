package com.bitprogress.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 鉴权参数
 */
@Data
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