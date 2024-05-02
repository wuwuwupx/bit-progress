package com.bitprogress.exception;

/**
 * 异常接口
 */
public interface IException {

    /**
     * 获取异常状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取异常信息
     *
     * @return 异常信息
     */
    String getMessage();

}
