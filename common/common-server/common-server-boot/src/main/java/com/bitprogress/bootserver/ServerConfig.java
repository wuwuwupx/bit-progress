package com.bitprogress.bootserver;

import com.bitprogress.bootserver.service.BootRouteInitializationService;
import com.bitprogress.securityroute.property.AnonymousRouteProperties;
import com.bitprogress.securityroute.property.InnerRouteProperties;
import com.bitprogress.securityroute.property.PermissionRouteProperties;
import com.bitprogress.securityroute.service.RouteInitializationService;
import com.bitprogress.securityroute.service.context.impl.AnonymousRouteContextService;
import com.bitprogress.securityroute.service.context.impl.InnerRouteContextService;
import com.bitprogress.securityroute.service.context.impl.PermissionRouteContextService;
import com.bitprogress.securityroute.service.impl.AnonymousRouteMatchService;
import com.bitprogress.securityroute.service.impl.InnerRouteMatchService;
import com.bitprogress.securityroute.service.impl.PermissionRouteMatchService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
public class ServerConfig {

    @Bean
    @ConditionalOnMissingBean(RouteInitializationService.class)
    public RouteInitializationService routeInitializationService(AnonymousRouteContextService anonymousRouteContextService,
                                                                 InnerRouteContextService innerRouteContextService,
                                                                 PermissionRouteContextService permissionRouteContextService) {
        return new BootRouteInitializationService(anonymousRouteContextService, innerRouteContextService, permissionRouteContextService);
    }

    @Bean
    @ConditionalOnMissingBean(AnonymousRouteContextService.class)
    public AnonymousRouteContextService anonymousRouteContextService() {
        return new AnonymousRouteContextService();
    }

    @Bean
    @ConditionalOnMissingBean(InnerRouteContextService.class)
    public InnerRouteContextService innerRouteContextService() {
        return new InnerRouteContextService();
    }

    @Bean
    @ConditionalOnMissingBean(PermissionRouteContextService.class)
    public PermissionRouteContextService permissionRouteContextService() {
        return new PermissionRouteContextService();
    }

    @Bean
    @ConditionalOnMissingBean(AnonymousRouteMatchService.class)
    public AnonymousRouteMatchService anonymousRouteMatchService(AnonymousRouteProperties anonymousRouteProperties,
                                                                 AnonymousRouteContextService anonymousRouteContextService) {
        return new AnonymousRouteMatchService(anonymousRouteProperties, anonymousRouteContextService);
    }

    @Bean
    @ConditionalOnMissingBean(InnerRouteMatchService.class)
    public InnerRouteMatchService innerRouteMatchService(InnerRouteProperties innerRouteProperties,
                                                         InnerRouteContextService innerRouteContextService) {
        return new InnerRouteMatchService(innerRouteProperties, innerRouteContextService);
    }

    @Bean
    @ConditionalOnMissingBean(PermissionRouteMatchService.class)
    public PermissionRouteMatchService permissionRouteMatchService(PermissionRouteProperties permissionRouteProperties,
                                                                   PermissionRouteContextService permissionRouteContextService) {
        return new PermissionRouteMatchService(permissionRouteProperties, permissionRouteContextService);
    }

}
