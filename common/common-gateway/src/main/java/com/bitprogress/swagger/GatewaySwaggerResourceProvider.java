package com.bitprogress.swagger;

import com.bitprogress.property.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * 配置swagger资源
 */
@Component
@Primary
public class GatewaySwaggerResourceProvider implements SwaggerResourcesProvider {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Autowired
    private RouteLocator routeLocator;

    /**
     * swagger2默认的url后缀
     */
    private static final String SWAGGER2URL = "/v2/api-docs?group=";

    /**
     * swagger默认前缀
     */
    private static final String SWAGGER_PRE_URL = "/swagger/";

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        String swaggerGroup = swaggerProperties.getGroup();
        Set<String> servers = new HashSet<>();
        Flux<Route> routes = routeLocator.getRoutes();
        routes.subscribe(route -> {
            String host = route.getUri().getHost();
            String url = SWAGGER_PRE_URL + host + SWAGGER2URL + swaggerGroup;
            if (!servers.contains(host)) {
                servers.add(host);
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setUrl(url);
                swaggerResource.setName(host);
                resources.add(swaggerResource);
            }
        });
        return resources;
    }

}
