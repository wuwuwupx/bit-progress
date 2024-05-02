package com.bitprogress.okhttp.util;

import com.bitprogress.okhttp.constant.OkHttpConstants;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.FilenameUtils;
import com.bitprogress.util.StringUtils;
import com.bitprogress.util.UrlUtils;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author wuwuwupx
 * create on 2021/7/19 20:59
 * okhttp请求工具类
 */
public class OkHttpClientUtils {

    /**
     * 发起POST请求
     *
     * @param url  请求url
     * @param body  请求内容
     * @param mediaType
     */
    public static String doPost(String url, String body, MediaType mediaType) throws IOException {
        return doPost(url, body, mediaType, null, null);
    }

    /**
     * 发起POST请求
     *
     * @param url  请求url
     * @param body  请求内容
     * @param mediaType
     */
    public static String doPost(String url, String body, MediaType mediaType, Map<String, String> params,
                                Map<String, String> headers) throws IOException {
        if (Objects.isNull(mediaType)) {
            mediaType = OkHttpConstants.MEDIA_TYPE_JSON;
        }
        RequestBody requestBody = RequestBody.create(mediaType, body);
        return doPost(url, requestBody, params, headers);
    }

    /**
     * 发起POST请求
     *
     * @param url  请求url
     * @param body  请求内容
     */
    public static String doPost(String url, RequestBody body, Map<String, String> params,
                                Map<String, String> headers) throws IOException {
        if (CollectionUtils.isNotEmpty(params)) {
            url = UrlUtils.urlJoinParam(url, params);
        }
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url).post(body);
        if (CollectionUtils.isNotEmpty(headers)) {
            builder.headers(Headers.of(headers));
        }
        Request request = builder.build();
        return client.newCall(request).execute().body().string();
    }

    /**
     * 发起GET请求
     *
     * @param url  请求url
     * @param headers  请求头
     */
    public static String doGetWithHeader(String url, Map<String, String> headers) throws IOException {
        return doGet(url, null, headers);
    }

    /**
     * 发起GET请求
     *
     * @param url  请求url
     * @param params  请求参数
     */
    public static String doGetWithParam(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, null);
    }

    /**
     * 发起GET请求
     *
     * @param url  请求url
     */
    public static String doGet(String url) throws IOException {
        return doGet(url, null, null);
    }

    /**
     * 发起GET请求
     *
     * @param url  请求url
     * @param params  请求参数
     * @param headers  请求头
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url = UrlUtils.urlJoinParam(url, params);
        Request.Builder builder = new Request.Builder()
                .get()
                .url(url);
        if (CollectionUtils.isNotEmpty(headers)) {
            builder.headers(Headers.of(headers));
        }
        Request request = builder.build();
        return client.newCall(request).execute().body().string();
    }

    /**
     * 发起GET请求
     *
     * @param url  请求url
     * @param params  请求参数
     * @param headers  请求头
     */
    public static File doGetDownFile(String url, Map<String, String> params, Map<String, String> headers,
                                     File directory) throws IOException {
        OkHttpClient client = new OkHttpClient();
        url = UrlUtils.urlJoinParam(url, params);
        Request.Builder builder = new Request.Builder()
                .get()
                .url(url);
        if (CollectionUtils.isNotEmpty(headers)) {
            builder.headers(Headers.of(headers));
        }
        Request request = builder.build();
        Response response = client.newCall(request).execute();
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
            sink.writeAll(response.body().source());
        }
        file.deleteOnExit();
        return file;
    }

}
