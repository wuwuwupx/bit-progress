package com.bitprogress.securityroute.service.context.impl;

import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.securityroute.service.context.RouteContextService;

import java.util.Set;

public class PermissionRouteContextService implements RouteContextService<PermissionRoute> {

    private static final ThreadLocal<Set<PermissionRoute>> ROUTES = new ThreadLocal<>();

    /**
     * 获取路由
     *
     * @return 路由集合
     */
    @Override
    public Set<PermissionRoute> getRoutes() {
        return ROUTES.get();
    }

    /**
     * 设置路由
     *
     * @param routes 路由集合
     */
    @Override
    public void setRoutes(Set<PermissionRoute> routes) {
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
