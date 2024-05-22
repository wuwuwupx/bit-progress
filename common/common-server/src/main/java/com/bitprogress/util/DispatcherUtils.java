package com.bitprogress.util;

import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.request.enums.RequestType;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherUtils {

    private static final Logger log = LoggerFactory.getLogger(DispatcherUtils.class);

    /**
     * 获取调度类型
     *
     * @param request 请求信息
     */
    public static RequestType getDispatcherType(HttpServletRequest request) {
        String requestTypeStr = request.getHeader(VerifyConstant.REQUEST_TYPE);
        if (StringUtils.isNotEmpty(requestTypeStr)) {
            try {
                return RequestType.valueOf(requestTypeStr);
            } catch (Exception e) {
                log.error("requestType convert error", e);
            }
        }
        return RequestType.ANONYMOUS_REQUEST;
    }

}
