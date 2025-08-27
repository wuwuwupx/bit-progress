package com.bitprogress.filter;

import com.bitprogress.auth.base.AuthException;
import com.bitprogress.auth.base.AuthInfo;
import com.bitprogress.auth.base.AuthResult;
import com.bitprogress.auth.base.Result;
import com.bitprogress.request.constant.VerifyConstant;
import com.bitprogress.request.enums.RequestSource;
import com.bitprogress.request.enums.RequestType;
import com.bitprogress.service.AuthService;
import com.bitprogress.service.MatchService;
import com.bitprogress.service.PermissionService;
import com.bitprogress.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR;

/**
 * 鉴权过滤器
 */
@Service
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
        String method = request.getMethod().name();
        HttpHeaders headers = request.getHeaders();

        String authentication = authService.getAuthentication(headers);

        if (matchService.ignoreAuthentication(method, url)) {
            return mutateExchange(chain, exchange, request, RequestType.ANONYMOUS_REQUEST, new AuthInfo());
        }
        // 校验token
        AuthResult<AuthInfo> authResult = authService.checkToken(authentication, AuthInfo.class);
        if (authResult.getResult()) {
            permissionService.authorizePermission(authResult, method, url);
            // 权限校验通过
            if (authResult.getResult()) {
                return mutateExchange(chain, exchange, request, RequestType.USER_REQUEST, authResult.getUserInfo());
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
                                      RequestType requestType,
                                      AuthInfo authInfo) {
        DefaultResponse response = (DefaultResponse) exchange.getAttributes().get(GATEWAY_LOADBALANCER_RESPONSE_ATTR);
        ServiceInstance server = response.getServer();
        String userInfo = authInfo.getUserInfo();
        ServerHttpRequest.Builder mutate = request.mutate();
        // 设置请求头信息
        mutate.header(VerifyConstant.REQUEST_RESOURCE, RequestSource.GATEWAY.getValue().toString())
                .header(VerifyConstant.GATEWAY_TOKEN, server.getMetadata().get(VerifyConstant.GATEWAY_TOKEN))
                .header(VerifyConstant.REQUEST_TYPE, requestType.getValue().toString())
                .header(VerifyConstant.USER_ID, authInfo.getUserId())
                .header(VerifyConstant.USER_INFO, userInfo);
        ServerHttpRequest httpRequest = mutate.build();
        ServerWebExchange cmsExchange = exchange
                .mutate()
                .request(httpRequest)
                .build();
        return chain.filter(cmsExchange);
    }

    /**
     * 网关拒绝，返回401
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange, AuthException authException) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory()
                .wrap(JsonUtils.serializeObject(Result.error(authException)).getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

}
