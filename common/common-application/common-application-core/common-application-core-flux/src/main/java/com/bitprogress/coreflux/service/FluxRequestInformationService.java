package com.bitprogress.coreflux.service;

import com.bitprogress.basemodel.util.EnumUtils;
import com.bitprogress.corebase.exception.RequestExceptionMessage;
import com.bitprogress.corebase.service.RequestInformationService;
import com.bitprogress.coreflux.holder.ReactiveRequestContextHolder;
import com.bitprogress.exception.util.Assert;
import com.bitprogress.modelbase.constant.TenantConstant;
import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.request.enums.RequestSource;
import com.bitprogress.request.enums.RequestType;
import com.bitprogress.usercontext.entity.UserInfo;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.Objects;

@Service
public class FluxRequestInformationService implements RequestInformationService {

    private static final Logger log = LoggerFactory.getLogger(FluxRequestInformationService.class);

    /**
     * 获取请求
     *
     * @return 请求
     */
    private ServerHttpRequest getRequest() {
        ServerWebExchange exchange = ReactiveRequestContextHolder.getExchange();
        return Objects.isNull(exchange) ? null : exchange.getRequest();
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    @Override
    public String getTenantId() {
        ServerHttpRequest request = getRequest();
        Assert.notNull(request, "获取请求信息失败");
        return request.getHeaders().getFirst(TenantConstant.OPERATE_TENANT_KEY);
    }

    /**
     * 获取请求来源
     *
     * @return 请求来源
     */
    @Override
    public RequestSource getRequestSource() {
        ServerHttpRequest request = getRequest();
        Assert.notNull(request, "获取请求信息失败");
        String requestSourceStr = request.getHeaders().getFirst(VerifyConstant.REQUEST_RESOURCE);
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
        ServerHttpRequest request = getRequest();
        if (Objects.nonNull(request)) {
            String requestTypeStr = request.getHeaders().getFirst(VerifyConstant.REQUEST_TYPE);
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
        ServerHttpRequest request = getRequest();
        if (Objects.nonNull(request)) {
            String userInfoStr = request.getHeaders().getFirst(VerifyConstant.USER_INFO);
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
