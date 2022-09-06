package com.bitprogress.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author wuwuwupx
 * @desc: 配置服务版本
 */
@Configuration
@ConfigurationProperties(prefix = DubboServiceProperties.PREFIX)
public class DubboServiceProperties {

    public static final String PREFIX = "dubbo.service";

    private Map<String, ServiceProperties> multiple;

    public Map<String, ServiceProperties> getMultiple() {
        return multiple;
    }

    public void setMultiple(Map<String, ServiceProperties> multiple) {
        this.multiple = multiple;
    }

}
