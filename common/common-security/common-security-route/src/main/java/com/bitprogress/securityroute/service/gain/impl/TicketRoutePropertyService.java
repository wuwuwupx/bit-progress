package com.bitprogress.securityroute.service.gain.impl;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.property.TicketRouteProperties;
import com.bitprogress.securityroute.service.gain.RoutePropertyService;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class TicketRoutePropertyService implements RoutePropertyService<ApiRoute> {

    private final TicketRouteProperties ticketRouteProperties;

    /**
     * 配置是否覆盖其他路由来源
     */
    @Override
    public Boolean isCover() {
        return Objects.nonNull(ticketRouteProperties.getCover()) && ticketRouteProperties.getCover();
    }

    /**
     * 从配置中获取路由集合
     *
     * @return 路由集合
     */
    @Override
    public Set<ApiRoute> getRoutes() {
        return ticketRouteProperties.getRoutes();
    }

}
