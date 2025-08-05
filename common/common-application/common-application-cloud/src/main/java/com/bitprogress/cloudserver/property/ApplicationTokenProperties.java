package com.bitprogress.cloudserver.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ApplicationTokenProperties.PREFIX)
public class ApplicationTokenProperties {

    public static final String PREFIX = "application.server.token";

    /**
     * gateway token
     */
    private String gateway;

    /**
     * feign token
     */
    private String feign;

    /**
     * route token
     */
    private String route;

}
