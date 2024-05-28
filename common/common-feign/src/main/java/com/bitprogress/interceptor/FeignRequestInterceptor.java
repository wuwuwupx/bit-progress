package com.bitprogress.interceptor;

import com.bitprogress.basecontext.context.DispatcherContext;
import com.bitprogress.ormcontext.context.TenantContext;
import com.bitprogress.ormparser.util.SqlParserUtils;
import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.request.enums.RequestSource;
import com.bitprogress.usercontext.context.UserContext;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;

import java.util.Map;

/**
 * @author wuwuwupx
 * feign服务调用都需要加上对应服务的token
 */
@AllArgsConstructor
public class FeignRequestInterceptor implements RequestInterceptor {

    private final ServiceInstanceChooser serviceInstanceChooser;

    /**
     * 为所有rest请求加上调用服务对应的token
     */
    @Override
    public void apply(RequestTemplate template) {
        template.header(VerifyConstant.REQUEST_RESOURCE, RequestSource.FEIGN.getValue().toString());
        String serverName = template.feignTarget().name();
        // 基础FeignClient进行请求，带上对应的标识
        ServiceInstance instance = serviceInstanceChooser.choose(serverName);
        Map<String, String> metadata = instance.getMetadata();
        String routeToken = CollectionUtils.getForMap(metadata, VerifyConstant.ROUTE_TOKEN);
        template.header(VerifyConstant.ROUTE_TOKEN, routeToken);
        // 上下文信息
        String dispatcherTypeJson = DispatcherContext.getDispatcherTypeJson();
        if (StringUtils.isNotEmpty(dispatcherTypeJson)) {
            template.header(VerifyConstant.DISPATCHER_TYPE, dispatcherTypeJson);
        }
        String userInfoJson = UserContext.getUserInfoJson();
        if (StringUtils.isNotEmpty(userInfoJson)) {
            template.header(VerifyConstant.USER_INFO, userInfoJson);
        }
        String tenantInfoJson = TenantContext.getTenantInfoJson();
        if (StringUtils.isNotEmpty(tenantInfoJson)) {
            template.header(VerifyConstant.TENANT_INFO, tenantInfoJson);
        }
        String sqlParserMsgJson = SqlParserUtils.getSqlParserMsgJson();
        if (StringUtils.isNotEmpty(sqlParserMsgJson)) {
            template.header(VerifyConstant.SQL_PARSER_MSG, sqlParserMsgJson);
        }
    }

}
