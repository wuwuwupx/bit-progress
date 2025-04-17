package com.bitprogress.securityroute.service.gain.impl;

import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.securityroute.property.PermissionRouteProperties;
import com.bitprogress.securityroute.service.gain.RoutePropertyService;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class PermissionRoutePropertyService implements RoutePropertyService<PermissionRoute> {

    private final PermissionRouteProperties permissionRouteProperties;

    /**
     * 配置是否覆盖其他路由来源
     */
    @Override
    public Boolean isCover() {
        return Objects.nonNull(permissionRouteProperties.getCover()) && permissionRouteProperties.getCover();
    }

    /**
     * 从配置中获取路由集合
     *
     * @return 路由集合
     */
    @Override
    public Set<PermissionRoute> getRoutes() {
        return permissionRouteProperties.getRoutes();
    }

}
