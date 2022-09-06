package com.bitprogress.model;

import com.bitprogress.constant.ResultConstants;
import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionMessage;

import java.io.Serializable;

/**
 * @author wuwuwupx
 * create on 2021/6/13 17:26
 * 返回请求结果
 */
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求返回的状态码
     */
    private Integer code;

    /**
     * 请求返回的错误
     */
    private String error;

    /**
     * 请求返回的信息
     */
    private String message;

    /**
     * 请求返回的数据
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultVO() {
    }

    public ResultVO(Integer code) {
        this(code, null, null, null);
    }

    public ResultVO(Integer code, String error, String message) {
        this(code, error, message, null);
    }

    public ResultVO(Integer code, String error, String message, T data) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    /**
     * 请求成功，无返回信息
     */
    public static ResultVO success() {
        return success(ResultConstants.SUCCESS_MESSAGE);
    }

    /**
     * 请求成功，返回特定message
     * 
     * @param message
     */
    public static ResultVO success(String message) {
        return success(ResultConstants.SUCCESS_CODE, message, null);
    }

    /**
     * 请求成功，返回特定data
     *
     * @param data
     */
    public static <T> ResultVO<T> successData(T data) {
        return success(ResultConstants.SUCCESS_CODE, ResultConstants.SUCCESS_MESSAGE, data);
    }

    /**
     * 请求成功，返回特定message和data
     * 
     * @param message
     * @param data
     */
    public static <T> ResultVO<T> success(String message, T data) {
        return success(ResultConstants.SUCCESS_CODE, message, data);
    }

    /**
     * 请求成功，返回特定code、message和data
     *
     * @param code
     * @param message
     * @param data
     */
    public static <T> ResultVO<T> success(Integer code, String message, T data) {
        return new ResultVO(code, null, message, data);
    }

    /**
     * 请求失败，无返回信息
     */
    public static ResultVO errorOf() {
        return errorOf(ResultConstants.FAIL_CODE);
    }

    /**
     * 请求失败，返回特定code
     * 
     * @param code
     */
    public static ResultVO errorOf(Integer code) {
        return errorOf(code, ResultConstants.FAIL_ERROR, ResultConstants.FAIL_MESSAGE);
    }

    /**
     * 请求失败，返回特定code
     *
     * @param message
     */
    public static ResultVO errorOf(String message) {
        return errorOf(ResultConstants.FAIL_CODE, ResultConstants.FAIL_ERROR, message);
    }

    /**
     * 请求失败，返回特定code, message
     *
     * @param code
     * @param message
     */
    public static ResultVO errorOf(Integer code, String error, String message) {
        return new ResultVO(code, error, message);
    }

    /**
     * 抛出异常
     *
     * @param exception
     */
    public static ResultVO errorOf(ExceptionMessage exception) {
        return errorOf(exception.getCode(), exception.getError(), exception.getMessage());
    }

    /**
     * 抛出异常
     *
     * @param exception
     */
    public static ResultVO errorOf(CommonException exception) {
        return errorOf(exception.getCode(), exception.getError(), exception.getMessage());
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "code=" + code +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
