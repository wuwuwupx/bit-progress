package com.bitprogress.auth.base;

/**
 * @author wpx
 * Created on 2021/1/26 11:20
 */
public class AuthResult<T extends AuthMsg> {

    private Boolean result;

    private String userId;

    private T authMsg;

    private AuthException authException;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public T getAuthMsg() {
        return authMsg;
    }

    public void setAuthMsg(T authMsg) {
        this.authMsg = authMsg;
    }

    public AuthException getAuthException() {
        return authException;
    }

    public void setAuthException(AuthException authException) {
        this.authException = authException;
    }
}
