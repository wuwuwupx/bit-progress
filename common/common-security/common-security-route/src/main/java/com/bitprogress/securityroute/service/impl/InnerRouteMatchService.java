package com.bitprogress.securityroute.service.impl;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.property.InnerRouteProperties;
import com.bitprogress.securityroute.service.RouteMatchService;
import com.bitprogress.securityroute.service.context.impl.InnerRouteContextService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class InnerRouteMatchService implements RouteMatchService<ApiRoute> {

    private final InnerRouteProperties routeProperties;
    private final InnerRouteContextService routeContextService;

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    public Boolean isInnerRoute(HttpMethod method, String url) {
        return matchRoute(method, url);
    }

    /**
     * 是否被配置文件中的路由信息覆盖
     *
     * @return 是否覆盖
     */
    @Override
    public Boolean isCoverByProperties() {
        return Objects.nonNull(routeProperties.getCover()) && routeProperties.getCover();
    }

    /**
     * 从配置文件中获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public Set<ApiRoute> getRoutesByProperties() {
        return routeProperties.getRoutes();
    }

    /**
     * 从上下文中获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public Set<ApiRoute> getRoutesByAnnotation() {
        return routeContextService.getContextInfo();
    }

}
