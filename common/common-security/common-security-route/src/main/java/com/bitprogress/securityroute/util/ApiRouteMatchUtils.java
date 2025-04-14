package com.bitprogress.securityroute.util;

import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.util.CollectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class ApiRouteMatchUtils {

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    /**
     * 路由规则匹配（ant格式）
     *
     * @param referenceApiRoute 基准路由信息
     * @param apiRoute          需要匹配的路由信息
     */
    public static boolean pathMatch(ApiRoute referenceApiRoute, ApiRoute apiRoute) {
        return Objects.nonNull(referenceApiRoute)
                && Objects.nonNull(apiRoute)
                && Objects.nonNull(referenceApiRoute.getMethod())
                && Objects.nonNull(apiRoute.getMethod())
                && (referenceApiRoute.getMethod().equals(apiRoute.getMethod())
                && MATCHER.match(referenceApiRoute.getUrl(), apiRoute.getUrl()));
    }

    /**
     * 路由规则匹配（ant格式）
     *
     * @param apiRoute 路由信息
     * @param method   请求方法
     * @param url      请求路径
     */
    public static boolean pathMatch(ApiRoute apiRoute, HttpMethod method, String url) {
        return Objects.nonNull(apiRoute)
                && Objects.nonNull(apiRoute.getMethod())
                && (apiRoute.getMethod().equals(method) && MATCHER.match(apiRoute.getUrl(), url));
    }

    /**
     * 找到最接近的ApiRoute
     *
     * @param apiRoutes 需要匹配的ApiRoute列表
     * @param apiRoute  传入的ApiRoute
     * @return 最接近的ApiRoute
     */
    public static <T extends ApiRoute> Optional<T> findClosestApiRoute(Set<T> apiRoutes, ApiRoute apiRoute) {
        return CollectionUtils.filter(apiRoutes, route -> pathMatch(route, apiRoute))
                .min(Comparator.comparingInt(ApiRouteMatchUtils::countWildcards));
    }

    /**
     * 找到最接近的ApiRoute
     *
     * @param apiRoutes 需要匹配的ApiRoute列表
     * @param method    请求方法
     * @param url       请求路径
     * @return 最接近的ApiRoute
     */
    public static <T extends ApiRoute> Optional<T> findClosestApiRoute(Set<T> apiRoutes, HttpMethod method, String url) {
        return CollectionUtils.filter(apiRoutes, route -> pathMatch(route, method, url))
                .min(Comparator.comparingInt(ApiRouteMatchUtils::countWildcards));
    }

    /**
     * 计算通配符的数量
     *
     * @param route ApiRoute对象
     * @return 通配符的数量
     */
    private static int countWildcards(ApiRoute route) {
        String pattern = route.getUrl();
        int count = 0;
        for (char c : pattern.toCharArray()) {
            if (c == '*' || c == '?') {
                count++;
            }
        }
        return count;
    }

}
