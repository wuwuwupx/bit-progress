package com.bitprogress.cloudserver.service;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.bitprogress.cloudserver.property.ApplicationDataProperties;
import com.bitprogress.cloudserver.property.ApplicationProperties;
import com.bitprogress.cloudserver.property.ApplicationTokenProperties;
import com.bitprogress.securityroute.entity.ApiRoute;
import com.bitprogress.securityroute.entity.PermissionRoute;
import com.bitprogress.securityroute.service.RouteManagedService;
import com.bitprogress.util.JsonUtils;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

/**
 * 发布到nacos-config
 */
@AllArgsConstructor
public class CloudRouteManagedService extends RouteManagedService {

    private final ConfigService configService;
    private final ApplicationProperties applicationProperties;
    private final ApplicationDataProperties applicationDataProperties;
    private final ApplicationTokenProperties applicationTokenProperties;

    /**
     * 发布路由
     *
     * @param anonymousRoutes  匿名路由
     * @param innerRoutes      内部路由
     * @param permissionRoutes 权限路由
     */
    @Override
    protected void publishRoute(Set<ApiRoute> anonymousRoutes,
                                Set<ApiRoute> innerRoutes,
                                Set<PermissionRoute> permissionRoutes) {
        String group = applicationDataProperties.getRoutePublishGroup();
        String applicationName = applicationProperties.getName();
        String routeToken = applicationTokenProperties.getRoute();
        if (Objects.nonNull(anonymousRoutes)) {
            try {
                // 发布路由
                String anonymousRouteDataId = applicationName + "-" + routeToken + "-anonymous-route.json";
                configService.publishConfig(anonymousRouteDataId, group, JsonUtils.serializeObject(anonymousRoutes));
            } catch (NacosException e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.nonNull(innerRoutes)) {
            try {
                // 发布路由
                String innerRouteDataId = applicationName + "-" + routeToken + "-inner-route.json";
                configService.publishConfig(innerRouteDataId, group, JsonUtils.serializeObject(innerRoutes));
            } catch (NacosException e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.nonNull(permissionRoutes)) {
            try {
                // 发布路由
                String permissionRouteDataId = applicationName + "-" + routeToken + "-permission-route.json";
                configService.publishConfig(permissionRouteDataId, group, JsonUtils.serializeObject(permissionRoutes));
            } catch (NacosException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *
     * @throws Exception in case of shutdown errors. Exceptions will get logged
     *                   but not rethrown to allow other beans to release their resources as well.
     */
    @Override
    public void destroy() throws Exception {
        String group = applicationDataProperties.getRoutePublishGroup();
        String applicationName = applicationProperties.getName();
        String routeToken = applicationTokenProperties.getRoute();
        try {
            // 发布路由
            String anonymousRouteDataId = applicationName + "-" + routeToken + "-anonymous-route.json";
            configService.removeConfig(anonymousRouteDataId, group);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        try {
            // 发布路由
            String innerRouteDataId = applicationName + "-" + routeToken + "-inner-route.json";
            configService.removeConfig(innerRouteDataId, group);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        try {
            // 发布路由
            String permissionRouteDataId = applicationName + "-" + routeToken + "-permission-route.json";
            configService.removeConfig(permissionRouteDataId, group);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

}
