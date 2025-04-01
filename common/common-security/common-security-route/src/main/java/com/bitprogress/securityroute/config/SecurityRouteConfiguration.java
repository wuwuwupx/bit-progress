package com.bitprogress.securityroute.config;

import com.bitprogress.securityroute.property.AnonymousRouteProperties;
import com.bitprogress.securityroute.property.InnerRouteProperties;
import com.bitprogress.securityroute.property.PermissionRouteProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties({AnonymousRouteProperties.class, InnerRouteProperties.class, PermissionRouteProperties.class})
public class SecurityRouteConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RouteInitialization routeInitialization() {
        return new RouteInitialization() {
        };
    }

}
