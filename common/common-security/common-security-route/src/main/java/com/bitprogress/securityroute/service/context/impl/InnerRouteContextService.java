package com.bitprogress.securityroute.service.context.impl;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.service.context.RouteContextService;

import java.util.Set;

public class InnerRouteContextService implements RouteContextService<ApiRoute> {

    private static final ThreadLocal<Set<ApiRoute>> ROUTES = new ThreadLocal<>();

    /**
     * 获取路由
     *
     * @return 路由集合
     */
    @Override
    public Set<ApiRoute> getRoutes() {
        return ROUTES.get();
    }

    /**
     * 设置路由
     *
     * @param routes 路由集合
     */
    @Override
    public void setRoutes(Set<ApiRoute> routes) {
        ROUTES.set(routes);
    }

    /**
     * 清空路由
     */
    @Override
    public void clearRoutes() {
        ROUTES.remove();
    }

}
