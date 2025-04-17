package com.bitprogress.securityroute.service.gain;

import com.bitprogress.securityroute.entity.ApiRoute;

import java.util.Set;

public interface RouteGainService<T extends ApiRoute> {

    /**
     * 从配置中获取路由集合
     *
     * @return 路由集合
     */
    Set<T> getRoutes();

}
