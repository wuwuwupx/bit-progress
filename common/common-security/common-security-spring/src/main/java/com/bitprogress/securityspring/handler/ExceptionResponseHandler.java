package com.bitprogress.securityspring.handler;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionMessage;
import com.bitprogress.util.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 异常响应处理器
 */
public interface ExceptionResponseHandler {

    default void writeExceptionResponse(HttpServletResponse httpServletResponse,
                                        Integer statusCode,
                                        String responseJson) throws IOException {
        httpServletResponse.setStatus(statusCode);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.println(responseJson);
        writer.flush();
        writer.close();
    }

    default void writeExceptionResponse(HttpServletResponse response, ExceptionMessage message) throws IOException {
        writeExceptionResponse(response, message.getCode(), JsonUtils.serializeObject(message));
    }

    default void writeExceptionResponse(HttpServletResponse response, CommonException exception) throws IOException {
        writeExceptionResponse(response, exception.getCode(), JsonUtils.serializeObject(exception));
    }

}
