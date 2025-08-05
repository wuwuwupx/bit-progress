package com.bitprogress.bootserver.service.route;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.service.gain.RouteAnnotationService;
import com.bitprogress.bootserver.context.route.impl.InnerRouteContextService;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class InnerRouteAnnotationService implements RouteAnnotationService<ApiRoute> {

    private final InnerRouteContextService contextService;

    /**
     * 从配置中获取路由集合
     *
     * @return 路由集合
     */
    @Override
    public Set<ApiRoute> getRoutes() {
        return contextService.getContextInfo();
    }

}
