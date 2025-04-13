package com.bitprogress.securityroute.service.impl;

import com.bitprogress.securityroute.context.RouteContext;
import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.property.AnonymousRouteProperties;
import com.bitprogress.securityroute.service.RouteMatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class AnonymousRouteMatchService implements RouteMatchService {

    private final AnonymousRouteProperties anonymousRouteProperties;

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    public Boolean isAnonymousRoute(HttpMethod method, String url) {
        return matchRoute(method, url);
    }

    /**
     * 是否被配置文件中的路由信息覆盖
     *
     * @return 是否覆盖
     */
    @Override
    public Boolean isCoverByProperties() {
        return Objects.nonNull(anonymousRouteProperties.getCover()) && anonymousRouteProperties.getCover();
    }

    /**
     * 从配置文件中获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public Set<ApiRoute> getRoutesByProperties() {
        return anonymousRouteProperties.getRoutes();
    }

    /**
     * 从上下文中获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public Set<ApiRoute> getRoutesByContext() {
        return RouteContext.getAnonymousRoutes();
    }

}
