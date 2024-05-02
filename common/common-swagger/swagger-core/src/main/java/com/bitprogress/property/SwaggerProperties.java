package com.bitprogress.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

/**
 * @author wuwuwupx
 * swagger配置信息
 */
@Configuration
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
public class SwaggerProperties {

    public static final String PREFIX = "swagger";

    /**
     * 是否展示swagger
     */
    private Boolean enable = false;

    /**
     * 分组
     */
    private String group = "default";

    /**
     * 说明
     */
    private String description = "接口信息";

    /**
     * 版本
     */
    private String version = "v1.0.0";

    /**
     * 应用名称
     */
    private String applicationName = "default";

    /**
     * securityScheme参数
     */
    private Map<String, String> securityScheme;

    /**
     * securityContext参数
     */
    private Set<String> securityContext;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Map<String, String> getSecurityScheme() {
        return securityScheme;
    }

    public void setSecurityScheme(Map<String, String> securityScheme) {
        this.securityScheme = securityScheme;
    }

    public Set<String> getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(Set<String> securityContext) {
        this.securityContext = securityContext;
    }

}
