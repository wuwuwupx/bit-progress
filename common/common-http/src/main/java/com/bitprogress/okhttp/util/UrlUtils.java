package com.bitprogress.okhttp.util;

import com.bitprogress.basemodel.constant.StringConstants;
import com.bitprogress.util.CollectionUtils;

import java.util.Map;

/**
 * url拼接工具类
 */
public class UrlUtils {

    /**
     * 拼接url和参数
     *
     * @param url    原始url
     * @param params 请求参数
     */
    public static String urlJoinParam(String url, Map<String, String> params) {
        if (CollectionUtils.isNotEmpty(params)) {
            StringBuilder urlBuilder = new StringBuilder(url);
            params.forEach((key, value) -> {
                boolean hasParam = url.contains(StringConstants.QUESTION_MARK);
                urlBuilder.append(hasParam ? StringConstants.AND : StringConstants.QUESTION_MARK)
                        .append(key)
                        .append(StringConstants.EQUAL_SIGN)
                        .append(value);
            });
            return urlBuilder.toString();
        }
        return url;
    }

}
