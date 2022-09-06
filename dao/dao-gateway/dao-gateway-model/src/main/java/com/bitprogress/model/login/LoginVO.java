package com.bitprogress.model.login;

import java.io.Serializable;

/**
 * @author wuwuwupx
 * 登录VO
 */
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录后获取的token
     */
    private String token;

    /**
     * 是否新用户
     */
    private Boolean newUser;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }

    @Override
    public String toString() {
        return "{" + "userId=" + userId + ", token=" + token + "newUser=" + newUser + "}";
    }

}
