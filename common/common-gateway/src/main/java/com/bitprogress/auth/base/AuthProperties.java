package com.bitprogress.auth.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gateway.auth")
public class AuthProperties {

    private String tokenName = "auth.token.";

    private String tokenPrefix;

    private boolean multiLogin = false;

    private Long cacheDays = 30L;

    private String rule = ")#*J&@J(#_=*!A";


    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix + ":";
    }

}
