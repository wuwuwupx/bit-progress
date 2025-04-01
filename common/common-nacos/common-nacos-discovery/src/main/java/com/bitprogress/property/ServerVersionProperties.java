package com.bitprogress.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务版本配置
 */
@Setter
@Getter
@ConfigurationProperties(prefix = ServerVersionProperties.PREFIX)
public class ServerVersionProperties {

    public static final String PREFIX = "spring.cloud";

    /**
     * 需要版本配置的服务
     * key：服务名，value：版本信息
     * 如没有配置版本信息，默认使用default
     * -- GETTER --
     *  获取版本控制信息

     */
    private Map<String, String> serverVersion = new HashMap<>();

    /**
     * 获取服务的版本控制信息
     *
     * @param serviceId 服务名
     */
    public String getServerVersionByServiceId(String serviceId) {
        return this.serverVersion.get(serviceId);
    }

}
