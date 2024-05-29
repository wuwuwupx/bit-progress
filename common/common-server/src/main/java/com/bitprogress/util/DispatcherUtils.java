package com.bitprogress.util;

import com.bitprogress.basemodel.util.EnumUtils;
import com.bitprogress.exception.RequestExceptionMessage;
import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.request.enums.RequestSource;
import com.bitprogress.request.enums.RequestType;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherUtils {

    private static final Logger log = LoggerFactory.getLogger(DispatcherUtils.class);

    /**
     * 获取请求来源
     *
     * @param request 请求信息
     */
    public static RequestSource getRequestSource(HttpServletRequest request) {
        String requestSourceStr = request.getHeader(VerifyConstant.REQUEST_RESOURCE);
        Assert.isNotEmpty(requestSourceStr, RequestExceptionMessage.REQUEST_SOURCE_MISS_WRONG_EXCEPTION);
        RequestSource requestSource = EnumUtils.getByValue(RequestSource.class, Integer.parseInt(requestSourceStr));
        Assert.notNull(requestSource, RequestExceptionMessage.REQUEST_SOURCE_NOT_APPOINT_EXCEPTION);
        return requestSource;
    }

    /**
     * 获取请求类型
     *
     * @param request 请求信息
     */
    public static RequestType getRequestType(HttpServletRequest request) {
        String requestTypeStr = request.getHeader(VerifyConstant.REQUEST_TYPE);
        if (StringUtils.isNotEmpty(requestTypeStr)) {
            try {
                return EnumUtils.getByValue(RequestType.class, Integer.parseInt(requestTypeStr));
            } catch (Exception e) {
                log.error("requestType convert error", e);
            }
        }
        return RequestType.ANONYMOUS_REQUEST;
    }

}
