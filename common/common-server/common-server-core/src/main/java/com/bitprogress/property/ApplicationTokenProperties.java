package com.bitprogress.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = ApplicationTokenProperties.PREFIX)
public class ApplicationTokenProperties {

    public static final String PREFIX = "application.server.token";

    /**
     * 服务路由token
     */
    private String route;

}
