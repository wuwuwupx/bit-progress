package com.bitprogress.cloudserver.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ApplicationProperties.PREFIX)
public class ApplicationProperties {

    public static final String PREFIX = "application.server";

    /**
     * application
     */
    private String name;

}
