package com.bitprogress.securityroute.config;

import com.bitprogress.securityroute.property.AnonymousRouteProperties;
import com.bitprogress.securityroute.property.InnerRouteProperties;
import com.bitprogress.securityroute.property.PermissionRouteProperties;
import com.bitprogress.securityroute.service.context.impl.AnonymousRouteContextService;
import com.bitprogress.securityroute.service.context.impl.InnerRouteContextService;
import com.bitprogress.securityroute.service.context.impl.PermissionRouteContextService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties({AnonymousRouteProperties.class, InnerRouteProperties.class, PermissionRouteProperties.class})
public class SecurityRouteConfiguration {

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

}
