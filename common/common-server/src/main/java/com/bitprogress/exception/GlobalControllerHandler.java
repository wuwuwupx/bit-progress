package com.bitprogress.exception;

import com.bitprogress.model.ResultResponse;
import com.bitprogress.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Objects;

/**
 * @author wuwuwupx
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
        String desc = applicationContext.getMessage(e.getCode(), e.getArgs(), e.getMessage(), Locale.getDefault());
        if (StringUtils.isEmpty(desc)) {
            desc = e.getCode();
        }
        return ResultResponse.fail(desc);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultResponse<Void> handleBaseException(IllegalArgumentException e) {
        String message = e.getMessage();
        final String nullTarget = "Target must not be null";
        if (StringUtils.isEmpty(message) || Objects.equals(nullTarget, message)) {
            message = "请求参数错误";
        }
        String desc = applicationContext.getMessage(message, null, null, Locale.getDefault());
        if (desc != null) {
            message = desc;
        }
        return ResultResponse.fail(message);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResultResponse<Void> handleBaseException(Throwable e) {
        String message = e.getMessage();
        if (StringUtils.isEmpty(message)) {
            message = "服务器内部错误";
        }
        return ResultResponse.fail(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        if (StringUtils.isEmpty(message)) {
            message = "请求数据格式错误";
        }
        return ResultResponse.fail(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ResultResponse<Void> handleDataIntegrityViolationException(NullPointerException e) {
        return ResultResponse.fail("请求对象不存在");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultResponse<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String eMessage = e.getMessage();
        return ResultResponse.fail(StringUtils.isEmpty(eMessage) ? "请求参数缺失" : eMessage);
    }

}
