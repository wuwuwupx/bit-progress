package com.bitprogress.securityroute.service.gain.impl;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.property.AnonymousRouteProperties;
import com.bitprogress.securityroute.service.gain.RoutePropertyService;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class AnonymousRoutePropertyService implements RoutePropertyService<ApiRoute> {

    private final AnonymousRouteProperties anonymousRouteProperties;

    /**
     * 配置是否覆盖其他路由来源
     */
    @Override
    public Boolean isCover() {
        return Objects.nonNull(anonymousRouteProperties.getCover()) && anonymousRouteProperties.getCover();
    }

    /**
     * 从配置中获取路由集合
     *
     * @return 路由集合
     */
    @Override
    public Set<ApiRoute> getRoutes() {
        return anonymousRouteProperties.getRoutes();
    }

}
