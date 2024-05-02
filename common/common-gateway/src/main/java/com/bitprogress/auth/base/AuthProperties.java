package com.bitprogress.auth.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuwuwupx
 */
@Configuration
@ConfigurationProperties(prefix = "gateway.auth")
public class AuthProperties {

    private String tokenName = "auth.token.";

    private String tokenPrefix;

    private boolean multiLogin = false;

    private Long cacheDays = 30L;

    private String rule = ")#*J&@J(#_=*!A";


    public boolean isMultiLogin() {
        return multiLogin;
    }

    public void setMultiLogin(boolean multiLogin) {
        this.multiLogin = multiLogin;
    }

    public Long getCacheDays() {
        return cacheDays;
    }

    public void setCacheDays(Long cacheDays) {
        this.cacheDays = cacheDays;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix + ":";
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
