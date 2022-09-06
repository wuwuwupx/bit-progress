package com.bitprogress.exception;

import com.bitprogress.util.StringUtils;
import com.bitprogress.model.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.FailedLoginException;
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
    public ResultVO handleCustomizeException(CommonException e) {
        return ResultVO.errorOf(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResultVO handleBaseException(ValidationException e) {
        String desc = applicationContext.getMessage(e.getCode(), e.getArgs(), e.getMsg(), Locale.getDefault());
        if (StringUtils.isEmpty(desc)) {
            desc = e.getCode();
        }
        return ResultVO.errorOf(desc);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FailedLoginException.class)
    public ResultVO handleFailedLoginException(FailedLoginException e) {
        return ResultVO.errorOf(ExceptionMessage.AUTH_ACCOUNT_PASSWORD_WRONG);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultVO handleBaseException(IllegalArgumentException e) {
        String message = e.getMessage();
        final String nullTarget = "Target must not be null";
        if (StringUtils.isEmpty(message) || Objects.equals(nullTarget, message)) {
            message = "请求参数错误";
        }
        String desc = applicationContext.getMessage(message, null, null, Locale.getDefault());
        if (desc != null) {
            message = desc;
        }
        return ResultVO.errorOf(message);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResultVO handleBaseException(Throwable e) {
        String message = e.getMessage();
        if (StringUtils.isEmpty(message)) {
            message = "服务器内部错误";
        }
        return ResultVO.errorOf(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        if (StringUtils.isEmpty(message)) {
            message = "请求数据格式错误";
        }
        return ResultVO.errorOf(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ResultVO handleDataIntegrityViolationException(NullPointerException e) {
        return ResultVO.errorOf("请求对象不存在");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultVO handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String eMessage = e.getMessage();
        return ResultVO.errorOf(StringUtils.isEmpty(eMessage) ? "请求参数缺失" : eMessage);
    }

}
