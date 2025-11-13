package com.bitprogress.okhttp.util;

import com.bitprogress.okhttp.constant.OkHttpConstants;
import com.bitprogress.okhttp.handler.ResponseHandler;
import com.bitprogress.okhttp.handler.ResponseHandlers;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.FilenameUtils;
import com.bitprogress.util.StringUtils;
import lombok.SneakyThrows;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.util.Map;

/**
 * okhttp请求工具类
 */
public class OkHttpClientUtils {

    /**
     * 创建GET请求
     *
     * @param url     链接
     * @param params  请求参数
     * @param headers 请求头
     */
    private static Request createGetRequest(String url,
                                            Map<String, String> params,
                                            Map<String, String> headers) {
        url = UrlUtils.urlJoinParam(url, params);
        Request.Builder builder = new Request.Builder()
                .get()
                .url(url);
        if (CollectionUtils.isNotEmpty(headers)) {
            builder.headers(Headers.of(headers));
        }
        return builder.build();
    }

    /**
     * 创建POST请求
     *
     * @param url       链接
     * @param body      请求内容
     * @param mediaType 请求内容类型
     * @param params    请求参数
     * @param headers   请求头
     */
    private static Request createPostRequest(String url,
                                             String body,
                                             MediaType mediaType,
                                             Map<String, String> params,
                                             Map<String, String> headers) {
        return createPostRequest(url, RequestBody.create(body, mediaType), params, headers);
    }

    /**
     * 创建POST请求
     *
     * @param url     链接
     * @param body    请求内容
     * @param params  请求参数
     * @param headers 请求头
     */
    private static Request createPostRequest(String url,
                                             RequestBody body,
                                             Map<String, String> params,
                                             Map<String, String> headers) {
        if (CollectionUtils.isNotEmpty(params)) {
            url = UrlUtils.urlJoinParam(url, params);
        }
        Request.Builder builder = new Request
                .Builder()
                .url(url)
                .post(body);
        if (CollectionUtils.isNotEmpty(headers)) {
            builder.headers(Headers.of(headers));
        }
        return builder.build();
    }

    /**
     * 发起POST请求
     *
     * @param url  请求url
     * @param body 请求内容
     */
    public static String doPost(String url, String body) {
        return doPost(url, body, OkHttpConstants.MEDIA_TYPE_JSON);
    }

    /**
     * 发起POST请求
     *
     * @param url       请求url
     * @param body      请求内容
     * @param mediaType 请求内容类型
     */
    public static String doPost(String url, String body, MediaType mediaType) {
        return doPost(url, body, mediaType, null, null);
    }

    /**
     * 发起POST请求
     *
     * @param url       请求url
     * @param body      请求内容
     * @param mediaType 请求内容类型
     */
    public static String doPost(String url,
                                String body,
                                MediaType mediaType,
                                Map<String, String> params,
                                Map<String, String> headers) {
        return doPost(url, body, mediaType, params, headers, ResponseHandlers.stringHandler());
    }

    /**
     * 发起POST请求
     *
     * @param url  请求url
     * @param body 请求内容
     */
    public static String doPost(String url, RequestBody body, Map<String, String> params, Map<String, String> headers) {
        return doPost(url, body, params, headers, ResponseHandlers.stringHandler());
    }

    /**
     * 发起POST请求
     *
     * @param url  请求url
     * @param body 请求内容
     */
    public static <T> T doPost(String url,
                               String body,
                               MediaType mediaType,
                               Map<String, String> params,
                               Map<String, String> headers,
                               ResponseHandler<T> responseHandler) {
        Request request = createPostRequest(url, body, mediaType, params, headers);
        return doPost(request, responseHandler);
    }

    /**
     * 发起POST请求
     *
     * @param url  请求url
     * @param body 请求内容
     */
    public static <T> T doPost(String url,
                               RequestBody body,
                               Map<String, String> params,
                               Map<String, String> headers,
                               ResponseHandler<T> responseHandler) {
        Request request = createPostRequest(url, body, params, headers);
        return doPost(request, responseHandler);
    }

    /**
     * 发起POST请求
     *
     * @param request         请求
     * @param responseHandler 响应处理
     */
    @SneakyThrows
    public static <T> T doPost(Request request, ResponseHandler<T> responseHandler) {
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        try (Response execute = call.execute()) {
            return responseHandler.handle(execute);
        }
    }

    /**
     * 发起GET请求
     *
     * @param url     请求url
     * @param headers 请求头
     */
    public static String doGetWithHeader(String url, Map<String, String> headers) {
        return doGet(url, null, headers);
    }

    /**
     * 发起GET请求
     *
     * @param url    请求url
     * @param params 请求参数
     */
    public static String doGetWithParam(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }

    /**
     * 发起GET请求
     *
     * @param url 请求url
     */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * 发起GET请求
     *
     * @param url     请求url
     * @param params  请求参数
     * @param headers 请求头
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headers) {
        return doGet(url, params, headers, ResponseHandlers.stringHandler());
    }

    /**
     * 发起GET请求
     *
     * @param url     请求url
     * @param params  请求参数
     * @param headers 请求头
     */
    @SneakyThrows
    public static <T> T doGet(String url,
                              Map<String, String> params,
                              Map<String, String> headers,
                              ResponseHandler<T> responseHandler) {
        OkHttpClient client = new OkHttpClient();
        Request request = createGetRequest(url, params, headers);
        Call call = client.newCall(request);
        try (Response execute = call.execute()) {
            return responseHandler.handle(execute);
        }
    }

    /**
     * 发起GET请求
     *
     * @param url     请求url
     * @param params  请求参数
     * @param headers 请求头
     */
    @SneakyThrows
    public static File doGetDownFile(String url,
                                     Map<String, String> params,
                                     Map<String, String> headers,
                                     File directory) {
        OkHttpClient client = new OkHttpClient();
        Request request = createGetRequest(url, params, headers);
        Response response = client
                .newCall(request)
                .execute();
        String contentType = response.header("Content-Type");
        if (contentType != null && contentType.startsWith("application/json")) {
            // application/json; encoding=utf-8 下载媒体文件出错
            throw new RuntimeException("下载媒体文件出错");
        }
        String fileName = OkHttpResponseProxy.getFileName(response);
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }

        String baseName = FilenameUtils.getBaseName(fileName);
        if (StringUtils.isEmpty(fileName) || baseName.length() < 3) {
            baseName = String.valueOf(System.currentTimeMillis());
        }
        File file = File.createTempFile(baseName, "." + FilenameUtils.getExtension(fileName), directory);
        try (BufferedSink sink = Okio.buffer(Okio.sink(file))) {
            ResponseBody body = response.body();
            if (body == null) return null;
            sink.writeAll(body.source());
        }
        file.deleteOnExit();
        return file;
    }

}
