package com.bitprogress.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuwuwupx
 * create on 2021/6/21 3:31
 * ServerProperties is
 */
@Configuration
@ConfigurationProperties(prefix = ApplicationTokenProperties.PREFIX)
public class ApplicationTokenProperties {

    public static final String PREFIX = "application.server.token";

    /**
     * 对外接口token
     */
    private String api;

    /**
     * 内部服务接口token
     */
    private String rest;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

}
