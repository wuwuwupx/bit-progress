package com.bitprogress.nacos.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wpx
 * Created on 2021/1/27 11:19
 * 
 */
@Configuration
@ConfigurationProperties(prefix = "gateway.nacos")
public class NacosGatewayProperties {

    /**
     * 路由配置分组
     */
    private String group;

    /**
     * 路由配置DataID
     */
    private String routeDataId;

    /**
     * 路由白名单配置ID
     */
    private String whiteRouteDataId;

    /**
     * 路由资源配置ID
     */
    private String routeRouseDataId;

    /**
     * 路由ApiToken配置ID
     */
    private String routeApiTokenDataId;

    public NacosGatewayProperties() {
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRouteDataId() {
        return routeDataId;
    }

    public void setRouteDataId(String routeDataId) {
        this.routeDataId = routeDataId;
    }

    public String getWhiteRouteDataId() {
        return whiteRouteDataId;
    }

    public void setWhiteRouteDataId(String whiteRouteDataId) {
        this.whiteRouteDataId = whiteRouteDataId;
    }

    public String getRouteRouseDataId() {
        return routeRouseDataId;
    }

    public void setRouteRouseDataId(String routeRouseDataId) {
        this.routeRouseDataId = routeRouseDataId;
    }

    public String getRouteApiTokenDataId() {
        return routeApiTokenDataId;
    }

    public void setRouteApiTokenDataId(String routeApiTokenDataId) {
        this.routeApiTokenDataId = routeApiTokenDataId;
    }

}
