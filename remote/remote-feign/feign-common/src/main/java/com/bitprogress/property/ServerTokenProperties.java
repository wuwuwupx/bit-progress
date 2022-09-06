package com.bitprogress.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuwuwupx
 * create on 2021/6/21 3:31
 * ServerProperties is
 */
@Configuration
@ConfigurationProperties(prefix = ServerTokenProperties.PREFIX)
public class ServerTokenProperties {

    public static final String PREFIX = "spring.cloud";

    /**
     * 调用服务配置rest-token
     */
    private Map<String, String> serverToken = new HashMap<>();

    public Map<String, String> getServerToken() {
        return serverToken;
    }

    public void setServerToken(Map<String, String> serverToken) {
        this.serverToken = serverToken;
    }

    /**
     * 通过服务名获取调用服务需要的restToken
     *
     * @param serverName
     */
    public String getServerTokenByServerName(String serverName) {
        return this.serverToken.get(serverName);
    }

    /**
     * 根据服务名称设置其rest接口token
     *
     * @param serverName
     * @param restToken
     */
    public void setServerTokenByServerName(String serverName, String restToken) {
        this.serverToken.putIfAbsent(serverName, restToken);
    }

}
