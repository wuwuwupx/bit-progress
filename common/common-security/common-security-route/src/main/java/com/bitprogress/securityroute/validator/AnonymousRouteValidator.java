package com.bitprogress.securityroute.validator;

import com.bitprogress.securityroute.context.RouteContext;
import com.bitprogress.securityroute.property.AnonymousRouteProperties;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

@AllArgsConstructor
public class AnonymousRouteValidator extends RouteValidator {

    private final AnonymousRouteProperties anonymousRouteProperties;

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    public Boolean isAnonymousRoute(HttpMethod method, String url) {
        Boolean cover = anonymousRouteProperties.getCover();
        Boolean anonymousRouteByProperties = isAnonymousRouteByProperties(method, url);
        return cover ? anonymousRouteByProperties : anonymousRouteByProperties || isAnonymousRouteByContext(method, url);
    }

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    private Boolean isAnonymousRouteByProperties(HttpMethod method, String url) {
        return CollectionUtils.anyMatch(anonymousRouteProperties.getRoutes(), apiRoute ->
                super.pathMatch(apiRoute, method, url));
    }

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    private Boolean isAnonymousRouteByContext(HttpMethod method, String url) {
        return CollectionUtils.anyMatch(RouteContext.getAnonymousRoutes(), apiRoute ->
                super.pathMatch(apiRoute, method, url));
    }

}
