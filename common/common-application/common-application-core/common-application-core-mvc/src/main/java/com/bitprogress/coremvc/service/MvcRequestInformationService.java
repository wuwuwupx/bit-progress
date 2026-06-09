package com.bitprogress.coremvc.service;

import com.bitprogress.basemodel.util.EnumUtils;
import com.bitprogress.corebase.exception.RequestExceptionMessage;
import com.bitprogress.corebase.service.RequestInformationService;
import com.bitprogress.exception.util.Assert;
import com.bitprogress.modelbase.constant.TenantConstant;
import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.request.enums.RequestSource;
import com.bitprogress.request.enums.RequestType;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Service
public class MvcRequestInformationService implements RequestInformationService {

    private static final Logger log = LoggerFactory.getLogger(MvcRequestInformationService.class);

    /**
     * 获取请求信息
     *
     * @return 请求信息
     */
    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.isNull(attributes) ? null : attributes.getRequest();
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    @Override
    public String getTenantId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(attributes, "获取请求信息失败");
        HttpServletRequest request = attributes.getRequest();
        return request.getHeader(TenantConstant.OPERATE_TENANT_KEY);
    }

    /**
     * 获取请求来源
     *
     * @return 请求来源
     */
    @Override
    public RequestSource getRequestSource() {
        HttpServletRequest request = getRequest();
        Assert.notNull(request, "获取请求信息失败");
        String requestSourceStr = request.getHeader(VerifyConstant.REQUEST_RESOURCE);
        Assert.isNotEmpty(requestSourceStr, RequestExceptionMessage.REQUEST_SOURCE_MISS_WRONG_EXCEPTION);
        RequestSource requestSource = EnumUtils.getByValue(RequestSource.class, Integer.parseInt(requestSourceStr));
        Assert.notNull(requestSource, RequestExceptionMessage.REQUEST_SOURCE_NOT_APPOINT_EXCEPTION);
        return requestSource;
    }

    /**
     * 获取请求类型
     *
     * @return 请求类型
     */
    @Override
    public RequestType getRequestType() {
        HttpServletRequest request = getRequest();
        if (Objects.nonNull(request)) {
            String requestTypeStr = request.getHeader(VerifyConstant.REQUEST_TYPE);
            if (StringUtils.isNotEmpty(requestTypeStr)) {
                try {
                    return EnumUtils.getByValue(RequestType.class, Integer.parseInt(requestTypeStr));
                } catch (Exception e) {
                    log.error("requestType convert error", e);
                }
            }
        }
        return RequestType.ANONYMOUS_REQUEST;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Override
    public UserInfo getUserInfo() {
        HttpServletRequest request = getRequest();
        if (Objects.nonNull(request)) {
            String userInfoStr = request.getHeader(VerifyConstant.USER_INFO);
            if (StringUtils.isNotEmpty(userInfoStr)) {
                try {
                    return JsonUtils.deserializeObject(userInfoStr, UserInfo.class);
                } catch (Exception e) {
                    log.error("userInfo convert error", e);
                }
            }
        }
        return new UserInfo();
    }

}
