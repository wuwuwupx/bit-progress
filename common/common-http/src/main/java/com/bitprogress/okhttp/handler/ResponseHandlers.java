package com.bitprogress.okhttp.handler;

import com.bitprogress.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ResponseBody;

import java.io.IOException;

public class ResponseHandlers {

    /**
     * 返回字符串
     *
     * @return ResponseHandler
     */
    public static ResponseHandler<String> stringHandler() {
        return response -> {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }
            ResponseBody body = response.body();
            if (body == null) return null;
            return body.string();
        };
    }

    /**
     * 返回字符串
     *
     * @return ResponseHandler
     */
    public static ResponseHandler<byte[]> byteHandler() {
        return response -> {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }
            ResponseBody body = response.body();
            if (body == null) return null;
            return body.bytes();
        };
    }

    /**
     * json处理
     *
     * @param clazz clazz
     * @return ResponseHandler
     */
    public static <T> ResponseHandler<T> jsonHandler(Class<T> clazz) {
        return response -> {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }
            ResponseBody body = response.body();
            if (body == null) return null;
            return JsonUtils.deserializeObject(body.byteStream(), clazz);
        };
    }

    /**
     * json处理
     *
     * @param typeReference typeReference
     * @return ResponseHandler
     */
    public static <T> ResponseHandler<T> jsonHandler(TypeReference<T> typeReference) {
        return response -> {
            ResponseBody body = response.body();
            if (body == null) return null;
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body.string(), typeReference);
        };
    }

    /**
     * json处理
     *
     * @param javaType javaType
     * @return ResponseHandler
     */
    public static <T> ResponseHandler<T> jsonHandler(JavaType javaType) {
        return response -> {
            ResponseBody body = response.body();
            if (body == null) return null;
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body.byteStream(), javaType);
        };
    }

}
