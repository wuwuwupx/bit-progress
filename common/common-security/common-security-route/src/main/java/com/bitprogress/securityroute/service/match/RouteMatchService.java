package com.bitprogress.securityroute.service.match;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.util.ApiRouteMatchUtils;
import com.bitprogress.util.CollectionUtils;
import org.springframework.http.HttpMethod;

import java.util.Optional;
import java.util.Set;

public interface RouteMatchService<T extends ApiRoute> {

    /**
     * 判断路由是否匹配
     *
     * @param method 请求方法
     * @param url    url
     * @return 路由是否匹配
     */
    default Boolean matchRoute(HttpMethod method, String url) {
        return matchByProperties(method, url) || (!isCoverByProperties() && matchRouteByAnnotation(method, url));
    }

    /**
     * 是否被配置文件中的路由信息覆盖
     *
     * @return 是否覆盖
     */
    Boolean isCoverByProperties();

    /**
     * 从配置文件中获取路由信息
     *
     * @return 路由信息
     */
    Set<T> getRoutesByProperties();

    /**
     * 判断路由是否匹配
     *
     * @param method 请求方法
     * @param url    url
     * @return 路由是否匹配
     */
    default Boolean matchByProperties(HttpMethod method, String url) {
        return CollectionUtils.anyMatch(getRoutesByProperties(), apiRoute -> pathMatch(apiRoute, method, url));
    }

    /**
     * 从注解中获取路由信息
     *
     * @return 路由信息
     */
    Set<T> getRoutesByAnnotation();

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 路由是否匹配
     */
    default Boolean matchRouteByAnnotation(HttpMethod method, String url) {
        return CollectionUtils.anyMatch(getRoutesByAnnotation(), apiRoute -> pathMatch(apiRoute, method, url));
    }

    /**
     * 判断路由是否匹配
     *
     * @param method 请求方法
     * @param url    url
     * @return 最接近的路由
     */
    default Optional<T> getClosestApiRoute(HttpMethod method, String url) {
        Set<T> apiRoutes = isCoverByProperties()
                ? getRoutesByProperties()
                : CollectionUtils.newSet(getRoutesByProperties(), getRoutesByAnnotation());
        return ApiRouteMatchUtils.findClosestApiRoute(apiRoutes, method, url);
    }

    /**
     * 判断路由是否匹配
     *
     * @param apiRoute apiRoute
     * @param method   请求方法
     * @param url      url
     * @return 路由是否匹配
     */
    default Boolean pathMatch(ApiRoute apiRoute, HttpMethod method, String url) {
        return ApiRouteMatchUtils.pathMatch(apiRoute, method, url);
    }

}
