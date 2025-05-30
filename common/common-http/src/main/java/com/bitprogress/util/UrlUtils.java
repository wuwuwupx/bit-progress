package com.bitprogress.util;

import com.bitprogress.basemodel.constant.StringConstants;

import java.util.Map;

/**
 * @author wuwuwupx
 *  url拼接工具类
 */
public class UrlUtils {

    /**
     * 拼接url和参数
     *
     * @param url
     * @param params
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
