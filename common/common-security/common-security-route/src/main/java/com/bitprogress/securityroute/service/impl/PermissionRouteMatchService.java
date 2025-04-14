package com.bitprogress.securityroute.service.impl;

import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.securityroute.property.PermissionRouteProperties;
import com.bitprogress.securityroute.service.RouteMatchService;
import com.bitprogress.securityroute.service.context.impl.PermissionRouteContextService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class PermissionRouteMatchService implements RouteMatchService<PermissionRoute> {

    private final PermissionRouteProperties routeProperties;
    private final PermissionRouteContextService routeContextService;

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    public Boolean isPermissionRoute(HttpMethod method, String url) {
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
    public Set<PermissionRoute> getRoutesByProperties() {
        return routeProperties.getRoutes();
    }

    /**
     * 从上下文中获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public Set<PermissionRoute> getRoutesByContext() {
        return routeContextService.getRoutes();
    }

}
