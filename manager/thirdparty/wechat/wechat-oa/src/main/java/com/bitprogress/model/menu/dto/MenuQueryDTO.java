package com.bitprogress.model.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 */
public class MenuQueryDTO {

    /**
     * user_id可以是粉丝的OpenID，也可以是粉丝的微信号
     */
    @JsonProperty("user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MenuQueryDTO{" +
                "userId='" + userId + '\'' +
                '}';
    }

}
