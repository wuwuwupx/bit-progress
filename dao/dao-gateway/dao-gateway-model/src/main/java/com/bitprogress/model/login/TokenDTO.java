package com.bitprogress.model.login;

import java.io.Serializable;

/**
 * @author wuwuwupx
 * 检查token的DTO
 */
public class TokenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" + "token=" + token + "}";
    }

}
