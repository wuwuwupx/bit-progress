package com.bitprogress.bootserver.service.route;

import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.bootserver.context.route.impl.PermissionRouteContextService;
import com.bitprogress.securityroute.service.gain.RouteAnnotationService;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class PermissionRouteAnnotationService implements RouteAnnotationService<PermissionRoute> {

    private final PermissionRouteContextService contextService;

    /**
     * 从配置中获取路由集合
     *
     * @return 路由集合
     */
    @Override
    public Set<PermissionRoute> getRoutes() {
        return contextService.getContextInfo();
    }

}
