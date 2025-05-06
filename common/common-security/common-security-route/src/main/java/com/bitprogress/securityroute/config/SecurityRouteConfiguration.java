package com.bitprogress.securityroute.config;

import com.bitprogress.securityroute.property.AnonymousRouteProperties;
import com.bitprogress.securityroute.property.InnerRouteProperties;
import com.bitprogress.securityroute.property.PermissionRouteProperties;
import com.bitprogress.securityroute.property.TicketRouteProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties({AnonymousRouteProperties.class, InnerRouteProperties.class,
        TicketRouteProperties.class, PermissionRouteProperties.class})
public class SecurityRouteConfiguration {

}
