package com.bitprogress.model;

import com.bitprogress.constant.ResultConstants;
import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionEnum;
import com.bitprogress.response.RequestResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.bitprogress.constant.ResultConstants.SUCCESS_MESSAGE;

/**
 * 结果集响应
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class ResultResponse<T> extends RequestResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 请求返回的数据
     */
    private T data;

    public ResultResponse(Integer code, String msg) {
        this(code, msg, null);
    }

    public ResultResponse(Integer code, String msg, T data) {
        super(code, msg);
        this.data = data;
    }

    /**
     * 请求成功，无返回信息
     */
    public static ResultResponse<Void> SUCCESS() {
        return new ResultResponse<>(ResultConstants.SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    /**
     * 请求成功，返回特定data
     *
     * @param data 返回数据
     */
    public static <T> ResultResponse<T> success(T data) {
        return success(ResultConstants.SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    /**
     * 请求成功，返回特定message和data
     *
     * @param message 返回信息
     * @param data    返回数据
     */
    public static <T> ResultResponse<T> success(String message, T data) {
        return success(ResultConstants.SUCCESS_CODE, message, data);
    }

    /**
     * 请求成功，返回特定code、message和data
     *
     * @param code    状态码
     * @param message 请求信息
     * @param data    请求数据
     */
    public static <T> ResultResponse<T> success(Integer code, String message, T data) {
        return new ResultResponse<>(code, message, data);
    }

    /**
     * 请求失败，无返回信息
     */
    public static ResultResponse<Void> FAIL() {
        return fail(ResultConstants.FAIL_CODE, ResultConstants.FAIL_MESSAGE);
    }

    /**
     * 请求失败，返回特定code
     *
     * @param code 状态码
     */
    public static ResultResponse<Void> fail(Integer code) {
        return fail(code, ResultConstants.FAIL_MESSAGE);
    }

    /**
     * 请求失败，返回特定code
     *
     * @param message 请求信息
     */
    public static ResultResponse<Void> fail(String message) {
        return fail(ResultConstants.FAIL_CODE, message);
    }

    /**
     * 请求失败，返回特定code, message
     *
     * @param code    状态码
     * @param message 请求信息
     */
    public static ResultResponse<Void> fail(Integer code, String message) {
        return new ResultResponse<>(code, message);
    }

    /**
     * 抛出异常
     *
     * @param exceptionEnum 请求异常
     */
    public static ResultResponse<Void> fail(ExceptionEnum exceptionEnum) {
        return fail(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    /**
     * 抛出异常
     *
     * @param exception 请求异常
     */
    public static ResultResponse<Void> fail(CommonException exception) {
        return fail(exception.getCode(), exception.getMessage());
    }

}
