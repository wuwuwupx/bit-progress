package com.bitprogress.util;

import com.bitprogress.exception.CommonException;
import com.bitprogress.okhttp.constant.OkHttpConstants;
import com.bitprogress.okhttp.util.OkHttpClientUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.bitprogress.constant.WechatConstant.ACCESS_TOKEN;

/**
 * @author wuwuwupx
 *  微信请求工具类
 */
public class WechatRequestUtils {

    /**
     * 统一get请求入口
     *
     * @param url         请求url
     * @param accessToken 微信接口调用凭证
     * @return 请求结果
     * @throws IOException
     */
    public static String doGetWithAccessToken(String url, String accessToken) {
        return doGetWithAccessToken(url, accessToken, null);
    }

    /**
     * 统一get请求入口
     *
     * @param url         请求url
     * @param accessToken 微信接口调用凭证
     * @return 请求结果
     * @throws IOException
     */
    public static String doGetWithAccessToken(String url, String accessToken, Map<String, String> addParams) {
        HashMap<String, String> params = new HashMap<>(8);
        params.put(ACCESS_TOKEN, accessToken);
        if (CollectionUtils.nonEmpty(addParams)) {
            params.putAll(addParams);
        }
        return doGet(url, params);
    }

    /**
     * 统一get请求入口
     *
     * @param url    请求url
     * @param params 请求参数
     * @return 请求结果
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params) {
        try {
            return OkHttpClientUtils.doGetWithParam(url, params);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException(500, e.getMessage(), "GET 微信接口 [" + url + "] 请求异常");
        }
    }

    /**
     * 统一get请求入口
     *
     * @param url         请求url
     * @param accessToken 微信接口调用凭证
     * @return 请求结果
     * @throws IOException
     */
    public static File doGetWithAccessToken(String url, String accessToken, Map<String, String> addParams, File file) {
        HashMap<String, String> params = new HashMap<>(8);
        params.put(ACCESS_TOKEN, accessToken);
        if (CollectionUtils.nonEmpty(addParams)) {
            params.putAll(addParams);
        }
        try {
            return OkHttpClientUtils.doGetDownFile(url, params, null, file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException(500, e.getMessage(), "GET 微信接口 [" + url + "] 请求异常");
        }
    }

    /**
     * 统一post请求入口
     *
     * @param url         请求url
     * @param accessToken 微信接口调用凭证
     * @param body        请求数据
     * @return 请求结果
     * @throws IOException
     */
    public static String doPostWithAccessToken(String url, String accessToken, String body) {
        MediaType mediaType = OkHttpConstants.MEDIA_TYPE_JSON;
        return doPostWithAccessToken(url, mediaType, accessToken, body, null);
    }

    /**
     * 统一post请求入口
     *
     * @param url         请求url
     * @param accessToken 微信接口调用凭证
     * @param body        请求数据
     * @return 请求结果
     * @throws IOException
     */
    public static String doPostWithAccessToken(String url, MediaType mediaType, String accessToken, String body) {
        return doPostWithAccessToken(url, mediaType, accessToken, body, null);
    }

    /**
     * 统一post请求入口
     *
     * @param url         请求url
     * @param mediaType   数据提交方式
     * @param accessToken 微信接口调用凭证
     * @param body        请求数据
     * @param addParams   补充参数
     * @return 请求结果
     * @throws IOException
     */
    public static String doPostWithAccessToken(String url, MediaType mediaType, String accessToken, String body,
                                               Map<String, String> addParams) {
        RequestBody requestBody = RequestBody.create(mediaType, body);
        return doPostWithAccessToken(url, accessToken, requestBody, addParams);
    }

    /**
     * 统一post请求入口
     *
     * @param url         请求url
     * @param accessToken 微信接口调用凭证
     * @param body        请求数据
     * @param addParams   补充参数
     * @return 请求结果
     * @throws IOException
     */
    public static String doPostWithAccessToken(String url, String accessToken, RequestBody body,
                                               Map<String, String> addParams) {
        HashMap<String, String> params = new HashMap<>(8);
        params.put(ACCESS_TOKEN, accessToken);
        if (CollectionUtils.nonEmpty(addParams)) {
            params.putAll(addParams);
        }
        return doPost(url, body, params);
    }

    public static String doPost(String url, String body) {
        MediaType mediaType = OkHttpConstants.MEDIA_TYPE_JSON;
        RequestBody requestBody = RequestBody.create(mediaType, body);
        return doPost(url, requestBody, null);
    }

    /**
     * 统一post请求入口
     *
     * @param url  请求url
     * @param body 请求数据
     * @return 请求结果
     * @throws IOException
     */
    public static String doPost(String url, RequestBody body, Map<String, String> params) {
        try {
            return OkHttpClientUtils.doPost(url, body, params, null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException(500, e.getMessage(), "POST 微信接口 [" + url + "] 请求异常");
        }
    }

}
