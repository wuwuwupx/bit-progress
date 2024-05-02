package com.bitprogress.interceptor;

import com.bitprogress.property.ServerTokenProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static com.bitprogress.constant.VerifyConstant.FEIGN_COMMON_TOKEN;
import static com.bitprogress.constant.VerifyConstant.ROUTE_REST_TOKEN;
import static com.bitprogress.feignclient.FeignClientService.FEIGN_NAME;

/**
 * @author wuwuwupx
 * feign服务调用都需要加上对应服务的token
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    @Autowired
    private ServerTokenProperties serverTokenProperties;

    /**
     * 为所有rest请求加上调用服务对应的token
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        String serverName = template.feignTarget().name();
        // 基础FeignClient进行请求，带上对应的标识
        if (FEIGN_NAME.equals(serverName)) {
            template.header(ROUTE_REST_TOKEN, FEIGN_COMMON_TOKEN);
            return;
        }
        String serverToken = serverTokenProperties.getServerTokenByServerName(serverName);
        template.header(ROUTE_REST_TOKEN, serverToken);

    }

}
