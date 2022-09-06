package com.bitprogress.model.login;

import java.io.Serializable;

/**
 * @author wuwuwupx
 * 退出登录DTO
 */
public class LogoutDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LogoutDTO(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" + "userId=" + userId + "}";
    }

}
