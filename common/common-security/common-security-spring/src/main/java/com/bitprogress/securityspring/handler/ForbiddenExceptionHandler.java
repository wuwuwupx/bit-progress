package com.bitprogress.securityspring.handler;

import com.bitprogress.response.exception.CommonExceptionEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 拒绝访问异常处理
 */
public class ForbiddenExceptionHandler implements AccessDeniedHandler, ExceptionResponseHandler {

    /**
     * 拒绝访问异常处理
     *
     * @param request               请求
     * @param response              响应
     * @param accessDeniedException 拒绝访问异常
     * @throws IOException      IO 异常
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        writeExceptionResponse(response, CommonExceptionEnum.FORBIDDEN_EXCEPTION);
    }

}
