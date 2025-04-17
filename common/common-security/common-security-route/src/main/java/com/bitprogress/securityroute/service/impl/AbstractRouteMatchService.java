package com.bitprogress.securityroute.service.impl;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.service.RouteAnnotationService;
import com.bitprogress.securityroute.service.RouteMatchService;
import com.bitprogress.securityroute.service.RoutePropertyService;
import com.bitprogress.securityroute.util.ApiRouteMatchUtils;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Optional;
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
