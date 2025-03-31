package com.bitprogress.servercore.exception;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ValidationException;
import com.bitprogress.response.model.ResultResponse;
import com.bitprogress.exception.util.ErrorUtils;
import com.bitprogress.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalControllerHandler {

    @Autowired
    private ApplicationContext applicationContext;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommonException.class)
    public ResultResponse<Void> handleCustomizeException(CommonException e) {
        return ResultResponse.fail(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResultResponse<Void> handleBaseException(ValidationException e) {
        return ResultResponse.fail(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultResponse<Void> handleBaseException(IllegalArgumentException e) {
        String lowestMessage = ErrorUtils.getLowestMessage(e);
        return ResultResponse.fail(StringUtils.isEmpty(lowestMessage) ? "请求参数错误" : lowestMessage);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResultResponse<Void> handleBaseException(Throwable e) {
        String lowestMessage = ErrorUtils.getLowestMessage(e);
        return ResultResponse.fail(StringUtils.isEmpty(lowestMessage) ? "服务器内部错误" : lowestMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String lowestMessage = ErrorUtils.getLowestMessage(e);
        return ResultResponse.fail(StringUtils.isEmpty(lowestMessage) ? "请求数据格式错误" : lowestMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ResultResponse<Void> handleDataIntegrityViolationException(NullPointerException e) {
        return ResultResponse.fail("请求对象不存在");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultResponse<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String lowestMessage = ErrorUtils.getLowestMessage(e);
        return ResultResponse.fail(StringUtils.isEmpty(lowestMessage) ? "请求参数缺失" : lowestMessage);
    }

}
