package com.bitprogress.auth.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author wuwuwupx
 * 授权信息
 */
public class AuthMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的token
     */
    private String token;

    /**
     * 一些补充参数
     */
    private Map<String, String> params;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getParams() {
        return Objects.isNull(params) ? new HashMap<>(2) : params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public AuthMsg() {
    }

}
