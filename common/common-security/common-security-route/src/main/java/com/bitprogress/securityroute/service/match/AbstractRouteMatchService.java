package com.bitprogress.securityroute.service.match;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.service.gain.RouteAnnotationService;
import com.bitprogress.securityroute.service.gain.RoutePropertyService;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public abstract class AbstractRouteMatchService<T extends ApiRoute> implements RouteMatchService<T> {

    private final RoutePropertyService<T> routePropertyService;
    private final RouteAnnotationService<T> routeAnnotationService;

    /**
     * 是否被配置文件中的路由信息覆盖
     *
     * @return 是否覆盖
     */
    @Override
    public Boolean isCoverByProperties() {
        return routePropertyService.isCover();
    }

    /**
     * 从配置文件中获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public Set<T> getRoutesByProperties() {
        return routePropertyService.getRoutes();
    }

    /**
     * 从注解中获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public Set<T> getRoutesByAnnotation() {
        return routeAnnotationService.getRoutes();
    }

}
