package com.bitprogress.securityroute.service;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.util.CollectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import java.util.Objects;
import java.util.Set;

public interface RouteMatchService {

    AntPathMatcher MATCHER = new AntPathMatcher();

    /**
     * 判断是否匹配
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否匹配
     */
    default Boolean matchRoute(HttpMethod method, String url) {
        return matchByProperties(method, url) || (!isCoverByProperties() && matchRouteByContext(method, url));
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
    Set<ApiRoute> getRoutesByProperties();

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    default Boolean matchByProperties(HttpMethod method, String url) {
        return CollectionUtils.anyMatch(getRoutesByProperties(), apiRoute -> pathMatch(apiRoute, method, url));
    }

    /**
     * 从上下文中获取路由信息
     *
     * @return 路由信息
     */
    Set<ApiRoute> getRoutesByContext();

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    default Boolean matchRouteByContext(HttpMethod method, String url) {
        return CollectionUtils.anyMatch(getRoutesByContext(), apiRoute -> pathMatch(apiRoute, method, url));
    }

    /**
     * 路由规则匹配（ant格式）
     *
     * @param apiRoute 路由信息
     * @param method   请求方法
     * @param url      请求路径
     */
    default boolean pathMatch(ApiRoute apiRoute, HttpMethod method, String url) {
        return Objects.nonNull(apiRoute)
                && Objects.nonNull(apiRoute.getMethod())
                && (apiRoute.getMethod().equals(method)
                && MATCHER.match(apiRoute.getUrl(), url));
    }

}
