package com.bitprogress.bootserver.propertity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * shiro配置
 */
@Data
@ConfigurationProperties(prefix = "spring.security.auth.token")
public class AuthProperties {

    private String tokenName = "auth.token.";

    private String tokenPrefix;

    private boolean multiLogin = false;

    private Long cacheTimes = 3600L;

    private String rule = ")#*J&@J(#_=*!A";

}
