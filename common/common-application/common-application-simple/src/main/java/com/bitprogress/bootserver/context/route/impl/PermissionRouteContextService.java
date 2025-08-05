package com.bitprogress.bootserver.context.route.impl;

import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.bootserver.context.route.RouteContextService;

import java.util.Set;

public class PermissionRouteContextService implements RouteContextService<PermissionRoute> {

    private static final ThreadLocal<Set<PermissionRoute>> ROUTES = new ThreadLocal<>();

    /**
     * 获取路由
     *
     * @return 路由集合
     */
    @Override
    public Set<PermissionRoute> getContextInfo() {
        return ROUTES.get();
    }

    /**
     * 设置路由
     *
     * @param routes 路由集合
     */
    @Override
    public void setContextInfo(Set<PermissionRoute> routes) {
        ROUTES.set(routes);
    }

    /**
     * 清空路由
     */
    @Override
    public void clearContextInfo() {
        ROUTES.remove();
    }

    /**
     * 获取上下文信息类型
     *
     * @return 上下文信息类型
     */
    @Override
    public Class<PermissionRoute> getInfoClass() {
        return PermissionRoute.class;
    }

}
