package com.bitprogress.bootserver.config;

import com.bitprogress.bootserver.service.BootRouteManagedService;
import com.bitprogress.bootserver.service.route.*;
import com.bitprogress.securityroute.service.RouteManagedService;
import com.bitprogress.securityroute.service.context.impl.AnonymousRouteContextService;
import com.bitprogress.securityroute.service.context.impl.InnerRouteContextService;
import com.bitprogress.securityroute.service.context.impl.PermissionRouteContextService;
import com.bitprogress.securityroute.service.gain.impl.AnonymousRoutePropertyService;
import com.bitprogress.securityroute.service.gain.impl.InnerRoutePropertyService;
import com.bitprogress.securityroute.service.gain.impl.PermissionRoutePropertyService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class RouteConfiguration {

    @Bean
    @ConditionalOnMissingBean(RouteManagedService.class)
    public RouteManagedService routeInitializationService(
            AnonymousRouteContextService anonymousRouteContextService,
            InnerRouteContextService innerRouteContextService,
            PermissionRouteContextService permissionRouteContextService) {
        return new BootRouteManagedService(anonymousRouteContextService, innerRouteContextService, permissionRouteContextService);
    }

    @Bean
    @ConditionalOnMissingBean(AnonymousRouteMatchService.class)
    public AnonymousRouteMatchService anonymousRouteMatchService(AnonymousRoutePropertyService propertyService,
                                                                 AnonymousRouteAnnotationService annotationService) {
        return new AnonymousRouteMatchService(propertyService, annotationService);
    }

    @Bean
    @ConditionalOnMissingBean(AnonymousRouteAnnotationService.class)
    public AnonymousRouteAnnotationService innerRouteAnnotationService(AnonymousRouteContextService contextService) {
        return new AnonymousRouteAnnotationService(contextService);
    }

    @Bean
    @ConditionalOnMissingBean(AnonymousRouteContextService.class)
    public AnonymousRouteContextService anonymousRouteContextService() {
        return new AnonymousRouteContextService();
    }

    @Bean
    @ConditionalOnMissingBean(InnerRouteMatchService.class)
    public InnerRouteMatchService innerRouteMatchService(InnerRoutePropertyService propertyService,
                                                         InnerRouteAnnotationService annotationService) {
        return new InnerRouteMatchService(propertyService, annotationService);
    }

    @Bean
    @ConditionalOnMissingBean(InnerRouteAnnotationService.class)
    public InnerRouteAnnotationService innerRouteAnnotationService(InnerRouteContextService contextService) {
        return new InnerRouteAnnotationService(contextService);
    }

    @Bean
    @ConditionalOnMissingBean(InnerRouteContextService.class)
    public InnerRouteContextService innerRouteContextService() {
        return new InnerRouteContextService();
    }

    @Bean
    @ConditionalOnMissingBean(PermissionRouteMatchService.class)
    public PermissionRouteMatchService permissionRouteMatchService(PermissionRoutePropertyService propertyService,
                                                                   PermissionRouteAnnotationService annotationService) {
        return new PermissionRouteMatchService(propertyService, annotationService);
    }

    @Bean
    @ConditionalOnMissingBean(PermissionRouteAnnotationService.class)
    public PermissionRouteAnnotationService permissionRouteAnnotationService(
            PermissionRouteContextService contextService) {
        return new PermissionRouteAnnotationService(contextService);
    }

    @Bean
    @ConditionalOnMissingBean(PermissionRouteContextService.class)
    public PermissionRouteContextService permissionRouteContextService() {
        return new PermissionRouteContextService();
    }

}
