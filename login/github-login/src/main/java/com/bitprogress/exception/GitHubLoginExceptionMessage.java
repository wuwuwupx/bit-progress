package com.bitprogress.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuwuwupx
 * GitHub登录模块错误信息
 */
@Getter
@AllArgsConstructor
public enum GitHubLoginExceptionMessage implements IException {

    /**
     * GitHub登录AccessToken获取异常
     */
    GITHUB_ACCESS_TOKEN_REQUEST_ERROR(5001, GitHubLoginMessageCodes.GITHUB_ACCESS_TOKEN_REQUEST_ERROR),

    /**
     * GitHub登录获取用户信息异常
     */
    GITHUB_USER_REQUEST_ERROR(5002, GitHubLoginMessageCodes.GITHUB_USER_REQUEST_ERROR),

    ;

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String message;

}
