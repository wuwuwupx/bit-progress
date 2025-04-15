package com.bitprogress.interceptor;

import com.bitprogress.ormcontext.service.DataScopeContextService;
import com.bitprogress.ormcontext.service.TenantContextService;
import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.request.enums.RequestSource;
import com.bitprogress.systemcontext.service.DispatcherContextService;
import com.bitprogress.usercontext.service.UserInfoContextService;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;

import java.util.Map;

/**
 * feign服务调用都需要加上对应服务的token
 */
@AllArgsConstructor
public class FeignRequestInterceptor implements RequestInterceptor {

    private final ServiceInstanceChooser serviceInstanceChooser;
    private final TenantContextService tenantContextService;
    private final DataScopeContextService<?, ?> dataScopeContextService;

    /**
     * 为所有feign请求加上调用服务对应的token
     */
    @Override
    public void apply(RequestTemplate template) {
        template.header(VerifyConstant.REQUEST_RESOURCE, RequestSource.FEIGN.getValue().toString());
        String serverId = template.feignTarget().name();
        // 基础FeignClient进行请求，带上对应的标识
        ServiceInstance instance = serviceInstanceChooser.choose(serverId);
        Map<String, String> metadata = instance.getMetadata();
        String routeToken = CollectionUtils.getForMap(metadata, VerifyConstant.ROUTE_TOKEN);
        template.header(VerifyConstant.ROUTE_TOKEN, routeToken);
        // 上下文信息
        String dispatcherTypeJson = DispatcherContextService.getDispatcherTypeJson();
        if (StringUtils.isNotEmpty(dispatcherTypeJson)) {
            template.header(VerifyConstant.DISPATCHER_TYPE, dispatcherTypeJson);
        }
        String userInfoJson = UserInfoContextService.getUserInfoJson();
        if (StringUtils.isNotEmpty(userInfoJson)) {
            template.header(VerifyConstant.USER_INFO, userInfoJson);
        }
        String tenantInfoJson = tenantContextService.getUserInfoJson();
        if (StringUtils.isNotEmpty(tenantInfoJson)) {
            template.header(VerifyConstant.TENANT_INFO, tenantInfoJson);
        }
        String tenantParserInfoJson = tenantContextService.getParserInfoJson();
        if (StringUtils.isNotEmpty(tenantParserInfoJson)) {
            template.header(VerifyConstant.TENANT_PARSER_INFO, tenantParserInfoJson);
        }
        String dataScopeInfoJson = dataScopeContextService.getUserInfoJson();
        if (StringUtils.isNotEmpty(dataScopeInfoJson)) {
            template.header(VerifyConstant.DATA_SCOPE_INFO, dataScopeInfoJson);
        }
        String dataScopeParserInfoJson = dataScopeContextService.getParserInfoJson();
        if (StringUtils.isNotEmpty(dataScopeParserInfoJson)) {
            template.header(VerifyConstant.DATA_SCOPE_PARSER_INFO, dataScopeParserInfoJson);
        }
    }

}
