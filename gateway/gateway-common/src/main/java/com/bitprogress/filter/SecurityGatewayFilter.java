package com.bitprogress.filter;

import com.alibaba.fastjson.JSON;
import com.bitprogress.constant.VerifyConstant;
import com.bitprogress.auth.base.AuthException;
import com.bitprogress.auth.base.AuthMsg;
import com.bitprogress.auth.base.AuthResult;
import com.bitprogress.auth.base.Result;
import com.bitprogress.route.GatewayRouteMsg;
import com.bitprogress.service.AuthService;
import com.bitprogress.service.MatchService;
import com.bitprogress.service.PermissionService;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR;

/**
 * @author wpx
 * Created on 2021/1/23 16:20
 */
@Configuration
public class SecurityGatewayFilter implements GlobalFilter {

    @Autowired
    private AuthService authService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 1. 检验是否白名单或非api开头 {@link MatchService#ignoreAuthentication(String, String)}
     * 2. 检验token是否正确 {@link AuthService#checkToken(String, Class)}
     *
     * @param exchange exchange信息
     * @param chain    chain信息
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String url = request.getPath().value();
        String method = request.getMethodValue();
        HttpHeaders headers = request.getHeaders();

        String authentication = authService.getAuthentication(headers);

        if (matchService.ignoreAuthentication(method, url)) {
            return mutateExchange(chain, exchange, request, StringUtils.MINUS_ONE, new AuthMsg());
        }
        // 校验token
        AuthResult<AuthMsg> authResult = authService.checkToken(authentication, AuthMsg.class);
        if (authResult.getResult()) {
            permissionService.authorizePermission(authResult, method, url);
            // 权限校验通过
            if (authResult.getResult()) {
                return mutateExchange(chain, exchange, request, authResult.getUserId(), authResult.getAuthMsg());
            }
        }


        return unauthorized(exchange, authResult.getAuthException());
    }

    /**
     * 通过检验，重新构建exchange
     * 将解析token获取的userId以及服务元数据中的中token放到ServerHttpRequest的header中，用以标识是通过网关转发的请求
     */
    private Mono<Void> mutateExchange(GatewayFilterChain chain,
                                      ServerWebExchange exchange,
                                      ServerHttpRequest request,
                                      String userId,
                                      AuthMsg authMsg) {
        DefaultResponse response = (DefaultResponse) exchange.getAttributes().get(GATEWAY_LOADBALANCER_RESPONSE_ATTR);
        String serviceId = response.getServer().getServiceId();
        String routeApiToken = GatewayRouteMsg.getRouteApiToken(serviceId);
        Map<String, String> params = authMsg.getParams();
        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header(VerifyConstant.USER_ID, userId).header(VerifyConstant.ROUTE_API_TOKEN, routeApiToken);
        if (CollectionUtils.nonEmpty(params)) {
            params.forEach(mutate::header);
        }
        ServerHttpRequest httpRequest = mutate.build();
        ServerWebExchange cmsExchange = exchange.mutate().request(httpRequest).build();
        return chain.filter(cmsExchange);
    }

    /**
     * 网关拒绝，返回401
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange, AuthException authException) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory()
                .wrap(JSON.toJSONBytes(Result.error(authException)));
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

}
