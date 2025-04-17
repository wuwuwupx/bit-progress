package com.bitprogress.securityspring.handler;

import com.bitprogress.response.exception.CommonExceptionEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class InvalidAuthenticationEntryPoint implements AuthenticationEntryPoint, ExceptionResponseHandler {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        writeExceptionResponse(response, CommonExceptionEnum.AUTH_MSG_EXCEPTION);
    }

}
