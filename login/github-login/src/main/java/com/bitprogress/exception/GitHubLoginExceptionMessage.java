package com.bitprogress.exception;

/**
 * @author wuwuwupx
 * GitHub登录模块错误信息
 */
public enum GitHubLoginExceptionMessage implements IExceptionMessage {

    /**
     * GitHub登录AccessToken获取异常
     */
    GITHUB_ACCESS_TOKEN_REQUEST_ERROR(5001, GitHubLoginErrorCodes.GITHUB_ACCESS_TOKEN_REQUEST_ERROR, GitHubLoginMessageCodes.GITHUB_ACCESS_TOKEN_REQUEST_ERROR),

    /**
     * GitHub登录获取用户信息异常
     */
    GITHUB_USER_REQUEST_ERROR(5002, GitHubLoginErrorCodes.GITHUB_USER_REQUEST_ERROR, GitHubLoginMessageCodes.GITHUB_USER_REQUEST_ERROR),

    ;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 错误码
     */
    private String error;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 获取异常状态码
     */
    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getError() {
        return null;
    }

    /**
     * 获取异常信息
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    GitHubLoginExceptionMessage(Integer code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }

}
