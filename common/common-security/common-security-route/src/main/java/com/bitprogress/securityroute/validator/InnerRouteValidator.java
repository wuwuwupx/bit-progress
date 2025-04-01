package com.bitprogress.securityroute.validator;

import com.bitprogress.securityroute.context.RouteContext;
import com.bitprogress.securityroute.property.InnerRouteProperties;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

@AllArgsConstructor
public class InnerRouteValidator extends RouteValidator {

    private final InnerRouteProperties innerRouteProperties;

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    public Boolean isInnerRoute(HttpMethod method, String url) {
        Boolean cover = innerRouteProperties.getCover();
        Boolean anonymousRouteByProperties = isInnerRouteByProperties(method, url);
        return cover ? anonymousRouteByProperties : anonymousRouteByProperties || isInnerRouteByContext(method, url);
    }

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    private Boolean isInnerRouteByProperties(HttpMethod method, String url) {
        return CollectionUtils.anyMatch(innerRouteProperties.getRoutes(), apiRoute ->
                super.pathMatch(apiRoute, method, url));
    }

    /**
     * 判断是否是匿名路由
     *
     * @param method 请求方法
     * @param url    url
     * @return 是否是匿名路由
     */
    private Boolean isInnerRouteByContext(HttpMethod method, String url) {
        return CollectionUtils.anyMatch(RouteContext.getAnonymousRoutes(), apiRoute ->
                super.pathMatch(apiRoute, method, url));
    }

}
