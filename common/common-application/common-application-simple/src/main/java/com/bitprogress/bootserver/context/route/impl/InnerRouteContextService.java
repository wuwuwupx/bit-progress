package com.bitprogress.bootserver.context.route.impl;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.bootserver.context.route.RouteContextService;

import java.util.Set;

public class InnerRouteContextService implements RouteContextService<ApiRoute> {

    private static final ThreadLocal<Set<ApiRoute>> ROUTES = new ThreadLocal<>();

    /**
     * 获取路由
     *
     * @return 路由集合
     */
    @Override
    public Set<ApiRoute> getContextInfo() {
        return ROUTES.get();
    }

    /**
     * 设置路由
     *
     * @param routes 路由集合
     */
    @Override
    public void setContextInfo(Set<ApiRoute> routes) {
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
    public Class<ApiRoute> getInfoClass() {
        return ApiRoute.class;
    }

}
