package com.bitprogress.cloudserver.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ApplicationDataProperties.PREFIX)
public class ApplicationDataProperties {

    public static final String PREFIX = "application.server.data";

    /**
     * 路由发布数据ID
     */
    private String routePublishDataId;

    /**
     * 路由发布数据组
     */
    private String routePublishGroup;

}
