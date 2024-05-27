package com.bitprogress.interceptor;

import com.bitprogress.basecontext.context.DispatcherContext;
import com.bitprogress.feignclient.FeignClientService;
import com.bitprogress.ormcontext.context.TenantContext;
import com.bitprogress.ormparser.util.SqlParserUtils;
import com.bitprogress.property.ServerTokenProperties;
import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.usercontext.context.UserContext;
import com.bitprogress.util.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;

/**
 * @author wuwuwupx
 * feign服务调用都需要加上对应服务的token
 */
@AllArgsConstructor
public class FeignRequestInterceptor implements RequestInterceptor {

    private final ServerTokenProperties serverTokenProperties;

    /**
     * 为所有rest请求加上调用服务对应的token
     */
    @Override
    public void apply(RequestTemplate template) {
        template.header(VerifyConstant.REQUEST_RESOURCE, VerifyConstant.FEIGN);
        String serverName = template.feignTarget().name();
        // 基础FeignClient进行请求，带上对应的标识
        if (FeignClientService.FEIGN_NAME.equals(serverName)) {
            template.header(VerifyConstant.ROUTE_REST_TOKEN, VerifyConstant.FEIGN_COMMON_TOKEN);
        } else {
            String serverToken = serverTokenProperties.getServerTokenByServerName(serverName);
            template.header(VerifyConstant.ROUTE_REST_TOKEN, serverToken);
        }
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
