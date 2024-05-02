package com.bitprogress.exception;

import com.bitprogress.auth.base.AuthException;
import com.bitprogress.auth.base.Result;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

public class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    public CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                          ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);
        Map<String, Object> errorAttributes = new HashMap<>(8);
        HttpStatus errorStatus = determineHttpStatus(error);
        Result result = determineResult(error);
        errorAttributes.put("path", request.path());
        errorAttributes.put("status", errorStatus.value());
        errorAttributes.put("error", errorStatus.getReasonPhrase());
        errorAttributes.put("code", result.getCode());
        errorAttributes.put("msg", result.getMessage());
        return errorAttributes;
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private HttpStatus determineHttpStatus(Throwable throwable) {
        return throwable instanceof ResponseStatusException ? ((ResponseStatusException)throwable).getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private Result determineResult(Throwable throwable) {
        if (throwable instanceof ResponseStatusException) {
            HttpStatus status = ((ResponseStatusException) throwable).getStatus();
            return Result.error(status.getReasonPhrase(), status.getReasonPhrase());
        } else if (throwable instanceof IllegalArgumentException) {
            return Result.error(AuthException.REQUEST_ARGUMENT);
        } else {
            return Result.error(throwable.getMessage(), throwable.getMessage());
        }
    }

}
