package com.bitprogress.securityroute.service.context;

import com.bitprogress.securityroute.entity.ApiRoute;

import java.util.Set;

public interface RouteContextService<T extends ApiRoute> {

    /**
     * 获取路由
     *
     * @return 路由集合
     */
    Set<T> getRoutes();

    /**
     * 设置路由
     *
     * @param routes 路由集合
     */
    void setRoutes(Set<T> routes);

    /**
     * 清空路由
     */
    void clearRoutes();

}
