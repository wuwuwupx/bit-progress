package com.bitprogress.response.model;

import com.bitprogress.basemodel.response.RequestResponse;
import com.bitprogress.exception.CommonException;
import com.bitprogress.response.exception.CommonExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

import static com.bitprogress.exception.constant.ResultConstants.*;

/**
 * 结果集响应
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class ResultResponse<T> extends RequestResponse {

    @Serial
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
        return new ResultResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    /**
     * 请求成功，返回特定data
     *
     * @param data 返回数据
     */
    public static <T> ResultResponse<T> success(T data) {
        return success(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    /**
     * 请求成功，返回特定message和data
     *
     * @param message 返回信息
     * @param data    返回数据
     */
    public static <T> ResultResponse<T> success(String message, T data) {
        return success(SUCCESS_CODE, message, data);
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
        return fail(FAIL_CODE, FAIL_MESSAGE);
    }

    /**
     * 请求失败，返回特定code
     *
     * @param code 状态码
     */
    public static ResultResponse<Void> fail(Integer code) {
        return fail(code, FAIL_MESSAGE);
    }

    /**
     * 请求失败，返回特定code
     *
     * @param message 请求信息
     */
    public static ResultResponse<Void> fail(String message) {
        return fail(FAIL_CODE, message);
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
     * @param commonExceptionEnum 请求异常
     */
    public static ResultResponse<Void> fail(CommonExceptionEnum commonExceptionEnum) {
        return fail(commonExceptionEnum.getCode(), commonExceptionEnum.getMessage());
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
