package com.bitprogress.securityroute.validator;

import com.bitprogress.securityroute.entity.ApiRoute;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import java.util.Objects;

public abstract class RouteValidator {

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    /**
     * 路由规则匹配（ant格式）
     *
     * @param apiRoute 路由信息
     * @param method   请求方法
     * @param url      请求路径
     */
    protected boolean pathMatch(ApiRoute apiRoute, HttpMethod method, String url) {
        return Objects.nonNull(apiRoute)
                && Objects.nonNull(apiRoute.getMethod())
                && (apiRoute.getMethod().equals(method)
                && MATCHER.match(apiRoute.getUrl(), url));
    }

}
