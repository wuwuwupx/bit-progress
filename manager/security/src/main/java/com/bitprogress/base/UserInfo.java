package com.bitprogress.base;

import java.util.Map;

/**
 * @author wpx
 */
public class UserInfo {

    private Long userId;

    private Map<String, String> params;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public UserInfo() {
    }

}
